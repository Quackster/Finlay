package org.alexdev.finlay.messages.incoming.user;

import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.dao.mysql.UsersMutesDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.user.IGNORE_USER_RESULT;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class IGNORE_USER implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        String username = reader.readString();

        if (player.getIgnoredList().contains(username)) {
            return;
        }

        int userId = PlayerDao.getId(username);
        UsersMutesDao.addMuted(player.getDetails().getId(), userId);

        player.getIgnoredList().add(username);
        player.send(new IGNORE_USER_RESULT(1));
    }
}
