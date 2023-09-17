package org.alexdev.finlay.game.messenger;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.game.player.PlayerManager;

public class MessengerMessage {
    private final Player sender;
    private final PlayerDetails senderDetails;
    private int id;
    private int toId;
    private int fromId;
    private long timeSet;
    private  String message;

    public MessengerMessage(int id, int toId, int fromId, long timeSet, String message) {
        this.id = id;
        this.toId = toId;
        this.fromId = fromId;
        this.timeSet = timeSet;
        this.message = message;
        this.sender = PlayerManager.getInstance().getPlayerById(fromId);
        this.senderDetails = PlayerManager.getInstance().getPlayerData(fromId);
    }

    public int getId() {
        return id;
    }

    public int getToId() {
        return toId;
    }

    public int getFromId() {
        return fromId;
    }

    public long getTimeSet() {
        return timeSet;
    }

    public String getMessage() {
        return message;
    }

    public Player getSender() {
        return sender;
    }

    public PlayerDetails getSenderDetails() {
        return senderDetails;
    }
}
