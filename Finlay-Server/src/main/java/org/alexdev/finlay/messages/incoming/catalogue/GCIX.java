package org.alexdev.finlay.messages.incoming.catalogue;

import org.alexdev.finlay.game.catalogue.CatalogueManager;
import org.alexdev.finlay.game.catalogue.CataloguePage;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.catalogue.CATALOGUE_PAGES;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class GCIX implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        player.send(new CATALOGUE_PAGES(
                CatalogueManager.getInstance().getPagesForRank(player.getDetails().getRank(), player.getDetails().hasClubSubscription())
        ));
    }
}
