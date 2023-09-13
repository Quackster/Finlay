package org.alexdev.finlay.game.triggers;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.games.GameManager;
import org.alexdev.finlay.game.games.enums.GameType;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.games.GAMEPLAYERINFO;
import org.alexdev.finlay.messages.outgoing.games.INSTANCELIST;
import org.alexdev.finlay.messages.types.MessageComposer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class GameLobbyTrigger extends GenericTrigger {
    public void onRoomEntry(Entity entity, Room room, Object... customArgs) { }
    public void onRoomLeave(Entity entity, Room room, Object... customArgs) { }

    public void showPoints(Player player, Room room) {
        room.send(new GAMEPLAYERINFO(this.getGameType(), List.of(player)));
    }

    public abstract void createGame(Player gameCreator, Map<String, Object> gameParameters);
    public abstract GameType getGameType();

    public MessageComposer getInstanceList() {
        return new INSTANCELIST(GameManager.getInstance().getGamesByType(this.getGameType()), GameManager.getInstance().getLastPlayedGames(this.getGameType()));
    }
}
