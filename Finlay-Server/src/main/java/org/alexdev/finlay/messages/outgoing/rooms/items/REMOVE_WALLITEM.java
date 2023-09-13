package org.alexdev.finlay.messages.outgoing.rooms.items;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class REMOVE_WALLITEM extends MessageComposer {
    private final Item item;

    public REMOVE_WALLITEM(Item item) {
        this.item = item;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.item.getId());
    }

    @Override
    public short getHeader() {
        return 84; // "AT"
    }
}
