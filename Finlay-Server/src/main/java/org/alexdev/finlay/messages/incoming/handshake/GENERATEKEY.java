package org.alexdev.finlay.messages.incoming.handshake;

import org.alexdev.finlay.game.encryption.HabboHexRC4;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.handshake.AVAILABLE_SETS;
import org.alexdev.finlay.messages.outgoing.handshake.SECRET_KEY;
import org.alexdev.finlay.messages.outgoing.handshake.SESSION_PARAMETERS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.config.GameConfiguration;

public class GENERATEKEY implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) {
        if (player.isLoggedIn()) {
            return;
        }

        String secretKey = HabboHexRC4.generatePublicKeyString();

        player.setDecoder(secretKey);
        player.send(new SECRET_KEY(secretKey));

        // player.send(new SESSION_PARAMETERS(player.getDetails()));

        //if (player.getVersion() <= 17) {
        // player.send(new AVAILABLE_SETS("[" + GameConfiguration.getInstance().getString("users.figure.parts.default") + "]"));
        //}
    }
}
