package org.alexdev.finlay.messages.outgoing.wobblesquabble;

import org.alexdev.finlay.game.games.wobblesquabble.WobbleSquabblePlayer;
import org.alexdev.finlay.game.games.wobblesquabble.WobbleSquabbleStatus;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class PT_STATUS extends MessageComposer {
    private final WobbleSquabbleStatus[] statuses;

    public PT_STATUS(WobbleSquabblePlayer wsPlayer1, WobbleSquabblePlayer wsPlayer2) {
        this.statuses = new WobbleSquabbleStatus[2];
        this.statuses[0] = new WobbleSquabbleStatus(wsPlayer1.getPosition(), wsPlayer1.getBalance(), wsPlayer1.getMove(), wsPlayer1.isHit());
        this.statuses[1] = new WobbleSquabbleStatus(wsPlayer2.getPosition(), wsPlayer2.getBalance(), wsPlayer2.getMove(), wsPlayer2.isHit());
    }

    @Override
    public void compose(NettyResponse response) {
        for (int i = 0; i < 2; i++) {
            WobbleSquabbleStatus wsStatus = this.statuses[i];

            response.writeDelimeter(wsStatus.getPosition(), (char)9);
            response.writeDelimeter(wsStatus.getBalance(), (char)9);
            response.writeDelimeter(wsStatus.getMove().getLetter(), (char)9);
            response.writeDelimeter(wsStatus.isHit() ? "h" : "", (char)9);
            response.write((char)13);
        }
    }

    @Override
    public short getHeader() {
        return 118;
    }
}
