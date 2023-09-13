package org.alexdev.finlay.messages.outgoing.songs;

import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.song.SongPlaylist;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.List;

public class SONG_PLAYLIST extends MessageComposer {
    private final List<SongPlaylist> songPlaylist;

    public SONG_PLAYLIST(List<SongPlaylist> songPlaylist) {
        this.songPlaylist = songPlaylist;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(0);
        response.writeInt(this.songPlaylist.size());

        int slotId = 1;
        for (SongPlaylist playlist : this.songPlaylist) {
            response.writeInt(playlist.getSong().getId());
            response.writeInt(playlist.getSong().getLength());
            response.writeString(playlist.getSong().getTitle());
            response.writeString(PlayerDao.getName(playlist.getSong().getUserId()));
            slotId++;
        }
    }

    @Override
    public short getHeader() {
        return 323; // "EC"
    }
}
