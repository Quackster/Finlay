package org.alexdev.finlay.game.commands.registered;

import org.alexdev.finlay.dao.mysql.SettingsDao;
import org.alexdev.finlay.game.commands.Command;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.user.ALERT;
import org.alexdev.finlay.util.config.GameConfiguration;
import org.alexdev.finlay.util.config.writer.GameConfigWriter;

public class SetConfigCommand extends Command {
    @Override
    public void addPermissions() {
        this.permissions.add(Fuseright.ADMINISTRATOR_ACCESS);
    }

    @Override
    public void addArguments() {
        this.arguments.add("setting");
        this.arguments.add("value");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        String setting = args[0];
        String value = args[1];

        if (!GameConfiguration.getInstance().getConfig().containsKey(setting)) {
            player.send(new ALERT("The setting \"" + setting + "\" doesn't exist!")); // TODO: Add locale
            return;
        }

        String oldValue = GameConfiguration.getInstance().getConfig().get(setting);

        SettingsDao.updateSetting(setting, value);
        GameConfiguration.reset(new GameConfigWriter());

        player.send(new ALERT("The setting \"" + setting + "\" value has been updated from \"" + oldValue + "\" to \"" + value + "\"")); // TODO: Add locale
    }

    @Override
    public String getDescription() {
        return "In-game housekeeping for the catalogue item prices.";
    }
}
