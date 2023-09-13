package org.alexdev.finlay.messages.incoming.jukebox;

import org.alexdev.finlay.dao.mysql.JukeboxDao;
import org.alexdev.finlay.dao.mysql.SongMachineDao;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.song.jukebox.BurnedDisk;
import org.alexdev.finlay.game.song.jukebox.JukeboxManager;
import org.alexdev.finlay.messages.outgoing.jukebox.JUKEBOX_DISCS;
import org.alexdev.finlay.messages.outgoing.songs.SONG_PLAYLIST;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class REMOVE_JUKEBOX_DISC implements MessageEvent {
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

        int slotId = reader.readInt();
        BurnedDisk burnedDisk = JukeboxDao.getDisk(room.getItemManager().getSoundMachine().getId(), slotId);

        if (burnedDisk == null) {
            return;
        }

        Item songDisk = null;

        for (Item item : player.getInventory().getItems()) {
            if ((burnedDisk.getItemId() == item.getId()) && item.isHidden()) {
                songDisk = item;
                break;
            }
        }

        if (songDisk == null) {
            return;
        }

        songDisk.setHidden(false);
        player.getInventory().addItem(songDisk); // Re-add at start.
        songDisk.save();

        SongMachineDao.removePlaylistSong(burnedDisk.getSongId(), room.getItemManager().getSoundMachine().getId());
        JukeboxDao.editDisk(songDisk.getId(), 0, 0);

        player.getInventory().getView("new"); // Refresh hand
        new GET_USER_SONG_DISCS().handle(player, null);

        room.send(new SONG_PLAYLIST(SongMachineDao.getSongPlaylist(room.getItemManager().getSoundMachine().getId())));
        room.send(new JUKEBOX_DISCS(JukeboxManager.getInstance().getDisks(room.getItemManager().getSoundMachine().getId())));
    }
}
