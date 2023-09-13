package org.alexdev.finlay.messages.incoming.infobus;

import org.alexdev.finlay.game.infobus.InfobusManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.texts.TextsManager;
import org.alexdev.finlay.messages.outgoing.rooms.infobus.CANNOT_ENTER_BUS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class TRYBUS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        // Do not process public room items
        if (!player.getRoomUser().getRoom().isPublicRoom()) {
            return;
        }

        // If the infobus is open
        if(InfobusManager.getInstance().isDoorOpen()) {
            player.getRoomUser().walkTo(
                    InfobusManager.getInstance().getDoorX(),
                    InfobusManager.getInstance().getDoorY()
            ); // Walk to enter square
        } else {
            // Show infobus window and get infobus_title from database, if not tell the user whats going on.
            String infobusWindowTitle = TextsManager.getInstance().getValue("infobus_title");
            infobusWindowTitle = (!infobusWindowTitle.isEmpty()) ? infobusWindowTitle : "Unable to get 'infobus_title' from 'dbo.external_texts'";
            player.send(new CANNOT_ENTER_BUS(infobusWindowTitle));
        }
    }
}
