package org.alexdev.finlay.game.commands.registered;

import org.alexdev.finlay.game.commands.Command;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.pathfinder.Position;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.items.PLACE_FLOORITEM;
import org.alexdev.finlay.util.StringUtil;

public class TalkCommand extends Command {
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

        String talkMessage;

        if (args.length > 0) {
            talkMessage = StringUtil.filterInput(String.join(" ", args), true);
        } else {
            talkMessage = "";
        }

        TalkCommand.createVoiceSpeakMessage(player.getRoomUser().getRoom(), talkMessage);
    }

    public static void createVoiceSpeakMessage(Room room, String text) {
        // 'Speaker'
        Item pItem = new Item();
        pItem.setId(Integer.MAX_VALUE);
        pItem.setPosition(new Position(255, 255, -1f));
        pItem.setCustomData("voiceSpeak(\"" + text + "\")");
        pItem.getDefinition().setSprite("spotlight");
        pItem.getDefinition().setLength(1);
        pItem.getDefinition().setWidth(1);
        room.send(new PLACE_FLOORITEM(pItem));
    }

    @Override
    public String getDescription() {
        return "Voice to text command";
    }
}
