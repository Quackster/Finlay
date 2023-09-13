package org.alexdev.finlay.messages.incoming.moderation;

import org.alexdev.finlay.game.moderation.cfh.CallForHelpManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class SUBMIT_CFH implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        String message = reader.readString();

        if (message.length() == 0) {
            return;
        }

        // TODO: ignore messages that only contains spaces

        // Only allow one call for help per user
        if (CallForHelpManager.getInstance().hasPendingCall(player)) {
            return;
        }

        CallForHelpManager.getInstance().submitCall(player, message);
    }
}
