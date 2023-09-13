package org.alexdev.finlay.messages.incoming.moderation;

import org.alexdev.finlay.game.moderation.cfh.CallForHelp;
import org.alexdev.finlay.game.moderation.cfh.CallForHelpManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.moderation.CFH_ACK;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class REQUEST_CFH implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        // Retrieve open calls from the current user
        CallForHelp call = CallForHelpManager.getInstance().getPendingCall(player.getDetails().getId());

        // Send details
        player.send(new CFH_ACK(call));
    }
}
