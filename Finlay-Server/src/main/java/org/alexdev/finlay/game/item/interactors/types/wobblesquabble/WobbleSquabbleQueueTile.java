package org.alexdev.finlay.game.item.interactors.types.wobblesquabble;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.games.wobblesquabble.WobbleSquabbleManager;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.pathfinder.Position;
import org.alexdev.finlay.game.room.entities.RoomEntity;
import org.alexdev.finlay.game.triggers.GenericTrigger;

public class WobbleSquabbleQueueTile extends GenericTrigger {
    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        if (roomEntity.getRoom().getTaskManager().hasTask(WobbleSquabbleManager.getInstance().getName())) {
            return;
        }

        roomEntity.walkTo(roomEntity.getPosition().getSquareInFront().getX(), roomEntity.getPosition().getSquareInFront().getY());
    }
}
