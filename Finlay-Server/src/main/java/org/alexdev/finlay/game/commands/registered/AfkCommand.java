package org.alexdev.finlay.game.commands.registered;

import org.alexdev.finlay.game.commands.Command;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.enums.StatusType;
import org.alexdev.finlay.messages.outgoing.rooms.user.USER_STATUSES;

import java.util.List;

public class AfkCommand extends Command {
    @Override
    public void addPermissions() {
        this.permissions.add(Fuseright.DEFAULT);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        if (player.getRoomUser().isWalking()) {
            return;
        }

        if (!player.getRoomUser().containsStatus(StatusType.AVATAR_SLEEP)) {
            player.getRoomUser().removeDrinks();
            player.getRoomUser().setStatus(StatusType.AVATAR_SLEEP, "");
            player.getRoomUser().setNeedsUpdate(true);

            // Send immediate update to client
            player.send(new USER_STATUSES(List.of(player)));
        }
    }

    @Override
    public String getDescription() {
        return "Put your eyes to sleep";
    }
}
