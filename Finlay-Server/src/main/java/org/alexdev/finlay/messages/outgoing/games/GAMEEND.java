package org.alexdev.finlay.messages.outgoing.games;

import org.alexdev.finlay.game.games.GameManager;
import org.alexdev.finlay.game.games.enums.GameType;
import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.game.games.player.GameTeam;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.Map;

public class GAMEEND extends MessageComposer {
    private final GameType gameType;
    private final Map<Integer, GameTeam> teams;

    public GAMEEND(GameType game, Map<Integer, GameTeam> teams) {
        this.gameType = game;
        this.teams = teams;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(GameManager.getInstance().getRestartSeconds(this.gameType));
        response.writeInt(this.teams.size());

        for (GameTeam team : this.teams.values()) {
            var players = team.getPlayers();
            response.writeInt(players.size());

            if (players.size() > 0) {
                for (GamePlayer gamePlayer : players) {
                    response.writeInt(gamePlayer.getObjectId());
                    response.writeString(gamePlayer.getPlayer().getDetails().getName());
                    response.writeInt(gamePlayer.getScore());
                }

                response.writeInt(team.getPoints());
            }
        }
    }

    @Override
    public short getHeader() {
        return 248;
    }
}
