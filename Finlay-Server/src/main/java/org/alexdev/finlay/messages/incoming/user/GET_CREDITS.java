package org.alexdev.finlay.messages.incoming.user;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GET_CREDITS implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) {
        player.send(new CREDIT_BALANCE(player.getDetails()));
    }
}
