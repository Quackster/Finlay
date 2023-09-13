package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.game.messenger.MessengerUser;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.messages.outgoing.messenger.FRIEND_REQUEST;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class MESSENGER_GETREQUESTS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        for (MessengerUser requester : player.getMessenger().getRequests()) {
            player.send(new FRIEND_REQUEST(requester));
        }
    }
}
