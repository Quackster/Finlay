package org.alexdev.finlay.messages.incoming.games;

import org.alexdev.finlay.game.games.Game;
import org.alexdev.finlay.game.games.GameManager;
import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.game.games.player.GameTeam;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.triggers.GameLobbyTrigger;
import org.alexdev.finlay.messages.outgoing.games.CREATEFAILED;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class KICKPLAYER implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (!(room.getModel().getRoomTrigger() instanceof GameLobbyTrigger)) {
            return;
        }

        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (gamePlayer == null) {
            return;
        }

        Game game = GameManager.getInstance().getGameById(gamePlayer.getGameId());

        if (game == null || game.getGameCreatorId() != player.getDetails().getId()) {
            return;
        }

        int instanceId = reader.readInt();

        GamePlayer teamPlayer = null;

        for (GameTeam team : game.getTeams().values()) {
            for (GamePlayer p : team.getActivePlayers()) {
                if (p.getPlayer().getRoomUser().getInstanceId() == instanceId) {
                    teamPlayer = p;
                    break;
                }
            }
        }

        if (teamPlayer == null) {
            return;
        }

        game.leaveGame(teamPlayer);
        teamPlayer.getPlayer().send(new CREATEFAILED(CREATEFAILED.FailedReason.KICKED));
    }
}
