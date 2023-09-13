package org.alexdev.finlay.messages.incoming.catalogue;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.catalogue.ALIAS_TOGGLE;
import org.alexdev.finlay.messages.outgoing.catalogue.SPRITE_LIST;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GET_ALIAS_LIST implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        player.send(new SPRITE_LIST());
        player.send(new ALIAS_TOGGLE());
    }
}
