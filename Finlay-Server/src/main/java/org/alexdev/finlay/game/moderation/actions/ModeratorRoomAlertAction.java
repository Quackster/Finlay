package org.alexdev.finlay.game.moderation.actions;

import org.alexdev.finlay.dao.mysql.ModerationDao;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.moderation.ModerationAction;
import org.alexdev.finlay.game.moderation.ModerationActionType;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.user.MODERATOR_ALERT;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.List;

public class ModeratorRoomAlertAction implements ModerationAction {
    @Override
    public void performAction(Player player, Room room, String alertMessage, String notes, NettyRequest reader) {
        if (!player.hasFuse(Fuseright.ROOM_ALERT)) {
            return;
        }

        List<Player> players = player.getRoomUser().getRoom().getEntityManager().getPlayers();

        for (Player target : players) {
            target.send(new MODERATOR_ALERT(alertMessage));
        }

        ModerationDao.addLog(ModerationActionType.ROOM_ALERT, player.getDetails().getId(), -1, alertMessage, notes);
    }
}
