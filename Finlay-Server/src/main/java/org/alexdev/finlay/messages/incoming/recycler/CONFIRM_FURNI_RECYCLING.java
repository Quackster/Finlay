package org.alexdev.finlay.messages.incoming.recycler;

import org.alexdev.finlay.dao.mysql.RecyclerDao;
import org.alexdev.finlay.game.catalogue.CatalogueManager;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.recycler.RecyclerSession;
import org.alexdev.finlay.messages.outgoing.recycler.START_RECYCLING_RESULT;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.DateUtil;

import java.util.List;

public class CONFIRM_FURNI_RECYCLING implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        RecyclerSession recyclerSession = RecyclerDao.getSession(player.getDetails().getId());

        if (recyclerSession == null) {
            return;
        }

        boolean isCancel = !reader.readBoolean();

        if (!isCancel) {
            if (!recyclerSession.isRecyclingDone() || recyclerSession.hasTimeout()) {
                return;
            }
        }

        for (int itemId : recyclerSession.getItems()) {
            Item item = player.getInventory().getItem(itemId);

            if (item == null) {
                continue;
            }

            if (isCancel) {
                item.setHidden(false);
                item.save();

                player.getInventory().addItem(item);
            } else {
                player.getInventory().getItems().remove(item);
                item.delete();
            }
        }

        RecyclerDao.deleteSession(player.getDetails().getId());
        if (!isCancel) {
            List<Item> itemList = CatalogueManager.getInstance().purchase(player, recyclerSession.getRecyclerReward().getCatalogueItem(), null, null, DateUtil.getCurrentTimeSeconds());

            if (!itemList.isEmpty()) {
                player.getInventory().getView("new");
            }

        }
        player.send(new START_RECYCLING_RESULT(true));
        player.getInventory().getView("new");
    }
}
