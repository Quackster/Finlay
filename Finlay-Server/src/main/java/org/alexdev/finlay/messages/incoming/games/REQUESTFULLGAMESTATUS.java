package org.alexdev.finlay.messages.incoming.games;

import org.alexdev.finlay.game.games.Game;
import org.alexdev.finlay.game.games.GameManager;
import org.alexdev.finlay.game.games.GameObject;
import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.game.games.snowstorm.SnowStormGame;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.games.FULLGAMESTATUS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.List;

public class REQUESTFULLGAMESTATUS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (gamePlayer == null) {
            return;
        }

        Game game = GameManager.getInstance().getGameById(gamePlayer.getGameId());

        if (!(game instanceof SnowStormGame)) {
            return;
        }

        player.send(new FULLGAMESTATUS(game));

        //player.send(new SNOWSTORM_FULLGAMESTATUS((SnowStormGame) game, gamePlayer, objects, snowStormGame.getExecutingEvents()));
    }
}
