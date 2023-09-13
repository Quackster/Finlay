package org.alexdev.finlay.game.item.interactors.types.wobblesquabble;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.games.wobblesquabble.WobbleSquabbleManager;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.interactors.InteractionType;
import org.alexdev.finlay.game.pathfinder.Position;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.entities.RoomEntity;
import org.alexdev.finlay.game.room.enums.StatusType;
import org.alexdev.finlay.game.room.mapping.RoomTile;
import org.alexdev.finlay.game.triggers.GenericTrigger;
import org.alexdev.finlay.messages.outgoing.user.ALERT;

public class WobbleSquabbleJoinQueue extends GenericTrigger {
    public void onEntityStep(Entity entity, RoomEntity roomEntity, Item item, Position oldPosition) {

    }

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        if (roomEntity.getRoom().getTaskManager().hasTask(WobbleSquabbleManager.getInstance().getName())) {
            return;
        }

        Player player = (Player) entity;

        if (player.getDetails().getTickets() < WobbleSquabbleManager.WS_GAME_TICKET_COST) {
            player.send(new ALERT("You need at least " + WobbleSquabbleManager.WS_GAME_TICKET_COST + " ticket(s) to play Wobble Squabble!"));
            return; // Too poor!
        }

        String[] teleportPositionData = item.getCurrentProgram().split(",");

        Position teleportPosition = new Position(
                Integer.valueOf(teleportPositionData[0]),
                Integer.valueOf(teleportPositionData[1])
        );

        teleportPosition.setRotation(Integer.valueOf(teleportPositionData[2]));
        RoomTile roomTile = roomEntity.getRoom().getMapping().getTile(teleportPosition);

        if (roomTile == null || roomTile.getEntities().size() > 0) {
            return;
        }

        roomEntity.removeStatus(StatusType.SWIM);
        roomEntity.warp(teleportPosition, true, false);

        InteractionType.WS_QUEUE_TILE.getTrigger().onEntityStop(entity, roomEntity, item, isRotation);
    }
}
