package org.alexdev.finlay.messages.incoming.jukebox;

import org.alexdev.finlay.dao.mysql.JukeboxDao;
import org.alexdev.finlay.dao.mysql.SongMachineDao;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.ItemManager;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.song.Song;
import org.alexdev.finlay.game.song.jukebox.JukeboxManager;
import org.alexdev.finlay.messages.outgoing.jukebox.JUKEBOX_DISCS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class ADD_JUKEBOX_DISC implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.getItemManager().getSoundMachine() == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        int itemId = reader.readInt();
        int slotId = reader.readInt();

        Item songDisk = null;

        for (Item item : player.getInventory().getItems()) {
            if (item.getId() == itemId && !item.isHidden()) {
                songDisk = item;
                break;
            }
        }

        if (songDisk == null) {
            return;
        }

        songDisk.setHidden(true);
        songDisk.save();

        player.getInventory().getView("new"); // Refresh hand

        int songId = JukeboxDao.getSongIdByItem(songDisk.getId());
        Song song = SongMachineDao.getSong(songId);

        if (song == null) {
            return;
        }

        if (slotId < 1 || slotId > 10) {
            return;
        }

        JukeboxDao.editDisk(songDisk.getId(), room.getItemManager().getSoundMachine().getId(), slotId);

        room.send(new JUKEBOX_DISCS(JukeboxManager.getInstance().getDisks(room.getItemManager().getSoundMachine().getId())));
        new GET_USER_SONG_DISCS().handle(player, null);
    }
}
