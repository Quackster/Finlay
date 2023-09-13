package org.alexdev.finlay.messages.incoming.rooms.user;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class WHISPER implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        String contents = reader.readString();

        String username = contents.substring(0, contents.indexOf(" "));
        String message = contents.substring(username.length() + 1);

        if (message.isBlank()) {
            return;
        }

        List<Player> receieveMessages = new ArrayList<>();
        receieveMessages.add(player);

        Player whisperUser = PlayerManager.getInstance().getPlayerByName(username);

        if (whisperUser != null) {
            if (!whisperUser.getIgnoredList().contains(player.getDetails().getName())) {
                receieveMessages.add(whisperUser);
            }
        }

        player.getRoomUser().talk(message, CHAT_MESSAGE.ChatMessageType.WHISPER, receieveMessages);
    }
}
