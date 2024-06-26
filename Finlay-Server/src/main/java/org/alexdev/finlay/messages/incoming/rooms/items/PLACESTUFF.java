package org.alexdev.finlay.messages.incoming.rooms.items;

import org.alexdev.finlay.dao.mysql.ItemDao;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.texts.TextsManager;
import org.alexdev.finlay.messages.outgoing.user.ALERT;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

public class PLACESTUFF implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        String content = reader.contents();
        String[] data = content.split(" ");

        if (data.length == 0) {
            return;
        }

        // Make sure provided data is numeric
        if (!StringUtils.isNumeric(data[0])) {
            return;
        }

        int itemId = Integer.parseInt(data[0]);
        Item item = player.getInventory().getItem(itemId);

        if (item == null) {
            return;
        }

        if (item.hasBehaviour(ItemBehaviour.WALL_ITEM)) {
            String wallPosition = content.substring(data[0].length() + 1);

            if (item.hasBehaviour(ItemBehaviour.POST_IT)) {
                String defaultColour = "FFFF33";

                Item sticky = new Item();
                sticky.setOwnerId(room.getData().getOwnerId());
                sticky.setDefinitionId(item.getDefinition().getId());
                sticky.setCustomData(defaultColour);
                sticky.setWallPosition(wallPosition);
                sticky.setRoomId(room.getId());

                ItemDao.newItem(sticky);
                room.getMapping().addItem(player, sticky);

                // Set custom data as 1 for 1 post-it, if for some reason they have no number for the post-it.
                if (!StringUtils.isNumeric(item.getCustomData())) {
                    item.setCustomData("1");
                }

                if (StringUtils.isNumeric(item.getCustomData())) {
                    int totalStickies = Integer.parseInt(item.getCustomData()) - 1;

                    if (totalStickies <= 0) {
                        player.getInventory().getItems().remove(item);
                        item.delete();
                    } else {
                        item.setCustomData(String.valueOf(totalStickies));
                        item.save();
                    }
                }
                return;
            }

            item.setWallPosition(wallPosition);
        } else {
            int x = Integer.parseInt(data[1]);
            int y = Integer.parseInt(data[2]);
            int rotation = rotation = Integer.parseInt(data[5]);

            if (item.hasBehaviour(ItemBehaviour.REDIRECT_ROTATION_0)) {
                rotation = 0;
            }

            if (item.hasBehaviour(ItemBehaviour.REDIRECT_ROTATION_2)) {
                rotation = 2;
            }

            if (item.hasBehaviour(ItemBehaviour.REDIRECT_ROTATION_4)) {
                rotation = 4;
            }

            if (!item.isValidMove(item, room, player, x, y, rotation)) {
                return;
            }

            if (room.getMapping().getTile(x, y) != null) {
                item.getPosition().setX(x);
                item.getPosition().setY(y);
                item.getPosition().setRotation(rotation);
            }
        }

        if (room.getItemManager().getSoundMachine() != null && (item.hasBehaviour(ItemBehaviour.SOUND_MACHINE) || item.hasBehaviour(ItemBehaviour.JUKEBOX))) {
            player.send(new ALERT(TextsManager.getInstance().getValue("room_sound_furni_limit")));
            return;
        }

        if (room.getItemManager().getMoodlight() != null && (item.hasBehaviour(ItemBehaviour.ROOMDIMMER))) {
            player.send(new ALERT(TextsManager.getInstance().getValue("roomdimmer_furni_limit")));
            return;
        }

        room.getMapping().addItem(player, item);
        player.getInventory().getItems().remove(item);
    }
}
