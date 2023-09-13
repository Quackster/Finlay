package org.alexdev.finlay.messages.outgoing.navigator;

import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.List;

public class FLAT_NORESULTS extends MessageComposer {
    private final List<Room> roomList;
    private final Player player;

    public FLAT_NORESULTS(List<Room> roomList, Player player) {
        this.roomList = roomList;
        this.player = player;
    }

    @Override
    public void compose(NettyResponse response) {
        for (Room room : this.roomList) {
            response.writeDelimeter(room.getId(), (char) 9);
            response.writeDelimeter(room.getData().getName(), (char) 9);

            if (room.isOwner(this.player.getDetails().getId()) || room.getData().showOwnerName() || this.player.hasFuse(Fuseright.SEE_ALL_ROOMOWNERS)) {
                response.writeDelimeter(room.getData().getOwnerName(), (char) 9);
            } else {
                response.writeDelimeter("-", (char) 9);
            }

            response.writeDelimeter(room.getData().getAccessType(), (char) 9);
            response.writeDelimeter("x", (char) 9);
            response.writeDelimeter(room.getData().getVisitorsNow(), (char) 9);
            response.writeDelimeter(room.getData().getVisitorsMax(), (char) 9);
            response.writeDelimeter("null", (char) 9);
            response.writeDelimeter(room.getData().getDescription(), (char) 9);
            response.writeDelimeter(room.getData().getDescription(), (char) 9);
            response.write(Character.toString((char) 13));
        }
    }

    @Override
    public short getHeader() {
        return 55; // "@w"
    }
}
