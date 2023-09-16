package org.alexdev.finlay.messages.outgoing.inventory;

import org.alexdev.finlay.game.inventory.Inventory;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.Map;

public class INVENTORY extends MessageComposer {
    private final Inventory inventory;
    private final Map<Integer, Item> casts;

    public INVENTORY(Inventory inventory, Map<Integer, Item> casts) {
        this.inventory = inventory;
        this.casts = casts;
    }

    @Override
    public void compose(NettyResponse response) {
        for (var kvp : this.casts.entrySet()) {
            Inventory.serialise(response, kvp.getValue(), kvp.getKey());
        }

        response.writeDelimeter((char)13, this.inventory.getItems().size());
    }

    @Override
    public short getHeader() {
        return 140; // "BL"
    }
}
