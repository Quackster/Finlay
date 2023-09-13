package org.alexdev.finlay.messages.incoming.moderation;

import org.alexdev.finlay.game.moderation.ModerationActionType;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class MODERATORACTION implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int targetType = reader.readInt();
        int actionType = reader.readInt();

        String alertMessage = reader.readString();
        String notes = reader.readString();

        for (ModerationActionType moderationActionType : ModerationActionType.values()) {
            if (moderationActionType.getTargetType() == targetType &&
                moderationActionType.getActionType() == actionType) {
                moderationActionType.getModerationAction().performAction(player, player.getRoomUser().getRoom(), alertMessage, notes, reader);
            }
        }
    }
}
