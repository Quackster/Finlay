package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.game.messenger.MessengerMessage;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.messenger.MESSENGER_MSG;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class MESSENGER_GETMESSAGES implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        for (MessengerMessage offlineMessage : player.getMessenger().getOfflineMessages().values()) {
            player.send(new MESSENGER_MSG(offlineMessage));
        }
    }
}
