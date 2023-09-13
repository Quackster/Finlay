package org.alexdev.finlay.messages.types;

import org.alexdev.finlay.server.netty.streams.NettyResponse;

public abstract class MessageComposer {
    /**
     * Write the message to send back to the client.
     */
    public abstract void compose(NettyResponse response);

    /**
     * Get the header
     */
    public abstract short getHeader();
}
