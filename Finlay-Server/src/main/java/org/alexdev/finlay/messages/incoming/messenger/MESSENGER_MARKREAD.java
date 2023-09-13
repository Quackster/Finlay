package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.dao.mysql.MessengerDao;
import org.alexdev.finlay.game.messenger.Messenger;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.sql.SQLException;

public class MESSENGER_MARKREAD implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException {
        int messageId = reader.readInt();

        MessengerDao.markMessageRead(messageId);
        player.getMessenger().getOfflineMessages().remove(messageId);
    }
}
