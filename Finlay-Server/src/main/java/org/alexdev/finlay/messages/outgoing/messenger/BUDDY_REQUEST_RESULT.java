package org.alexdev.finlay.messages.outgoing.messenger;

import org.alexdev.finlay.game.messenger.MessengerError;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.ArrayList;
import java.util.List;

public class BUDDY_REQUEST_RESULT extends MessageComposer {
    private List<MessengerError> errors;

    public BUDDY_REQUEST_RESULT(List<MessengerError> errors) {
        this.errors = errors;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.errors.size());

        for (MessengerError error : this.errors) {
            response.writeString(error.getCauser().getUsername());
            response.writeInt(error.getErrorType().getErrorCode());
        }
    }

    @Override
    public short getHeader() {
        return 315; // "D{"
    }
}
