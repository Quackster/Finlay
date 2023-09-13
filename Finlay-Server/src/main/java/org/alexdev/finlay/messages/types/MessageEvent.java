package org.alexdev.finlay.messages.types;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public interface MessageEvent {
    
    /**
     * Handle the incoming client message.
     *
     * @param player the player
     * @param reader the reader
     */
    void handle(Player player, NettyRequest reader) throws Exception;
}
