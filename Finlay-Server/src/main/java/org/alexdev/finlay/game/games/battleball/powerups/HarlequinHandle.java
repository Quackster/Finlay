package org.alexdev.finlay.game.games.battleball.powerups;

import org.alexdev.finlay.game.GameScheduler;
import org.alexdev.finlay.game.games.battleball.BattleBallGame;
import org.alexdev.finlay.game.games.battleball.enums.BattleBallPlayerState;
import org.alexdev.finlay.game.games.battleball.objects.PlayerUpdateObject;
import org.alexdev.finlay.game.games.enums.GameState;
import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.game.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HarlequinHandle {
    public static void handle(BattleBallGame game, GamePlayer gamePlayer, Room room) {
        List<GamePlayer> affectedPlayers = new ArrayList<>();

        for (GamePlayer p : gamePlayer.getGame().getActivePlayers()) {
            if (p.getColouringForOpponentId() != -1 || p.getTeamId() == gamePlayer.getTeamId()) {
                continue;
            }

            if (p.getPlayerState() != BattleBallPlayerState.NORMAL) {
                continue; // Don't override people using power ups
            }

            p.setPlayerState(BattleBallPlayerState.COLORING_FOR_OPPONENT);
            p.setHarlequinPlayer(gamePlayer);

            game.addObjectToQueue(new PlayerUpdateObject(p));
            affectedPlayers.add(p);
        }

        GameScheduler.getInstance().getService().schedule(()-> {
            if (game.getGameState() == GameState.ENDED) {
                return;
            }

            for (GamePlayer p : affectedPlayers) {
                p.setPlayerState(BattleBallPlayerState.NORMAL);
                p.setHarlequinPlayer(null);

                game.addObjectToQueue(new PlayerUpdateObject(p));
            }

        }, 10, TimeUnit.SECONDS);
    }
}
