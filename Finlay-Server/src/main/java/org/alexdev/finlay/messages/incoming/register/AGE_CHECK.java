package org.alexdev.finlay.messages.incoming.register;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.register.AGE_CHECK_RESULT;
import org.alexdev.finlay.messages.outgoing.register.EMAIL_APPROVED;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class AGE_CHECK implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.isLoggedIn()) {
            player.send(new AGE_CHECK_RESULT());
        }
        else {
            player.send(new EMAIL_APPROVED());
        }
    }
}
