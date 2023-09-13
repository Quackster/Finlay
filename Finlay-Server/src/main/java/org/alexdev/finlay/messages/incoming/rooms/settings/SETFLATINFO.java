package org.alexdev.finlay.messages.incoming.rooms.settings;

import org.alexdev.finlay.dao.mysql.RoomDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.StringUtil;

public class SETFLATINFO implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        String contents = reader.contents();

        if (contents.startsWith("/")) {
            contents = contents.substring(1);
        }

        int roomId = Integer.parseInt(contents.split("/")[0]);

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId())) {
            return;
        }

        String[] data = contents.split(Character.toString((char) 13));

        for (String setting : data) {
            int index = setting.indexOf("=");

            if (index == -1) {
                continue;
            }

            String key = setting.substring(0, index);
            String value = setting.substring(index + 1);

            if (key.startsWith("description")) {
                room.getData().setDescription(StringUtil.filterInput(value, true));
            }

            if (key.startsWith("allsuperuser")) {
                room.getData().setSuperUsers(Integer.parseInt(value) == 1);
            }

            if (key.startsWith("maxvisitors")) {
                int maxVisitors = Integer.parseInt(value);

                if (maxVisitors < 10 || maxVisitors > 50) {
                    maxVisitors = 25;
                }

                room.getData().setVisitorsMax(maxVisitors);
            }

            if (key.startsWith("password")) {
                room.getData().setPassword(StringUtil.filterInput(value, true));
            }
        }

        RoomDao.save(room);
    }
}
