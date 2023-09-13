package org.alexdev.finlay.messages.incoming.rooms;

import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.messages.outgoing.games.FULLGAMESTATUS;
import org.alexdev.finlay.messages.outgoing.navigator.CANTCONNECT;
import org.alexdev.finlay.messages.outgoing.navigator.CANTCONNECT.QueueError;
import org.alexdev.finlay.messages.outgoing.rooms.OPEN_CONNECTION;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class ROOM_DIRECTORY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        char is_public = reader.contents().charAt(0);

        if (is_public != 'A') {
            player.send(new OPEN_CONNECTION());
            return;
        }

        reader.readBytes(1); // strip 'A'
        int roomId = reader.readInt();

        Room room = null;

        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (roomId == -1 && gamePlayer != null && gamePlayer.isEnteringGame()) {
            room = gamePlayer.getGame().getRoom();
            room.getEntityManager().enterRoom(player, gamePlayer.getSpawnPosition());
        } else {
            room = RoomManager.getInstance().getRoomById(roomId);

            if (room == null) {
                return;
            }

            if (room.isClubOnly() && !player.getDetails().hasClubSubscription()) {
                player.send(new CANTCONNECT(QueueError.CLUB_ONLY));
                return;
            }

            if (room.getData().getTotalVisitorsNow() >= room.getData().getTotalVisitorsMax() && !player.hasFuse(Fuseright.ENTER_FULL_ROOMS)) {
                player.send(new CANTCONNECT(QueueError.FULL));
                return;
            }

            room.getEntityManager().enterRoom(player, null);
        }
     }
}
