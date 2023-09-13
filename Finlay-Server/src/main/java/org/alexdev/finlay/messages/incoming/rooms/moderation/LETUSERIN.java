package org.alexdev.finlay.messages.incoming.rooms.moderation;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.FLATNOTALLOWEDTOENTER;
import org.alexdev.finlay.messages.outgoing.rooms.FLAT_LETIN;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class LETUSERIN implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId())) {
            return;
        }

        String username = reader.readString();
        String successToken = reader.contents();
        boolean canEnter = successToken.equals("A");

        Player enteringPlayer = PlayerManager.getInstance().getPlayerByName(username);

        if (enteringPlayer == null) {
            return;
        }

        if (canEnter) {
            enteringPlayer.getRoomUser().setAuthenticateId(room.getId());
            enteringPlayer.send(new FLAT_LETIN());
        } else {
            enteringPlayer.send(new FLATNOTALLOWEDTOENTER());
        }
    }
}
