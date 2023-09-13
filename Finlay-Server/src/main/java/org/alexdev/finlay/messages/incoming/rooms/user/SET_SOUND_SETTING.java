package org.alexdev.finlay.messages.incoming.rooms.user;

import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class SET_SOUND_SETTING implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        boolean enabled = reader.readBoolean();
        player.getDetails().setSoundSetting(enabled);

        PlayerDao.saveSoundSetting(player.getDetails());
    }
}
