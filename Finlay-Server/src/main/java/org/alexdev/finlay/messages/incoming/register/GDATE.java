package org.alexdev.finlay.messages.incoming.register;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.handshake.AVAILABLE_SETS;
import org.alexdev.finlay.messages.outgoing.register.DATE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.DateUtil;
import org.alexdev.finlay.util.config.GameConfiguration;

public class GDATE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new DATE(DateUtil.getShortDate()));
    }
}
