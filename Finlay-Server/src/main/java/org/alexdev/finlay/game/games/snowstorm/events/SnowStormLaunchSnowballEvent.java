package org.alexdev.finlay.game.games.snowstorm.events;

import org.alexdev.finlay.game.games.GameObject;
import org.alexdev.finlay.game.games.enums.GameObjectType;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class SnowStormLaunchSnowballEvent extends GameObject {
    private final int objectId;
    private final int x;
    private final int y;
    private final int trajectory;
    private final int throwerId;

    public SnowStormLaunchSnowballEvent(int objectId, int throwerId, int x, int y, int trajectory) {
        super(objectId, GameObjectType.SNOWWAR_THROW_EVENT);
        this.objectId = objectId;
        this.throwerId = throwerId;
        this.x = x;
        this.y = y;
        this.trajectory = trajectory;
    }

    @Override
    public void serialiseObject(NettyResponse response) {
        response.writeInt(this.getGameObjectType().getObjectId());
        response.writeInt(this.objectId);
        response.writeInt(this.throwerId);
        response.writeInt(this.x);
        response.writeInt(this.y);
        response.writeInt(this.trajectory);
    }
}
