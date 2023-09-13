package org.alexdev.finlay.messages.incoming.user;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.user.LATENCY;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class TEST_LATENCY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        int latency = reader.readInt();
        player.send(new LATENCY(latency));
    }
}
