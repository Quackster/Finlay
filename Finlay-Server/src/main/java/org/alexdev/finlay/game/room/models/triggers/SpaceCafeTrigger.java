package org.alexdev.finlay.game.room.models.triggers;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.tasks.SpaceCafeTask;
import org.alexdev.finlay.game.triggers.GenericTrigger;

import java.util.concurrent.TimeUnit;

public class SpaceCafeTrigger extends GenericTrigger {
    @Override
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) {
        if (!firstEntry) {
            return;
        }

        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        room.getTaskManager().scheduleTask("SpaceCafeTask", new SpaceCafeTask(room), 0, 500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onRoomLeave(Entity entity, Room room, Object... customArgs) {

    }
}
