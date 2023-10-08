package org.alexdev.finlay.messages.outgoing.rooms.items;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class IDATA extends MessageComposer {
    private String colour;
    private String text;
    private Item item;

    public IDATA(Item item, String colour, String text) {
        this.item = item;
        this.colour = colour;
        this.text = text;
    }

    public IDATA(Item item) {
        this.item = item;
    }

    @Override
    public void compose(NettyResponse response) {
        if (this.item.hasBehaviour(ItemBehaviour.POST_IT)) {
            response.writeDelimeter(this.item.getId(), (char) 9);
            response.writeDelimeter(this.colour, (char) 13);
            response.write(this.text);
        } else {
            response.writeDelimeter(this.item.getId(), (char) 9);
            response.writeDelimeter(this.item.getId(), ' ');
            response.writeDelimeter(this.item.getOwnerId(), ' ');
            response.write(this.item.getCustomData());
        }
    }

    @Override
    public short getHeader() {
        return 48; // "@p"
    }
}

