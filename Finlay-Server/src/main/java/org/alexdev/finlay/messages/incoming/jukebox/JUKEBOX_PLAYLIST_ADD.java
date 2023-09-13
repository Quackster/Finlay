package org.alexdev.finlay.messages.incoming.jukebox;

import org.alexdev.finlay.dao.mysql.SongMachineDao;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.song.SongPlaylist;
import org.alexdev.finlay.game.song.jukebox.BurnedDisk;
import org.alexdev.finlay.game.song.jukebox.JukeboxManager;
import org.alexdev.finlay.messages.outgoing.songs.SONG_PLAYLIST;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JUKEBOX_PLAYLIST_ADD implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.getItemManager().getSoundMachine() == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
            //return;
        }

        int songId = reader.readInt();
        SongMachineDao.removePlaylistSong(songId, room.getItemManager().getSoundMachine().getId());

        var playList = SongMachineDao.getSongPlaylist(room.getItemManager().getSoundMachine().getId());
        var loadedDiscs = JukeboxManager.getInstance().getDisks(room.getItemManager().getSoundMachine().getId());

        // Don't load a song if it's not in the jukebox
        if (loadedDiscs.keySet().stream().noneMatch(disc -> disc.getSongId() == songId)) {
            return;
        }

        List<BurnedDisk> sortedDisks = new ArrayList<>(loadedDiscs.keySet());
        sortedDisks.sort(Comparator.comparingInt(BurnedDisk::getSlotId));

        int newSlotId = (sortedDisks.size() > 0 ? sortedDisks.get(0).getSlotId() : 0) + 1;

        SongMachineDao.addPlaylist(room.getItemManager().getSoundMachine().getId(), songId, newSlotId);
        playList.add(new SongPlaylist(room.getItemManager().getSoundMachine().getId(), SongMachineDao.getSong(songId), newSlotId));

        room.send(new SONG_PLAYLIST(playList));
    }
}
