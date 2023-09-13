package org.alexdev.finlay.messages.incoming.user;

import org.alexdev.finlay.dao.mysql.UsersMutesDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.messages.outgoing.user.IGNORED_LIST;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.List;

public class GET_IGNORE_LIST implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getIgnoredList().size() > 0) {
            return;
        }

        List<Integer> ignoreList = UsersMutesDao.getMutedUsers(player.getDetails().getId());

        for (int userId : ignoreList) {
            player.getIgnoredList().add(PlayerManager.getInstance().getPlayerData(userId).getName());
        }

        player.send(new IGNORED_LIST(player.getIgnoredList()));
    }
}
