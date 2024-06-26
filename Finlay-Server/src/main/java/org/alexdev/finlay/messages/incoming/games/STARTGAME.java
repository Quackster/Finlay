package org.alexdev.finlay.messages.incoming.games;

import org.alexdev.finlay.game.games.Game;
import org.alexdev.finlay.game.games.GameManager;
import org.alexdev.finlay.game.games.enums.GameState;
import org.alexdev.finlay.game.games.enums.GameType;
import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.triggers.GameLobbyTrigger;
import org.alexdev.finlay.messages.outgoing.games.CREATEFAILED;
import org.alexdev.finlay.messages.outgoing.games.STARTFAILED;
import org.alexdev.finlay.messages.outgoing.user.ALERT;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class STARTGAME implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (!(room.getModel().getRoomTrigger() instanceof GameLobbyTrigger)) {
            return;
        }

        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (gamePlayer == null) {
            return;
        }

        Game game = GameManager.getInstance().getGameById(gamePlayer.getGameId());

        if (game.getGameState() != GameState.WAITING) {
            return;
        }

        if (game.getGameCreatorId() != player.getDetails().getId()) {
            return;
        }

        if (!game.canGameStart()) {
            if (game.getGameType() == GameType.SNOWSTORM && game.getTeamAmount() == 1) {
                player.send(new ALERT("There needs to be at least two players to start this match"));
            } else {
                player.send(new STARTFAILED(STARTFAILED.FailedReason.MINIMUM_TEAMS_REQUIRED, null));
            }
            return;
        }

        game.startGame();
    }
}