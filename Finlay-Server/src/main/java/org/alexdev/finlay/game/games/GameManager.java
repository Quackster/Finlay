package org.alexdev.finlay.game.games;

import org.alexdev.finlay.dao.mysql.GameDao;
import org.alexdev.finlay.game.GameScheduler;
import org.alexdev.finlay.game.games.battleball.BattleBallMap;
import org.alexdev.finlay.game.games.enums.GameType;
import org.alexdev.finlay.game.games.history.GameHistory;
import org.alexdev.finlay.game.games.player.GameRank;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.models.RoomModel;
import org.alexdev.finlay.messages.outgoing.games.GAMEDELETED;
import org.alexdev.finlay.util.DateUtil;
import org.alexdev.finlay.util.config.GameConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GameManager {
    private static final int MAX_HISTORY = 15;
    private static GameManager instance = null;
    private ScheduledFuture<?> expiryLoop;

    private AtomicInteger gameCounter;
    private AtomicInteger finishedGameCounter;

    private List<GameSpawn> spawnList;
    private List<GameRank> rankList;
    private List<RoomModel> modelList;

    private List<Game> games;
    private List<BattleBallMap> battleballTileMaps;

    private Map<GameType, List<GameHistory>> lastPlayedGames;

    public GameManager() {
        this.rankList = GameDao.getRanks();
        this.modelList = GameDao.getGameMaps();
        this.spawnList = GameDao.getGameSpawns();
        this.battleballTileMaps = GameDao.getBattleballTileMaps();

        this.games = new ArrayList<>();

        this.gameCounter = new AtomicInteger(0);
        this.finishedGameCounter = new AtomicInteger(0);

       // this.createExpiryCheckLoop();
        this.refreshPlayedGames();
    }

    public void refreshPlayedGames() {
        if (this.lastPlayedGames == null) {
            this.lastPlayedGames = new ConcurrentHashMap<>();

            for (GameType gameType : GameType.values()) {
                this.lastPlayedGames.put(gameType, new CopyOnWriteArrayList<>());
            }
        }

        for (GameType gameType : GameType.values()) {
            var games = this.lastPlayedGames.get(gameType);

            if (games.size() > GameManager.MAX_HISTORY) {
                games = games.subList(games.size() - Math.min(games.size(), GameManager.MAX_HISTORY + 1), games.size());
                this.lastPlayedGames.put(gameType, games);
            }
        }
    }


    /**
     * Recurring task used to clear old games after their listing time has expired.
     */
    /*private void createExpiryCheckLoop() {
        this.expiryLoop = GameScheduler.getInstance().getService().scheduleAtFixedRate(() -> {
            finishedGames.removeIf(game -> DateUtil.getCurrentTimeSeconds() > game.getExpireTime());
        }, 0, getListingExpiryTime() / 10, TimeUnit.SECONDS);
    }*/

    /**
     * Get the game spawn list by game type, map id and team id
     *
     * @param gameType the game type (battleball or snowstorm)
     * @param mapId the map id
     * @param teamId the team id
     * @return the game spawn
     */
    public List<GameSpawn> getGameSpawns(GameType gameType, int mapId, int teamId) {
        /*for (GameSpawn gameSpawn : this.spawnList) {
            if ((gameSpawn.getGameType() == gameType) && (gameSpawn.getMapId() == mapId) && (gameSpawn.getTeamId() == teamId)) {
                return gameSpawn;
            }
        }*/

        return this.spawnList.stream().filter(gameSpawn -> (gameSpawn.getGameType() == gameType) && (gameSpawn.getMapId() == mapId) && (gameSpawn.getTeamId() == teamId)).collect(Collectors.toList());
    }

    /**
     * Get the game spawn by gamr type, map id and team id
     *
     * @param mapId the map id
     * @return the game spawn
     */
    public BattleBallMap getBattleballTileMap(int mapId) {
        for (BattleBallMap tileMap : this.battleballTileMaps) {
            if (tileMap.getMapId() == mapId) {
                return tileMap;
            }
        }

        return null;
    }

    /**
     * Get the game rank by the player points.
     *
     * @param type the type of game to get the points for
     * @param player the player to get the points for
     * @return the rank, null otherwise
     */
    public GameRank getRankByPoints(GameType type, Player player) {
        int score = player.getDetails().getGamePoints(type);

        for (GameRank rank : this.rankList) {
            if (score >= rank.getMinPoints()) {
                if (rank.getMaxPoints() == 0 || score <= rank.getMaxPoints()) {
                    return rank;
                }
            }
        }

        return null;
    }

    /**
     * Gets a game instance by specified game id
     *
     * @param gameId the game id used
     * @return the game instance
     */
    public Game getGameById(int gameId) {
        for (Game game : this.games) {
            if (game.getId() == gameId) {
                return game;
            }
        }

        return null;
    }

    /**
     * Get the list of games by type
     *
     * @param gameType the type of game
     * @return the list of games
     */
    public List<Game> getGamesByType(GameType gameType) {
        return this.games.stream().filter(
                game -> game.getGameType() == gameType
        ).collect(Collectors.toList());
    }

    /**
     * Get the list of game ranks by type
     *
     * @param gameType the type of game to get the ranks for
     * @return the list of ranks
     */
    public List<GameRank> getRanksByType(GameType gameType) {
        return this.rankList.stream().filter(
                rank -> rank.getType() == gameType
        ).collect(Collectors.toList());
    }

    /**
     * Get the instance of {@link GameManager}
     *
     * @return the instance
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    /**
     * Reload the instance of {@link GameManager}
     */
    public static void reset() {
        int gameCounter = 0;

        if (instance != null) {
            gameCounter = instance.gameCounter.get();
        }

        instance = null;
        getInstance().getGameCounter().set(gameCounter);
    }

    /**
     * Creates a new game id for the game
     *
     * @return the game id
     */
    public int createId() {
        return gameCounter.incrementAndGet();
    }

    /**
     * Gets the game id counter.
     *
     * @return the id counter
     */
    public AtomicInteger getGameCounter() {
        return gameCounter;
    }


    /**
     * Gets the list of currently active games
     *
     * @return the list of games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * Gets the restart time for the specified game type.
     */
    public int getRestartSeconds(GameType gameType) {
        return GameConfiguration.getInstance().getInteger(gameType.name().toLowerCase() + ".restart.game.seconds");
    }

    /**
     * Gets the game time for the specified game type.
     */
    public int getLifetimeSeconds(GameType gameType) {
        return GameConfiguration.getInstance().getInteger(gameType.name().toLowerCase() + ".game.lifetime.seconds");
    }

    /**
     * Gets the game time for the specified game type.
     */
    public int getPreparingSeconds(GameType gameType) {
        return GameConfiguration.getInstance().getInteger(gameType.name().toLowerCase() + ".preparing.game.seconds");
    }

    /**
     * Get the amount of seconds allowed for a finished game to persist on the instance list before it's removed
     *
     * @return the amount of seconds
     */
    public int getListingExpiryTime() {
        return GameConfiguration.getInstance().getInteger("game.finished.listing.expiry.seconds");
    }

    /**
     * Get model by type and map id
     *
     * @return the room model instance
     */
    public RoomModel getModel(GameType type, int mapId) {
        String prefix = (type == GameType.BATTLEBALL ? "bb" : "ss");

        for (RoomModel roomModel : this.modelList) {
            if (roomModel.getName().equals(prefix + "_arena_" + mapId)) {
                return roomModel;
            }
        }

        return null;
    }

    public List<RoomModel> getMaps() {
        return this.modelList;
    }

    /**
     * Get the list of all finished games
     *
     * @return the list of finished games
     */
    public List<GameHistory> getLastPlayedGames(GameType gameType) {
        return lastPlayedGames.get(gameType);
    }

    public GameHistory getFinishedGameById(GameType gameType, int gameId) {
        return this.lastPlayedGames.get(gameType).stream().filter(g -> g.getId() == gameId).findFirst().orElse(null);
    }
}
