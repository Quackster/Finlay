package org.alexdev.finlay.game.item.interactors.types;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.entities.RoomEntity;
import org.alexdev.finlay.game.triggers.GenericTrigger;
import org.alexdev.finlay.messages.outgoing.rooms.pool.OPEN_UIMAKOPPI;

public class PoolBoothInteractor extends GenericTrigger {
    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)entity;

        player.getRoomUser().setWalkingAllowed(false);
        player.getRoomUser().getTimerManager().resetRoomTimer(120); // Only allow 120 seconds when changing clothes, to stop someone from just afking in the booth for 15 minutes.
        player.send(new OPEN_UIMAKOPPI());

        item.showProgram("close");
    }

    @Override
    public void onEntityLeave(Entity entity, RoomEntity roomEntity, Item item) {
        item.showProgram("open");
    }
}
