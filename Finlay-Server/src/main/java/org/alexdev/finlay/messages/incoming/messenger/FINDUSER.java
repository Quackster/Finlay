package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.dao.mysql.MessengerDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.messages.outgoing.messenger.MESSENGER_SEARCH;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class FINDUSER implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        String searchQuery = reader.readString();

        // if (player.getVersion() < 23) {
            int userId = MessengerDao.searchUser(searchQuery);
            player.send(new MESSENGER_SEARCH(PlayerManager.getInstance().getPlayerData(userId)));
        /*} else {
            List<Integer> userList = MessengerDao.search(searchQuery.toLowerCase());

            List<PlayerDetails> friends = new ArrayList<>();
            List<PlayerDetails> others = new ArrayList<>();

            for (int userId : userList) {
                if (player.getMessenger().hasFriend(userId)) {
                    friends.add(PlayerManager.getInstance().getPlayerData(userId));
                } else {
                    others.add(PlayerManager.getInstance().getPlayerData(userId));
                }
            }

            friends.removeIf(playerDetails -> playerDetails.getId() == player.getDetails().getId());
            others.removeIf(playerDetails -> playerDetails.getId() == player.getDetails().getId());

            player.send(new MESSENGER_SEARCH(friends, others));
        }*/
    }
}
