package org.alexdev.finlay.game.games.battleball.powerups;

import org.alexdev.finlay.game.GameScheduler;
import org.alexdev.finlay.game.games.battleball.BattleBallGame;
import org.alexdev.finlay.game.games.battleball.enums.BattleBallPlayerState;
import org.alexdev.finlay.game.games.battleball.objects.PlayerUpdateObject;
import org.alexdev.finlay.game.games.enums.GameState;
import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.game.room.Room;

import java.util.concurrent.TimeUnit;

public class SpringHandle {
    public static void handle(BattleBallGame game, GamePlayer gamePlayer, Room room) {
        gamePlayer.setPlayerState(BattleBallPlayerState.HIGH_JUMPS);
        game.addObjectToQueue(new PlayerUpdateObject(gamePlayer));

        GameScheduler.getInstance().getService().schedule(()-> {
            if (game.getGameState() == GameState.ENDED) {
                return;
            }

            if (gamePlayer.getPlayerState() != BattleBallPlayerState.HIGH_JUMPS) {
                return;
            }

            gamePlayer.setPlayerState(BattleBallPlayerState.NORMAL);
            game.addObjectToQueue(new PlayerUpdateObject(gamePlayer));
        }, 10, TimeUnit.SECONDS);
    }
}
