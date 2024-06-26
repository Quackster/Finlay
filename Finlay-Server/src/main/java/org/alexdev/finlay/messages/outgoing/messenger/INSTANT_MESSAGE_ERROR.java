package org.alexdev.finlay.messages.outgoing.messenger;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class INSTANT_MESSAGE_ERROR extends MessageComposer {
    private int errorCode;
    private int chatId;

    public INSTANT_MESSAGE_ERROR(int errorCode, int chatId) {
        this.errorCode = errorCode;
        this.chatId = chatId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.errorCode);
        response.writeInt(this.chatId);
    }

    @Override
    public short getHeader() {
        return 261; // "DE"
    }
}