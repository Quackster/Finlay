package org.alexdev.finlay.game.games.snowstorm.events;

import org.alexdev.finlay.game.games.GameObject;
import org.alexdev.finlay.game.games.enums.GameObjectType;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class SnowStormThrowEvent extends GameObject {
    private final int objectId;
    private final int x;
    private final int y;
    private final int throwHeight;

    public SnowStormThrowEvent(int objectId, int x, int y, int throwHeight) {
        super(objectId, GameObjectType.SNOWWAR_TARGET_THROW_EVENT);
        this.objectId = objectId;
        this.x = x;
        this.y = y;
        this.throwHeight = throwHeight;
    }

    @Override
    public void serialiseObject(NettyResponse response) {
        response.writeInt(this.getGameObjectType().getObjectId());
        response.writeInt(this.objectId);
        response.writeInt(this.x);
        response.writeInt(this.y);
        response.writeInt(this.throwHeight);
    }
}
