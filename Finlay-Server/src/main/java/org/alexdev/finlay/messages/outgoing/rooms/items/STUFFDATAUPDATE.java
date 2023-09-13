package org.alexdev.finlay.messages.outgoing.rooms.items;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class STUFFDATAUPDATE extends MessageComposer {
    private final Item item;

    public STUFFDATAUPDATE(Item item) {
        this.item = item;
    }

    @Override
    public void compose(NettyResponse response) {
        if (this.item.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            this.item.serialise(response);
        } else {
            response.writeString(this.item.getId());
            response.writeString(this.item.getCustomData());
        }
    }

    @Override
    public short getHeader() {
        if (this.item.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            return 85; // "AU"
        } else {
            return 88; // "AX"
        }
    }
}
