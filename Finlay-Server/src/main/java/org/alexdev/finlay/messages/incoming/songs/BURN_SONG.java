package org.alexdev.finlay.messages.incoming.songs;

import org.alexdev.finlay.dao.mysql.CurrencyDao;
import org.alexdev.finlay.dao.mysql.ItemDao;
import org.alexdev.finlay.dao.mysql.JukeboxDao;
import org.alexdev.finlay.dao.mysql.SongMachineDao;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.ItemManager;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.song.Song;
import org.alexdev.finlay.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.config.ServerConfiguration;

import java.util.Calendar;

public class BURN_SONG implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        // if (player.getVersion()  <= 14) {
        //     return;
        // }

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.getItemManager().getSoundMachine() == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        if (player.getDetails().getCredits() <= 0) {
            return;
        }

        int songId = reader.readInt();

        Song song = SongMachineDao.getSong(songId);

        if (song == null) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        Item item = new Item();
        item.setOwnerId(player.getDetails().getId());
        item.setDefinitionId(ItemManager.getInstance().getDefinitionBySprite("song_disk").getId());
        item.setCustomData(player.getDetails().getName() + (char)10 +
                cal.get(Calendar.DAY_OF_MONTH) + (char)10 +
                cal.get(Calendar.MONTH) + (char)10 +
                cal.get(Calendar.YEAR) + (char)10 +
                song.getLength() + (char)10 +
                song.getTitle());

        ItemDao.newItem(item);

        player.getInventory().addItem(item);
        player.getInventory().getView("new");

        JukeboxDao.addDisk(item.getId(), 0, songId);
        JukeboxDao.setBurned(songId, true);

        CurrencyDao.decreaseCredits(player.getDetails(), 1);
        player.send(new CREDIT_BALANCE(player.getDetails()));
    }
}
