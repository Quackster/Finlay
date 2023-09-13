package org.alexdev.finlay.messages.incoming.infobus;

import org.alexdev.finlay.game.infobus.InfobusManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class VOTE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        String vote = reader.contents();;
        InfobusManager.getInstance().addVote(Integer.parseInt(vote));
    }
}
