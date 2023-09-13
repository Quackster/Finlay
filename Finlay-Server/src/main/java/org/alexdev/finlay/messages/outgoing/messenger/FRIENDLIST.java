package org.alexdev.finlay.messages.outgoing.messenger;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.messenger.MessengerUser;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;
import org.alexdev.finlay.util.config.GameConfiguration;

import java.util.List;

public class FRIENDLIST extends MessageComposer {
    private final Player player;
    private final List<MessengerUser> friends;

    public FRIENDLIST(Player player, List<MessengerUser> friends) {
        this.player = player;
        this.friends = friends;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.friends.size());

        for (MessengerUser friend : this.friends) {
            friend.serialise(response);
        }
    }

    @Override
    public short getHeader() {
        return 263; // "DG"
    }
}