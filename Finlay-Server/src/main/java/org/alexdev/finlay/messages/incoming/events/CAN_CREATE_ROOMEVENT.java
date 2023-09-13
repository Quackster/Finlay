package org.alexdev.finlay.messages.incoming.events;

import org.alexdev.finlay.game.events.EventsManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.events.ROOMEVENT_PERMISSION;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class CAN_CREATE_ROOMEVENT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        boolean canCreateEvent = EventsManager.getInstance().canCreateEvent(player);
        player.send(new ROOMEVENT_PERMISSION(canCreateEvent));
    }
}
