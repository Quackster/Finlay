package org.alexdev.finlay.game.commands.registered;

import org.alexdev.finlay.dao.mysql.CurrencyDao;
import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.commands.Command;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.finlay.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;
import org.alexdev.finlay.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.apache.commons.lang3.math.NumberUtils;


import java.util.LinkedHashMap;
import java.util.Map;

public class GiveCreditsCommand extends Command {
    @Override
    public void addPermissions() {
        this.permissions.add(Fuseright.ADMINISTRATOR_ACCESS);
    }

    @Override
    public void addArguments() {
        this.arguments.add("user");
        this.arguments.add("credits");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        // :credits Patrick 300

        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Player targetUser = PlayerManager.getInstance().getPlayerByName(args[0]);

        if (targetUser == null) {
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Could not find user: " + args[0]));
            return;
        }

        if (args.length == 1) {
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Credit amount not provided"));
            return;
        }

        String credits = args[1];

        // credits should be numeric
        if (!NumberUtils.isCreatable(credits)) {
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Credit amount is not a number."));
            return;
        }

        PlayerDetails targetDetails = targetUser.getDetails();
        Map<PlayerDetails, Integer> playerDetailsToSave = new LinkedHashMap<>() {{
            put(targetDetails, Integer.parseInt(credits));
        }};

        CurrencyDao.increaseCredits(playerDetailsToSave);

        targetUser.send(new CREDIT_BALANCE(targetUser.getDetails()));

        player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), credits + " has been added to user " + targetDetails.getName()));
    }

    @Override
    public String getDescription() {
        return "Give credits to user";
    }
}
