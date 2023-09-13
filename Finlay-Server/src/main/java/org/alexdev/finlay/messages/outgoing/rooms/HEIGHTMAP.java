package org.alexdev.finlay.messages.outgoing.rooms;

import org.alexdev.finlay.game.room.models.RoomModel;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;
import org.alexdev.finlay.util.StringUtil;

public class HEIGHTMAP extends MessageComposer {
    private final String heightmap;

    public HEIGHTMAP(String heightmap) {
        this.heightmap = heightmap;
    }

    public HEIGHTMAP(RoomModel roomModel) {
        this.heightmap = roomModel.getHeightmap();
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.heightmap);
    }

    @Override
    public short getHeader() {
        return 31; // "@_"
    }
}
