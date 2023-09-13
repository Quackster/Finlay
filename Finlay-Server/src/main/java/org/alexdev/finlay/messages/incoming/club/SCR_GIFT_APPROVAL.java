package org.alexdev.finlay.messages.incoming.club;

import org.alexdev.finlay.game.club.ClubSubscription;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.log.Log;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.sql.SQLException;

public class SCR_GIFT_APPROVAL implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (ClubSubscription.isGiftDue(player)) {

            try {
                ClubSubscription.tryNextGift(player);
            } catch (SQLException e) {
                Log.getErrorLogger().error("Error trying to process club gift for user (" + player.getDetails().getName() + "): ", e);
            }
        }
    }
}
