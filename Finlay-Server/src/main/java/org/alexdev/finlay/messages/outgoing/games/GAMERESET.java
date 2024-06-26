package org.alexdev.finlay.messages.outgoing.games;

import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.List;

public class GAMERESET extends MessageComposer {
    private int timeUntilGameStart;
    private List<GamePlayer> gamePlayerList;

    public GAMERESET(int timeUntilGameStart, List<GamePlayer> gamePlayerList) {
        this.timeUntilGameStart = timeUntilGameStart;
        this.gamePlayerList = gamePlayerList;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.timeUntilGameStart);
        response.writeInt(this.gamePlayerList.size());

        for (GamePlayer gamePlayer : this.gamePlayerList) {
            response.writeInt(gamePlayer.getObjectId());
            response.writeInt(gamePlayer.getPlayer().getRoomUser().getPosition().getX());
            response.writeInt(gamePlayer.getPlayer().getRoomUser().getPosition().getY());
            response.writeInt(gamePlayer.getPlayer().getRoomUser().getPosition().getRotation());
        }
    }

    @Override
    public short getHeader() {
        return 249;
    }
}
