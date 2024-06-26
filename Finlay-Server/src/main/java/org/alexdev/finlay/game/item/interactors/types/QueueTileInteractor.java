package org.alexdev.finlay.game.item.interactors.types;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.pathfinder.Position;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.entities.RoomEntity;
import org.alexdev.finlay.game.room.tasks.StatusTask;
import org.alexdev.finlay.game.triggers.GenericTrigger;
import org.alexdev.finlay.messages.outgoing.user.currencies.NO_TICKETS;

public class QueueTileInteractor extends GenericTrigger {

    @Override
    public void onEntityStep(Entity entity, RoomEntity roomEntity, Item item, Position oldPosition) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)entity;

        if (!roomEntity.getRoom().getData().getModel().equals("pool_b")) {
            return;
        }
        if (player.getDetails().getTickets() == 0 || player.getDetails().getPoolFigure().isEmpty()) {
            oldPosition.setRotation(2); // Make user face this way, like the original Lido behaviour
            player.getRoomUser().stopWalking();
            player.getRoomUser().warp(oldPosition, false, false);

            if (player.getDetails().getTickets() == 0) {
                player.send(new NO_TICKETS());
            }
        }
    }

    @Override
    public void onEntityStop(Entity entity, RoomEntity roomEntity, Item item, boolean isRotation) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)entity;

        if (roomEntity.getRoom().getData().getModel().equals("pool_b")) {
            if (player.getDetails().getTickets() == 0 || player.getDetails().getPoolFigure().isEmpty()) {
                return;
            }
        }

        // When they stop walking, check if the player is on a pool lido queue and walk to the next one
        StatusTask.processPoolQueue(player);
    }
}

