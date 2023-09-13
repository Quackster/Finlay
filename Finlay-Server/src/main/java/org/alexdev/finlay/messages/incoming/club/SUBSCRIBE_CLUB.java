package org.alexdev.finlay.messages.incoming.club;

import org.alexdev.finlay.game.club.ClubSubscription;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.club.CLUB_GIFT;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class SUBSCRIBE_CLUB implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        reader.readString();

        int days = -1;
        int credits = -1;

        int choice = reader.readInt();

        switch (choice) {
            case 1:
            {
                credits = 25;
                days = 31;
                break;
            }
            case 2:
            {
                credits = 60;
                days = 93;
                break;
            }
            case 3:
            {
                credits = 105;
                days = 186;
                break;
            }
        }

        ClubSubscription.subscribeClub(player, days, credits);

        if (ClubSubscription.isGiftDue(player)) {
            player.send(new CLUB_GIFT(1));
        }
    }
}
