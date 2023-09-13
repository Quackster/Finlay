package org.alexdev.finlay.game.commands.registered;

import org.alexdev.finlay.game.commands.Command;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.messages.outgoing.user.ALERT;
import org.alexdev.finlay.util.StringUtil;

public class HotelAlertCommand extends Command {
    @Override
    public void addPermissions() {
        this.permissions.add(Fuseright.ADMINISTRATOR_ACCESS);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        // Concatenate all arguments
        String alert = StringUtil.filterInput(String.join(" ", args), true);

        // Send all players an alert
        PlayerManager.getInstance().sendAll(new ALERT(alert));
    }

    @Override
    public String getDescription() {
        return "Sends an alert hotel-wide";
    }
}