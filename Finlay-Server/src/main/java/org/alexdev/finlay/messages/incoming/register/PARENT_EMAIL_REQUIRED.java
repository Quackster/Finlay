package org.alexdev.finlay.messages.incoming.register;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.register.PARENT_EMAIL_VERIFIED;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class PARENT_EMAIL_REQUIRED implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new PARENT_EMAIL_VERIFIED(1));
    }
}
