package org.alexdev.finlay.game.moderation.actions;

import org.alexdev.finlay.dao.mysql.ModerationDao;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.moderation.ModerationActionType;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.moderation.ModerationAction;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.user.ALERT;
import org.alexdev.finlay.messages.outgoing.user.MODERATOR_ALERT;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class ModeratorAlertUserAction implements ModerationAction {
    @Override
    public void performAction(Player player, Room room, String alertMessage, String notes, NettyRequest reader) {
        if (!player.hasFuse(Fuseright.ROOM_ALERT)) {
            return;
        }

        String alertUser = reader.readString();
        Player target = PlayerManager.getInstance().getPlayerByName(alertUser);

        if (target != null) {
            target.send(new MODERATOR_ALERT(alertMessage));
            ModerationDao.addLog(ModerationActionType.ALERT_USER, player.getDetails().getId(), target.getDetails().getId(), alertMessage, notes);
        } else {
            player.send(new ALERT("Target user is not online."));
        }
    }
}