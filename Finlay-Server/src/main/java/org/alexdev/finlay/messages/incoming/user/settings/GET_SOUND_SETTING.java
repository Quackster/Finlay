package org.alexdev.finlay.messages.incoming.user.settings;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.user.settings.SOUND_SETTING;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GET_SOUND_SETTING implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) {
        player.send(new SOUND_SETTING(player.getDetails()));
    }
}