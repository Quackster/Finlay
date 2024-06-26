package org.alexdev.finlay.messages.outgoing.games;

import org.alexdev.finlay.game.games.enums.GameType;
import org.alexdev.finlay.game.games.history.GameHistory;
import org.alexdev.finlay.game.games.snowstorm.SnowStormGame;
import org.alexdev.finlay.game.games.Game;
import org.alexdev.finlay.game.games.enums.GameState;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.List;
import java.util.stream.Collectors;

public class INSTANCELIST extends MessageComposer {
    private final List<Game> createdGames;
    private final List<Game> startedGames;
    private final List<GameHistory> finishedGames;

    public INSTANCELIST(List<Game> gamesByType, List<GameHistory> finishedGames) {
        this.createdGames = gamesByType.stream().filter(game -> game.getGameState() == GameState.WAITING).collect(Collectors.toList());
        this.startedGames = gamesByType.stream().filter(game -> game.getGameState() == GameState.STARTED).collect(Collectors.toList());
        this.finishedGames = finishedGames;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.createdGames.size());

        for (Game game : this.createdGames) {
            response.writeInt(game.getId());
            response.writeString(game.getName());

            response.writeInt(game.getGameCreatorId());
            response.writeString(game.getGameCreator());

            if (game.getGameType() == GameType.SNOWSTORM) {
                SnowStormGame snowStormGame = (SnowStormGame) game;
                response.writeInt(snowStormGame.getGameLength());
            }

            response.writeInt(game.getMapId());
        }

        response.writeInt(this.startedGames.size());

        for (Game game : this.startedGames) {
            response.writeInt(game.getId());
            response.writeString(game.getName());
            response.writeString(game.getGameCreator());

            if (game.getGameType() == GameType.SNOWSTORM) {
                SnowStormGame snowStormGame = (SnowStormGame) game;
                response.writeInt(snowStormGame.getGameLength());
            }

            response.writeInt(game.getMapId());
        }

        response.writeInt(this.finishedGames.size());

        for (GameHistory game : this.finishedGames) {
            response.writeInt(game.getId());
            response.writeString(game.getName());
            response.writeString(game.getGameCreator());

            if (game.getGameType() == GameType.SNOWSTORM) {
                response.writeInt(game.getGameLength());
            }

            response.writeInt(game.getMapId());
        }
    }

    @Override
    public short getHeader() {
        return 232; // "Ch"
    }
}
