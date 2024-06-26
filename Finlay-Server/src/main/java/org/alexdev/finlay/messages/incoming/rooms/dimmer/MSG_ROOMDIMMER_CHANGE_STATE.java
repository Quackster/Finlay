package org.alexdev.finlay.messages.incoming.rooms.dimmer;

import org.alexdev.finlay.dao.mysql.MoodlightDao;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.finlay.messages.outgoing.rooms.user.CHAT_MESSAGE.ChatMessageType;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class MSG_ROOMDIMMER_CHANGE_STATE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        Item item = room.getItemManager().getMoodlight();

        if (item == null) {
            return;
        }

        if (!MoodlightDao.containsPreset(item.getId())) {
            MoodlightDao.createPresets(item.getId());
        }

        // Cancel RainbowTask because the operator decided to use their own moodlight settings.
        if (room.getTaskManager().hasTask("RainbowTask")) {
            room.getTaskManager().cancelTask("RainbowTask");
            player.send(new CHAT_MESSAGE(ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Rainbow room dimmer cycle has stopped"));
        }

        Pair<Integer, ArrayList<String>> presetData = MoodlightDao.getPresets(item.getId());

        int currentPreset = presetData.getLeft();
        ArrayList<String> presets = presetData.getRight();

        boolean isEnabled = !(item.getCustomData().charAt(0) == '2');
        item.setCustomData((isEnabled ? "2" : "1") + "," + currentPreset + "," + presets.get(currentPreset - 1));
        item.updateStatus();
        item.save();
    }
}