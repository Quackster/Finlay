package org.alexdev.finlay.messages.outgoing.games;

import org.alexdev.finlay.game.games.snowstorm.SnowStormTurn;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.List;

public class SNOWSTORM_GAMESTATUS extends MessageComposer {
    private final List<SnowStormTurn> turns;

    public SNOWSTORM_GAMESTATUS(List<SnowStormTurn> events) {
        this.turns = events;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(this.turns.size() == 0 ? 1 : this.turns.size());

        for (var turn : this.turns) {
            response.writeInt(turn.getSubTurns().size());

            for (var gameObject : turn.getSubTurns()) {
                gameObject.serialiseObject(response);
            }
        }
    }

    @Override
    public short getHeader() {
        return 244; // "Cs"
    }
}
