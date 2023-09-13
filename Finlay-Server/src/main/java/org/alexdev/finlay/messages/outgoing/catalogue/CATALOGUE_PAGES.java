package org.alexdev.finlay.messages.outgoing.catalogue;

import org.alexdev.finlay.game.catalogue.CataloguePage;
import org.alexdev.finlay.game.player.PlayerRank;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.List;

public class CATALOGUE_PAGES extends MessageComposer {
    private final List<CataloguePage> cataloguePages;

    public CATALOGUE_PAGES(List<CataloguePage> cataloguePages) {
        this.cataloguePages = cataloguePages;
    }

    @Override
    public void compose(NettyResponse response) {
        for (CataloguePage page : this.cataloguePages) {
            response.writeDelimeter(page.getNameIndex(), (char) 9);
            response.writeDelimeter(page.getName(), (char) 13);
        }
    }

    @Override
    public short getHeader() {
        return 126; // "A~"
    }
}
