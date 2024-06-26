package org.alexdev.finlay.messages.incoming.catalogue;

import org.alexdev.finlay.game.catalogue.CatalogueItem;
import org.alexdev.finlay.game.catalogue.CatalogueManager;
import org.alexdev.finlay.game.catalogue.CataloguePage;
import org.alexdev.finlay.game.catalogue.RareManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.catalogue.CATALOGUE_PAGE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.DateUtil;
import org.alexdev.finlay.util.config.GameConfiguration;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GCAP implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        String pageName = reader.contents().split("/")[1];

        CataloguePage cataloguePage = CatalogueManager.getInstance().getCataloguePage(pageName);

        if (cataloguePage == null) {
            return;
        }

        if (player.getDetails().getRank().getRankId() >= cataloguePage.getMinRole().getRankId()) {
            List<CatalogueItem> catalogueItemList = CatalogueManager.getInstance().getCataloguePageItems(cataloguePage.getId());

            if (RareManager.getInstance().getCurrentRare() != null &&
                    cataloguePage.getId() == GameConfiguration.getInstance().getInteger("rare.cycle.page.id")) {

                var currentRare = RareManager.getInstance().getCurrentRare();

                var rareItem = currentRare.copy();
                rareItem.setPrice(RareManager.getInstance().getRareCost().get(currentRare));
                catalogueItemList = List.of(rareItem);

                TimeUnit rareManagerUnit = TimeUnit.valueOf(GameConfiguration.getInstance().getString("rare.cycle.refresh.timeunit"));

                long interval = rareManagerUnit.toSeconds(GameConfiguration.getInstance().getInteger("rare.cycle.refresh.interval"));
                long currentTick = RareManager.getInstance().getTick().get();
                long timeUntil = interval - currentTick;

                cataloguePage.setBody(GameConfiguration.getInstance().getString("rare.cycle.page.text").replace("{rareCountdown}", DateUtil.getReadableSeconds(timeUntil)));
            }

            //Ai:Recycler{13}n:Recycler{13}l:ctlg_recycler{13}g:catalog_recycler_headline1{13}{1}
            player.send(new CATALOGUE_PAGE(
                    cataloguePage,
                    catalogueItemList));
        }
    }
}
