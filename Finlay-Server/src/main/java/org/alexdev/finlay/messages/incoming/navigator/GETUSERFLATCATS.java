package org.alexdev.finlay.messages.incoming.navigator;

import org.alexdev.finlay.game.navigator.NavigatorCategory;
import org.alexdev.finlay.game.navigator.NavigatorManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.navigator.USERFLATCATS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class GETUSERFLATCATS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        List<NavigatorCategory> categoryList = new ArrayList<>();

        for (NavigatorCategory category : NavigatorManager.getInstance().getCategories().values()) {
            if (category.isPublicSpaces()) {
                continue;
            }

            if (category.getMinimumRoleAccess().getRankId() > player.getDetails().getRank().getRankId()) {
                continue;
            }

            categoryList.add(category);
        }

        player.send(new USERFLATCATS(categoryList));
    }
}
