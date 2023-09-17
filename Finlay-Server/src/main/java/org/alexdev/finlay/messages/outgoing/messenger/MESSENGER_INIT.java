package org.alexdev.finlay.messages.outgoing.messenger;

import org.alexdev.finlay.game.messenger.MessengerMessage;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.messenger.Messenger;
import org.alexdev.finlay.game.messenger.MessengerUser;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;
import org.alexdev.finlay.util.DateUtil;
import org.alexdev.finlay.util.config.GameConfiguration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MESSENGER_INIT extends MessageComposer {
    private final Player player;
    private final List<MessengerMessage> messages;
    private final List<MessengerUser> requests;
    private String persistentMessage;
    private final int friendsLimit;
    private List<MessengerUser> friends;

    public MESSENGER_INIT(Player player, String persistentMessage, Messenger data) {
        this.player = player;
        this.persistentMessage = persistentMessage;
        this.friendsLimit = data.getFriendsLimit();
        this.friends = data.getFriends();
        this.messages = data.getOfflineMessages().values().stream().toList();
        this.requests = data.getRequests();
    }

    @Override
    public void compose(NettyResponse response) {
        int normalFriendsLimit = GameConfiguration.getInstance().getInteger("messenger.max.friends.nonclub");
        int clubFriendsLimit = GameConfiguration.getInstance().getInteger("messenger.max.friends.club");

        response.writeString(this.persistentMessage);
        response.writeInt(this.friendsLimit);
        response.writeInt(normalFriendsLimit);
        response.writeInt(clubFriendsLimit);

        response.writeInt(this.friends.size());

        for (MessengerUser friend : this.friends) {
            friend.serialise(response);
        }

        response.writeInt(this.messages.size());

        for (MessengerMessage msg : this.messages) {
            response.writeInt(msg.getId());
            response.writeInt(msg.getFromId());
            response.writeString(msg.getSenderDetails().getFigure());
            response.writeString(DateUtil.getDateAsString(msg.getTimeSet()));
            response.writeString(msg.getMessage());
        }

        response.writeInt(0); // campaign messages

        response.writeInt(this.requests.size());

        for (MessengerUser requester : this.requests) {
            response.writeInt(requester.getUserId());
            response.writeString(requester.getUsername());
        }
    }

    @Override
    public short getHeader() {
        return 12; // "@L"
    }
}
