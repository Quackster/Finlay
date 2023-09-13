package org.alexdev.finlay.game.room.tasks;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.items.BROADCAST_TELEPORTER;
import org.alexdev.finlay.messages.outgoing.rooms.user.USER_STATUSES;

import java.util.List;

public class TeleporterTask implements Runnable {
    private final Item item;
    private final Entity entity;
    private final Room room;

    public TeleporterTask(Item linkedTeleporter, Entity entity, Room room) {
        this.item = linkedTeleporter;
        this.entity = entity;
        this.room = room;
    }

    @Override
    public void run() {
        this.entity.getRoomUser().warp(this.item.getPosition().copy(), true, false);

        if (this.entity.getType() == EntityType.PLAYER) {
            Player player = (Player) this.entity;
            player.getRoomUser().setAuthenticateTelporterId(-1);
        }

        this.room.send(new BROADCAST_TELEPORTER(this.item, this.entity.getDetails().getName(), false));
    }
}
