package org.alexdev.finlay.messages.incoming.rooms.badges;

import org.alexdev.finlay.dao.mysql.BadgeDao;
import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.rooms.badges.USER_BADGE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class SETBADGE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        reader.readInt();

        String newBadge = reader.readString();

        if (!player.getDetails().getBadges().contains(newBadge)) {
            return;
        }

        boolean showBadge = reader.readBoolean();

        player.getDetails().setCurrentBadge(newBadge);
        player.getDetails().setShowBadge(showBadge);

        if (player.getRoomUser().getRoom() != null) {
            player.getRoomUser().getRoom().send(new USER_BADGE(player.getRoomUser().getInstanceId(), player.getDetails()));
        }

        BadgeDao.saveCurrentBadge(player.getDetails());
    }
}