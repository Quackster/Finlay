package org.alexdev.finlay.messages.incoming.rooms.user;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.user.TYPING_STATUS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class USER_CANCEL_TYPING implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Room room = player.getRoomUser().getRoom();

        if (room == null || !player.getRoomUser().isTyping()) {
            return;
        }

        if (player.getRoomUser().getGamePlayer() != null &&
            player.getRoomUser().getGamePlayer().getGame() != null &&
            player.getRoomUser().getGamePlayer().isInGame()) {
            return;
        }

        player.getRoomUser().getTimerManager().stopChatBubbleTimer();
        player.getRoomUser().setTyping(false);

        room.send(new TYPING_STATUS(player.getRoomUser().getInstanceId(), player.getRoomUser().isTyping()));
    }
}
