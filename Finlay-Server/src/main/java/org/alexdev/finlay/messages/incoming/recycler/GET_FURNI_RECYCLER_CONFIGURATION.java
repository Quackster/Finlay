package org.alexdev.finlay.messages.incoming.recycler;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.recycler.RecyclerManager;
import org.alexdev.finlay.messages.outgoing.recycler.RECYCLER_CONFIGURATION;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GET_FURNI_RECYCLER_CONFIGURATION implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        /*

                config.put("recycler.timeout.seconds", "300");
        config.put("recycler.session.length.seconds", "3660");
        config.put("recycler.item.quarantine.seconds", "2592000");

         */
        player.send(new RECYCLER_CONFIGURATION(
                RecyclerManager.getInstance().isRecyclerEnabled(),
                RecyclerManager.getInstance().getRecyclerRewards(),
                RecyclerManager.getInstance().getRecyclerTimeoutSeconds(),
                RecyclerManager.getInstance().getRecyclerItemQuarantineSeconds(),
                RecyclerManager.getInstance().getRecyclerSessionLengthSeconds()
        ));
    }
}
