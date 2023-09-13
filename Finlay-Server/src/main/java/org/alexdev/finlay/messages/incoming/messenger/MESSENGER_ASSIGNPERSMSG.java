package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.messenger.CONSOLE_MOTTO;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.StringUtil;

public class MESSENGER_ASSIGNPERSMSG implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        String consoleMotto = StringUtil.filterInput(reader.readString(), true);

        player.getMessenger().setPersistentMessage(consoleMotto);
    }
}
