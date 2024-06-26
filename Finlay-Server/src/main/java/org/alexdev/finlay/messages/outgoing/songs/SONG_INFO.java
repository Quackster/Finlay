package org.alexdev.finlay.messages.outgoing.songs;

import org.alexdev.finlay.game.song.Song;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class SONG_INFO extends MessageComposer {
    private final Song song;

    public SONG_INFO(Song song) {
        this.song = song;
    }

    @Override
    public void compose(NettyResponse response) {
        // TODO: V15 toggle?
        // if (getPlayer().getVersion() > 14) {
         //   response.writeInt(this.song.getId());
         //   response.writeString(this.song.getTitle());
        //}

        response.writeString(this.song.getData());
    }

    @Override
    public short getHeader() {
        return 300; // "Dl"
    }
}
