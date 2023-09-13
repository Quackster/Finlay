package org.alexdev.finlay.game.commands.registered;

import org.alexdev.finlay.game.commands.Command;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;

public class PacketTestCommand extends Command {
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

        String packet = String.join(" ", args);

        for (int i = 0; i < 14; i++) {
            packet = packet.replace("{" + i + "}", Character.toString((char)i));
        }

        // Add ending packet suffix
        packet += Character.toString((char)1);

        player.sendObject(packet);
    }

    @Override
    public String getDescription() {
        return "Tests a Habbo client-sided packet";
    }
}
