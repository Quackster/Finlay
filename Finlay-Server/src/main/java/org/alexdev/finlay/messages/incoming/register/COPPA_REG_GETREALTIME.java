package org.alexdev.finlay.messages.incoming.register;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.register.COPPA_REALTIME;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class COPPA_REG_GETREALTIME implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
     player.send(new COPPA_REALTIME());
    }
}
