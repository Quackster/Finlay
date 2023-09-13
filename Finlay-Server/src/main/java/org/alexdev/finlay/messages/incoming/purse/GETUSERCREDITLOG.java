package org.alexdev.finlay.messages.incoming.purse;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GETUSERCREDITLOG implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) {
        player.send(new CREDIT_BALANCE(player.getDetails()));
    }
}
