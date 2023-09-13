package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.game.messenger.MessengerError;
import org.alexdev.finlay.game.messenger.MessengerErrorType;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.messenger.FRIENDS_UPDATE;
import org.alexdev.finlay.messages.outgoing.messenger.MESSENGER_ERROR;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.config.GameConfiguration;

public class FRIENDLIST_UPDATE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        player.send(new FRIENDS_UPDATE(player, player.getMessenger()));
    }
}
