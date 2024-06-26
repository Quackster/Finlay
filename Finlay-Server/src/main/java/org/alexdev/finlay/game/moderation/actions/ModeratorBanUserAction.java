package org.alexdev.finlay.game.moderation.actions;

import org.alexdev.finlay.dao.mysql.BanDao;
import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.GameScheduler;
import org.alexdev.finlay.game.ban.BanType;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.moderation.ModerationAction;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.moderation.USER_BANNED;
import org.alexdev.finlay.messages.outgoing.user.ALERT;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.DateUtil;

import java.util.concurrent.TimeUnit;

public class ModeratorBanUserAction implements ModerationAction {
    @Override
    public void performAction(Player player, Room room, String alertMessage, String notes, NettyRequest reader) {
        if (!player.hasFuse(Fuseright.BAN)) {
            return;
        }

        String name = reader.readString();
        int banHours = reader.readInt();
        boolean banMachineId = reader.readBoolean();
        boolean banIp = reader.readBoolean();

        if (banHours > 100000) {
            banHours = 100000;
        }

        if (banHours < 2) {
            banHours = 2;
        }

        PlayerDetails playerDetails = PlayerManager.getInstance().getPlayerData(name);

        if (playerDetails == null) {
            player.send(new ALERT("Could not find user: " + name));
            return;
        }

        if (playerDetails.isBanned() != null) {
            player.send(new ALERT("User is already banned!"));
            return;
        }

        long banTime = DateUtil.getCurrentTimeSeconds() + TimeUnit.HOURS.toSeconds(banHours);
        BanDao.addBan(BanType.USER_ID, String.valueOf(playerDetails.getId()), banTime, alertMessage);


        if (banIp) {
            BanDao.addBan(BanType.IP_ADDRESS, PlayerDao.getLatestIp(playerDetails.getId()), banTime, alertMessage);
        }

        Player target = PlayerManager.getInstance().getPlayerById(playerDetails.getId());

        if (target != null) {
            target.send(new USER_BANNED(alertMessage));
            GameScheduler.getInstance().getService().schedule(target::kickFromServer, 1, TimeUnit.SECONDS);
        }

        player.send(new ALERT("The user " + playerDetails.getName() + " has been banned."));
    }
}
