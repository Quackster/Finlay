package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.dao.mysql.MessengerDao;
import org.alexdev.finlay.game.messenger.MessengerUser;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.sql.SQLException;

public class MESSENGER_DECLINEBUDDY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        boolean declineAll = reader.readBoolean();

        if (declineAll) {
            player.getMessenger().declineAllRequests();
            return;
        }

        int amount = reader.readInt();

        for (int i = 0; i < amount; i++) {
            int userId = reader.readInt();

            if (!player.getMessenger().hasRequest(userId)) {
                continue;
            }

            MessengerUser requester = player.getMessenger().getRequest(userId);
            player.getMessenger().declineRequest(requester);
        }
    }
}
