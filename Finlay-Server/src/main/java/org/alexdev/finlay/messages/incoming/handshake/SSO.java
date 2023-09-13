package org.alexdev.finlay.messages.incoming.handshake;

import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.user.LOCALISED_ERROR;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class SSO implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) {
        if (player.isLoggedIn()) {
            return;
        }

        String ticket = reader.readString();

        if (!PlayerDao.loginTicket(player, ticket)) {
            //player.send(new LOCALISED_ERROR("Incorrect SSO ticket"));
            player.kickFromServer();
        } else {
            player.login();
        }
    }
}
