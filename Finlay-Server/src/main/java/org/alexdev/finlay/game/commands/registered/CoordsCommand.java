package org.alexdev.finlay.game.commands.registered;

import org.alexdev.finlay.game.commands.Command;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.user.ALERT;
import org.alexdev.finlay.util.StringUtil;

public class CoordsCommand extends Command {
    @Override
    public void addPermissions() {
        this.permissions.add(Fuseright.ADMINISTRATOR_ACCESS);
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

        player.send(new ALERT("Your coordinates:<br>" +
                "X: " + player.getRoomUser().getPosition().getX() + "<br>" +
                "Y: " + player.getRoomUser().getPosition().getY() + "<br>" +
                "Z: " + Double.toString(StringUtil.format(player.getRoomUser().getPosition().getZ())) + "<br>" + "<br>" +
                "Head rotation: " + player.getRoomUser().getPosition().getHeadRotation() + "<br>" +
                "Body rotation: " + player.getRoomUser().getPosition().getBodyRotation()));
    }

    @Override
    public String getDescription() {
        return "Shows the coordinates in the room";
    }
}
