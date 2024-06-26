package org.alexdev.finlay.game.games.utils;

import org.alexdev.finlay.game.games.Game;
import org.alexdev.finlay.game.games.battleball.BattleBallTile;
import org.alexdev.finlay.game.games.battleball.enums.BattleBallColourState;
import org.alexdev.finlay.game.games.battleball.enums.BattleBallTileState;
import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.game.room.mapping.RoomTile;

public class TileUtil {
    public static boolean undoTileAttributes(BattleBallTile tile, Game game) {
        BattleBallTileState state = tile.getState();
        BattleBallColourState colour = tile.getColour();

        if (colour == BattleBallColourState.DEFAULT || state == BattleBallTileState.DEFAULT) {
            return false;
        }

        tile.getPointsReferece().clear();

        tile.setColour(BattleBallColourState.DEFAULT);
        tile.setState(BattleBallTileState.DEFAULT);

        /*int pointsToRemove = 0;

        if (state == BattleBallTileState.TOUCHED) {
            pointsToRemove = 2;
        }

        if (state == BattleBallTileState.CLICKED) {
            pointsToRemove = 6;
        }

        if (state == BattleBallTileState.PRESSED) {
            pointsToRemove = 10;
        }

        if (state == BattleBallTileState.SEALED) {
            pointsToRemove = 14;
        }

        GameTeam team = game.getTeams().get(colour.getColourId());

        if (pointsToRemove > 0) {
            int eachTeamRemove = team.getPlayers().size() / pointsToRemove;

            for (GamePlayer p : team.getPlayers()) {
                //p.setScore(p.getScore() - eachTeamRemove);
            }

            tile.setColour(BattleBallColourState.DEFAULT);
            tile.setState(BattleBallTileState.DEFAULT);
        }*/

        return true;
    }
    public static boolean isValidGameTile(GamePlayer gamePlayer, BattleBallTile tile, boolean checkEntities) {
        if (tile == null) {// && tile.getColour() != BattleBallColourState.DISABLED;
            return false;
        }

        return RoomTile.isValidTile(gamePlayer.getGame().getRoom(), checkEntities ? gamePlayer.getPlayer() : null, tile.getPosition());
    }
}
