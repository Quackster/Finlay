package org.alexdev.finlay.messages.incoming.rooms.pool;

import org.alexdev.finlay.dao.mysql.CurrencyDao;
import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.interactors.InteractionType;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.messages.outgoing.catalogue.NO_CREDITS;
import org.alexdev.finlay.messages.outgoing.user.ALERT;
import org.alexdev.finlay.messages.outgoing.user.NO_USER_FOUND;
import org.alexdev.finlay.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.finlay.messages.outgoing.user.currencies.TICKET_BALANCE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class BTCKS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        int mode = reader.readInt();
        String ticketsFor = reader.readString();

        if (ticketsFor == null) {
            return;
        }

        int costCredits;
        int ticketsAmount;

        if (mode == 1) {
            costCredits = 1;
            ticketsAmount = 2;
        } else {
            costCredits = 6;
            ticketsAmount = 20;
        }

        if (costCredits > player.getDetails().getCredits()) {
            player.send(new NO_CREDITS());
            return;
        }

        int userId = PlayerDao.getId(ticketsFor);

        if (userId == -1) {
            player.send(new NO_USER_FOUND(ticketsFor));
            return;
        }

        PlayerDetails details = PlayerManager.getInstance().getPlayerData(userId);
        CurrencyDao.increaseTickets(details, ticketsAmount);

        Player ticketPlayer = PlayerManager.getInstance().getPlayerByName(ticketsFor);

        if (ticketPlayer != null) {
            if (userId != player.getDetails().getId()) {
                ticketPlayer.send(new ALERT(player.getDetails().getName() + " has gifted you tickets!"));
            }

            ticketPlayer.send(new TICKET_BALANCE(details.getTickets()));
        }

        player.getRoomUser().getTimerManager().resetRoomTimer();

        CurrencyDao.decreaseCredits(player.getDetails(), costCredits);
        player.send(new CREDIT_BALANCE(player.getDetails()));

        // Join queue after buying ticket
        if (player.getRoomUser().getRoom().getModel().getName().equals("md_a")) {
            Item item = player.getRoomUser().getCurrentItem();

            if (item != null && item.getDefinition().getInteractionType() == InteractionType.WS_JOIN_QUEUE) {
                item.getDefinition().getInteractionType().getTrigger().onEntityStop(player, player.getRoomUser(), item, false);
            }
        }
    }
}
