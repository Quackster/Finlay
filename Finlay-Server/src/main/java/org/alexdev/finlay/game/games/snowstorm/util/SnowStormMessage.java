package org.alexdev.finlay.game.games.snowstorm.util;

import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.game.games.snowstorm.SnowStormGame;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public interface SnowStormMessage {
    void handle(NettyRequest request, SnowStormGame snowStormGame, GamePlayer gamePlayer) throws Exception;
}
