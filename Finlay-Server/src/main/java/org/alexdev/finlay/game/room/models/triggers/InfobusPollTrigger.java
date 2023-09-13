package org.alexdev.finlay.game.room.models.triggers;

import org.alexdev.finlay.game.infobus.InfobusManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.triggers.GenericTrigger;

public class InfobusPollTrigger extends GenericTrigger {
    @Override
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;
        InfobusManager.getInstance().addPlayer(player.getDetails().getId());
    }

    @Override
    public void onRoomLeave(Entity entity, Room room, Object... customArgs) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;
        InfobusManager.getInstance().removePlayer(player.getDetails().getId());
    }
}
