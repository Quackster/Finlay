package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.game.messenger.Messenger;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.messages.outgoing.messenger.MESSENGER_INIT;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.config.ServerConfiguration;

public class MESSENGERINIT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Messenger messenger = PlayerManager.getInstance().getMessengerData(player.getDetails().getId());
        //player.send(new MESSENGER_INIT(player, player.getDetails().getConsoleMotto(), messenger));
    }
}
