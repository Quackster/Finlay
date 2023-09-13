package org.alexdev.finlay.messages.outgoing.openinghours;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.time.Duration;
import java.time.LocalDateTime;

public class INFO_HOTEL_CLOSING extends MessageComposer {
    private Duration minutesUntil;

    public INFO_HOTEL_CLOSING(Duration minutesUntil) {
        this.minutesUntil = minutesUntil;
    }


    @Override
    public void compose(NettyResponse response) {
        response.writeInt(Math.toIntExact(minutesUntil.toMinutes()));
    }

    @Override
    public short getHeader() {
        return 291; // "Dc"
    }
}