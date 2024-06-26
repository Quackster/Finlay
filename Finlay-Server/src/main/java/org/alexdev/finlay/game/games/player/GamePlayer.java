package org.alexdev.finlay.game.games.player;

import org.alexdev.finlay.game.games.Game;
import org.alexdev.finlay.game.games.GameManager;
import org.alexdev.finlay.game.games.GameObject;
import org.alexdev.finlay.game.games.battleball.BattleBallGame;
import org.alexdev.finlay.game.games.battleball.BattleBallTile;
import org.alexdev.finlay.game.games.battleball.enums.BattleBallPlayerState;
import org.alexdev.finlay.game.games.snowstorm.util.SnowStormAttributes;
import org.alexdev.finlay.game.games.utils.ScoreReference;
import org.alexdev.finlay.game.pathfinder.Position;
import org.alexdev.finlay.game.player.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class GamePlayer {
    private Player player;
    private GameObject gameObject;
    private int userId;
    private int objectId;
    private int gameId;
    private int teamId;
    private Position position;
    private boolean enteringGame;
    private boolean isSpectator;
    private boolean inGame;
    private boolean clickedRestart;
    private BattleBallPlayerState playerState;
    private GamePlayer harlequinPlayer;

    private boolean assignedSpawn;
    private AtomicInteger score;
    private int xp;

    private SnowStormAttributes snowStormAttributes;

    public GamePlayer(Player player) {
        this.player = player;
        this.userId = player.getDetails().getId();
        this.teamId = -1;
        this.gameId = -1;
        this.objectId = -1;
        this.harlequinPlayer = null;
        this.enteringGame = false;
        this.clickedRestart = false;
        this.position = new Position();
        this.score = new AtomicInteger(0);
        this.xp = 0;
        this.snowStormAttributes = new SnowStormAttributes();
    }

    /**
     * Set the score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score.set(score);
    }

    /**
     * Get the score of the current player, the team score is generated by adding all scores from all players
     *
     * @return the score
     */
    public void calculateScore() {
        if (!this.inGame) {
            this.score.set(0);
            return;
        }

        if (this.getGame() instanceof BattleBallGame) {
            this.score.set(0);

            BattleBallGame battleBallGame = (BattleBallGame) this.getGame();

            for (BattleBallTile battleBallTile : battleBallGame.getTiles()) {
                for (ScoreReference scoreReference : battleBallTile.getPointsReferece()) {
                    if (scoreReference.getBy() != this.userId) {
                        continue;
                    }

                    this.score.addAndGet(scoreReference.getScore());
                }
            }
        }
    }

    public int getScore() {
        return score.get();
    }

    /**
     * Get the xp of the current player
     *
     * @return the score
     */
    public int getXp() {
        return xp;
    }
    /**
     * Sets the xp for the player, does NOT allow negative numbers
     *
     * @param xp the xp to set
     */
    public void setXp(int xp) {
        this.xp = xp;
        if (this.xp < 0) {
            this.xp = 0;
        }
    }


    /**
     * Get the game the game player is currently playing in
     *
     * @return the game instance
     */
    public Game getGame() {
        return GameManager.getInstance().getGameById(this.getGameId());
    }

    /**
     * Get the player being held in this game player instance
     *
     * @return the game player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the user id of the game player
     *
     * @return the user id of the game player
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Get the current team the user is on, this will take the Harlequin power up into account so
     * use {@getTeamId()} for their actual team id.
     *
     * @return the team instance
     */
    public GameTeam getTeam() {
        int teamId = this.harlequinPlayer != null ? this.harlequinPlayer.getTeamId() : this.getTeamId();
        return this.getGame().getTeams().get(teamId);
    }

    /**
     * Get the team id that the player is currently in
     *
     * @return the team id
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Set the team id the user is currently in
     *
     * @param teamId the team id
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * Set the spawn position of the player, used for when the game starts and restarts
     *
     * @return the spawn position
     */
    public Position getSpawnPosition() {
        return position;
    }

    /**
     * Get the current game id that the gamer player is in, -1 for no game
     *
     * @return the game id
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Set the game id the user is currently in
     *
     * @param gameId the game id
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    /**
     * Get if the user is entering the game, used for disabling walking on room entry
     *
     * @return true, if successful
     */
    public boolean isEnteringGame() {
        return enteringGame;
    }

    /**
     * Set whether not the users are entering a game
     *
     * @param enteringGame whether not they're entering the game
     */
    public void setEnteringGame(boolean enteringGame) {
        this.enteringGame = enteringGame;
    }

    /**
     * Get whether the user is in game or not
     *
     * @return true, if successful
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Set whether they're in game or not
     *
     * @param inGame the flag to set
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * Get if the user has clicked restart when the game has ended
     *
     * @return true, if successful
     */
    public boolean isClickedRestart() {
        return clickedRestart;
    }

    /**
     * Set whether not the gamer player clicked restart when the game ended
     *
     * @param clickedRestart the flag whether or not they clicked restart
     */
    public void setClickedRestart(boolean clickedRestart) {
        this.clickedRestart = clickedRestart;
    }

    /**
     * Get the opponent object id that the user is colouring for
     *
     * @return the opponent id, -1 for none
     */
    public int getColouringForOpponentId() {
        return harlequinPlayer != null ? harlequinPlayer.getObjectId() : -1;
    }

    /**
     * Get the player that acticated the harlequin up
     *
     * @return the player who activated the power up
     */
    public GamePlayer getHarlequinPlayer() {
        return harlequinPlayer;
    }

    /**
     * Set the player who activated the harlequin power up
     *
     * @param harlequinPlayer the game player
     */
    public void setHarlequinPlayer(GamePlayer harlequinPlayer) {
        this.harlequinPlayer = harlequinPlayer;
    }

    /**
     * Get if the user is spectating the match
     *
     * @return true, if successful
     */
    public boolean isSpectator() {
        return isSpectator;
    }

    /**
     * Set whether or not the user is spectating the match or not
     *
     * @param spectator whether or not they're spectating
     */
    public void setSpectator(boolean spectator) {
        isSpectator = spectator;
    }

    /**
     * Get the current state the player is in, the default being NORMAL.
     *
     * @return the player state
     */
    public BattleBallPlayerState getPlayerState() {
        return playerState;
    }

    /**
     * Set the current state the player is in
     *
     * @param playerState the player state
     */
    public void setPlayerState(BattleBallPlayerState playerState) {
        this.playerState = playerState;
    }

    /**
     * Get the game object attached to this player
     *
     * @return the game object
     */
    public GameObject getGameObject() {
        return gameObject;
    }

    /**
     * Set the game object attached to this player
     *
     * @param gameObject the game object
     */
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    /**
     * Get the object id attached to this player
     *
     * @return the object id
     */
    public int getObjectId() {
        return objectId;
    }

    /**
     * Set the object id attached to this player
     *
     * @param objectId the object id
     */
    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    /**
     * Get the class for snowstorm attributes.
     *
     * @return the instance
     */
    public SnowStormAttributes getSnowStormAttributes() {
        return snowStormAttributes;
    }

    public boolean isAssignedSpawn() {
        return assignedSpawn;
    }

    public void setAssignedSpawn(boolean assignedSpawn) {
        this.assignedSpawn = assignedSpawn;
    }
}
