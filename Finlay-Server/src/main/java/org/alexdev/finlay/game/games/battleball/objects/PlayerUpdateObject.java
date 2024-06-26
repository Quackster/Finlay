package org.alexdev.finlay.game.games.battleball.objects;

import org.alexdev.finlay.game.games.GameObject;
import org.alexdev.finlay.game.games.enums.GameObjectType;
import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class PlayerUpdateObject extends GameObject {
    private final GamePlayer gamePlayer;

    public PlayerUpdateObject(GamePlayer gamePlayer) {
        super(gamePlayer.getPlayer().getDetails().getId(), GameObjectType.BATTLEBALL_PLAYER_OBJECT);
        this.gamePlayer = gamePlayer;
    }

    @Override
    public void serialiseObject(NettyResponse response) {
        response.writeInt(this.gamePlayer.getObjectId());
        response.writeInt(this.gamePlayer.getPlayer().getRoomUser().getPosition().getX());
        response.writeInt(this.gamePlayer.getPlayer().getRoomUser().getPosition().getY());
        response.writeInt((int) this.gamePlayer.getPlayer().getRoomUser().getPosition().getZ());
        response.writeInt(this.gamePlayer.getPlayer().getRoomUser().getPosition().getRotation());
        response.writeInt(this.gamePlayer.getPlayerState().getStateId());
        response.writeInt(this.gamePlayer.getColouringForOpponentId());
    }
}
