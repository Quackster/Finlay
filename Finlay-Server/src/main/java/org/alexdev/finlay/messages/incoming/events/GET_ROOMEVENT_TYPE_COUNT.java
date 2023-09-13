package org.alexdev.finlay.messages.incoming.events;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.events.ROOMEVENT_TYPES;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.config.GameConfiguration;

public class GET_ROOMEVENT_TYPE_COUNT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new ROOMEVENT_TYPES(GameConfiguration.getInstance().getInteger("events.category.count")));
    }
}
