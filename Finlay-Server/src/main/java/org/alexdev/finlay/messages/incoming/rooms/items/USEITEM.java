package org.alexdev.finlay.messages.incoming.rooms.items;

import org.alexdev.finlay.game.GameScheduler;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.enums.StatusType;
import org.alexdev.finlay.game.room.tasks.CameraTask;
import org.alexdev.finlay.messages.outgoing.rooms.user.USER_STATUSES;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class USEITEM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        if (!player.getRoomUser().containsStatus(StatusType.CARRY_ITEM)) {
            return;
        }
        
        String item = player.getRoomUser().getStatus(StatusType.CARRY_ITEM).getValue();

        player.getRoomUser().getStatuses().remove(StatusType.CARRY_ITEM.getStatusCode());
        player.getRoomUser().setStatus(StatusType.USE_ITEM, item);

        if (!player.getRoomUser().isWalking()) {
            player.getRoomUser().getRoom().send(new USER_STATUSES(List.of(player)));
        }

        GameScheduler.getInstance().getService().schedule(new CameraTask(player), 1, TimeUnit.SECONDS);
    }
}
