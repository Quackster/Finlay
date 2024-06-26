package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.dao.mysql.MessengerDao;
import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.messenger.Messenger;
import org.alexdev.finlay.game.messenger.MessengerError;
import org.alexdev.finlay.game.messenger.MessengerErrorType;
import org.alexdev.finlay.game.messenger.MessengerUser;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.messages.outgoing.messenger.BUDDY_REQUEST_RESULT;
import org.alexdev.finlay.messages.outgoing.messenger.FRIEND_REQUEST;
import org.alexdev.finlay.messages.outgoing.messenger.MESSENGER_ERROR;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.sql.SQLException;

public class MESSENGER_REQUESTBUDDY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        String username = reader.readString();
        Messenger target = PlayerManager.getInstance().getMessengerData(username);

        if (target == null) {
            // Error type in external texts has it defined as "There was an error finding the user for the friend request"
            player.send(new MESSENGER_ERROR(new MessengerError(MessengerErrorType.FRIEND_REQUEST_NOT_FOUND)));
            return;
        }

        Messenger callee = player.getMessenger();

        if (callee.isFriendsLimitReached()) {
            player.send(new MESSENGER_ERROR(new MessengerError(MessengerErrorType.FRIENDLIST_FULL)));
            return;
        }

        if (target.hasFriend(player.getDetails().getId())) {
            return;
        }

        if (target.hasRequest(player.getDetails().getId())) {
            return;
        }

        if (target.isFriendsLimitReached()) {
            player.send(new MESSENGER_ERROR(new MessengerError(MessengerErrorType.TARGET_FRIEND_LIST_FULL)));
            return;
        }

        if (!target.isAllowsFriendRequests()) {
            player.send(new MESSENGER_ERROR(new MessengerError(MessengerErrorType.TARGET_DOES_NOT_ACCEPT)));
            return;
        }

        target.addRequest(callee.getMessengerUser());
    }
}
