package org.alexdev.finlay.messages.outgoing.songs;

import org.alexdev.finlay.game.song.Song;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.List;

public class SONG_LIST extends MessageComposer {
    private final List<Song> songList;

    public SONG_LIST(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.songList.size());

        for (Song song : this.songList) {
            response.writeInt(song.getId());
            response.writeInt(song.getLength());
            response.writeString(song.getTitle());
            response.writeBool(song.isBurnt());
        }
    }

    @Override
    public short getHeader() {
        return 322; // "EB"
    }
}
