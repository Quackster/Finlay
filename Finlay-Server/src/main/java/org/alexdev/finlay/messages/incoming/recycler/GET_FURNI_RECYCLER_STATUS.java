package org.alexdev.finlay.messages.incoming.recycler;

import org.alexdev.finlay.dao.mysql.RecyclerDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.recycler.RecyclerManager;
import org.alexdev.finlay.messages.outgoing.recycler.RECYCLER_STATUS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GET_FURNI_RECYCLER_STATUS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new RECYCLER_STATUS(
                RecyclerManager.getInstance().isRecyclerEnabled(),
                RecyclerDao.getSession(player.getDetails().getId())));
    }
}
