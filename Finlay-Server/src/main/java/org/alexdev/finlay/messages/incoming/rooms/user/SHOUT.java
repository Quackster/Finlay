package org.alexdev.finlay.messages.incoming.rooms.user;

import org.alexdev.finlay.game.commands.CommandManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.finlay.messages.outgoing.rooms.user.TYPING_STATUS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.StringUtil;

public class SHOUT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        String message = StringUtil.filterInput(reader.readString(), true);

        if (player.getRoomUser().isTyping()) {
            player.getRoomUser().setTyping(false);
            room.send(new TYPING_STATUS(player.getRoomUser().getInstanceId(), player.getRoomUser().isTyping()));
        }

        if (message.isEmpty()) {
            return;
        }

        if (CommandManager.getInstance().hasCommand(player, message)) {
            CommandManager.getInstance().invokeCommand(player, message);
            return;
        }

        player.getRoomUser().talk(message, CHAT_MESSAGE.ChatMessageType.SHOUT);
    }
}
