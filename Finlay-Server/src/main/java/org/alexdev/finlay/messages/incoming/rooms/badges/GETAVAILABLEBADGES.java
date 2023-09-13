package org.alexdev.finlay.messages.incoming.rooms.badges;

import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.rooms.badges.AVAILABLE_BADGES;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GETAVAILABLEBADGES implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        player.send(new AVAILABLE_BADGES(player.getDetails()));
    }
}