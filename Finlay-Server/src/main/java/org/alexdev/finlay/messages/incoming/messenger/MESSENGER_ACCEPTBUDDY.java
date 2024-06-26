package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.game.messenger.Messenger;
import org.alexdev.finlay.game.messenger.MessengerError;
import org.alexdev.finlay.game.messenger.MessengerErrorType;
import org.alexdev.finlay.game.messenger.MessengerUser;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.messages.outgoing.messenger.ADD_FRIEND;
import org.alexdev.finlay.messages.outgoing.messenger.BUDDY_REQUEST_RESULT;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.config.ServerConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MESSENGER_ACCEPTBUDDY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        int userId = reader.readInt();
        acceptBuddy(player, userId);
    }

    private void acceptBuddy(Player player, int userId) {
        List<MessengerError> errors = new ArrayList<>();
        MessengerUser newBuddy = player.getMessenger().getRequest(userId);

        if (newBuddy == null) {
            MessengerError error = new MessengerError(MessengerErrorType.FRIEND_REQUEST_NOT_FOUND);
            error.setCauser(newBuddy);
            errors.add(error);
        }

        Messenger newBuddyData = PlayerManager.getInstance().getMessengerData(userId);

        if (newBuddyData == null) {
            // log warning
            return;
        } else if (player.getMessenger().isFriendsLimitReached()) {
            MessengerError error = new MessengerError(MessengerErrorType.FRIENDLIST_FULL);
            error.setCauser(newBuddy);

            errors.add(error);
        } else if (newBuddyData.isFriendsLimitReached()) {
            MessengerError error = new MessengerError(MessengerErrorType.TARGET_FRIEND_LIST_FULL);
            error.setCauser(newBuddy);

            errors.add(error);
        } else if (!newBuddyData.isAllowsFriendRequests()) {
            MessengerError error = new MessengerError(MessengerErrorType.TARGET_DOES_NOT_ACCEPT);
            error.setCauser(newBuddy);

            errors.add(error);
        }

        player.getMessenger().addFriend(newBuddy);
        player.send(new ADD_FRIEND(newBuddy, player));

        Player friend = PlayerManager.getInstance().getPlayerById(userId);

        if (friend != null) {
            MessengerUser meAsBuddy = player.getMessenger().getMessengerUser();

            friend.getMessenger().addFriend(meAsBuddy);
            friend.send(new ADD_FRIEND(meAsBuddy, player));
        }

        player.send(new BUDDY_REQUEST_RESULT(errors));
    }
}
