package org.alexdev.finlay.messages.incoming.games;

import org.alexdev.finlay.game.games.GameParameter;
import org.alexdev.finlay.game.games.enums.GameType;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.triggers.GameLobbyTrigger;
import org.alexdev.finlay.messages.outgoing.games.GAMEPARAMETERS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class INITIATECREATEGAME implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (!(room.getModel().getRoomTrigger() instanceof GameLobbyTrigger)) {
            return;
        }

        GameLobbyTrigger gameLobbyTrigger = (GameLobbyTrigger) room.getModel().getRoomTrigger();
        GameParameter[] parameters = null;

        if (gameLobbyTrigger.getGameType() == GameType.BATTLEBALL) {
            parameters = new GameParameter[]{
                    new GameParameter("fieldType", true, "1", 1, 5),
                    new GameParameter("numTeams", true, "2", 2, 4),
                    new GameParameter("allowedPowerups", true, "1,2,3,4,5,6,7,8"),
                    new GameParameter("name", true, "")
            };
        }

        if (gameLobbyTrigger.getGameType() == GameType.SNOWSTORM) {
            parameters = new GameParameter[]{
                    new GameParameter("fieldType", true, "1", 1, 7),
                    new GameParameter("numTeams", true, "2", 2, 4),
                    new GameParameter("gameLengthChoice", true, "1", 1, 3),
                    new GameParameter("name", true, "")
            };
        }

        if (parameters != null)
            player.send(new GAMEPARAMETERS(parameters));
    }
}
