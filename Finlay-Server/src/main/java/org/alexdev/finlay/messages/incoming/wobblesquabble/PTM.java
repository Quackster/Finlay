package org.alexdev.finlay.messages.incoming.wobblesquabble;

import org.alexdev.finlay.game.games.wobblesquabble.WobbleSquabbleManager;
import org.alexdev.finlay.game.games.wobblesquabble.WobbleSquabbleMove;
import org.alexdev.finlay.game.games.wobblesquabble.WobbleSquabblePlayer;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class PTM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (!WobbleSquabbleManager.getInstance().isPlaying(player)) {
            return;
        }

        var move = WobbleSquabbleMove.getMove(reader.contents());

        if (move == null) {
            return;
        }

        WobbleSquabblePlayer wsPlayer = WobbleSquabbleManager.getInstance().getPlayer(player);

        if (wsPlayer == null) {
            return;
        }

        wsPlayer.setMove(move);
        wsPlayer.setRequiresUpdate(true);
    }
}
