package org.alexdev.finlay.game.room.tasks;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.game.pathfinder.Position;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.enums.StatusType;
import org.alexdev.finlay.messages.outgoing.rooms.items.MOVE_FLOORITEM;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RollerCompleteTask implements Runnable {
    private final Room room;
    private final Collection<Item> rollingItems;
    private final Set<Entity> rollingEntities;

    public RollerCompleteTask(Collection<Item> rollingItems, Set<Entity> rollingEntities, Room room) {
        this.rollingItems = rollingItems;
        this.rollingEntities = rollingEntities;
        this.room = room;
    }

    @Override
    public void run() {
        for (Item item : this.rollingItems) {
            if (item.getRollingData() == null) {
                continue;
            }

            /*if (item.getRollingData().getHeightUpdate() > 0) {
                item.getPosition().setZ(item.getPosition().getZ() + item.getRollingData().getHeightUpdate());
                this.room.send(new MOVE_FLOORITEM(item));
            }*/

            item.setCurrentRollBlocked(false);
            item.setRollingData(null);
        }

        for (Entity entity : this.rollingEntities) {
            if (entity.getRoomUser().getRollingData() == null) {
                continue;
            }

            entity.getRoomUser().invokeItem(null, false);
            entity.getRoomUser().setRollingData(null);
        }
    }
}

