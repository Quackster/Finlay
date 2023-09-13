package org.alexdev.finlay.messages.outgoing.messenger;

import org.alexdev.finlay.game.messenger.MessengerUser;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class ADD_FRIEND extends MessageComposer {
    private final MessengerUser friend;
    private final Player player;

    public ADD_FRIEND(MessengerUser friend, Player player) {
        this.friend = friend;
        this.player = player;
    }

    @Override
    public void compose(NettyResponse response) {
        this.friend.serialise(response);
    }

    @Override
    public short getHeader() {
        return 137; // "BI"
    }
}
