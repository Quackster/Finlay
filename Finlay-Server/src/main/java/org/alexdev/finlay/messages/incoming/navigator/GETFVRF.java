package org.alexdev.finlay.messages.incoming.navigator;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.messages.outgoing.navigator.FAVOURITEROOMRESULTS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.List;
import java.util.stream.Collectors;

public class GETFVRF implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        List<Room> favouriteRooms = RoomManager.getInstance().getFavouriteRooms(player.getDetails().getId());

        List<Room> favouritePublicRooms = favouriteRooms.stream().filter(Room::isPublicRoom).collect(Collectors.toList());
        List<Room> favouriteFlatRooms = favouriteRooms.stream().filter(room -> !room.isPublicRoom()).collect(Collectors.toList());

        player.send(new FAVOURITEROOMRESULTS(player, favouritePublicRooms,  favouriteFlatRooms));
    }
}
