package org.alexdev.finlay.game.games.battleball.events;

import org.alexdev.finlay.game.games.GameEvent;
import org.alexdev.finlay.game.games.enums.GameEventType;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class DespawnObjectEvent extends GameEvent {
    private final int gameObjectId;

    public DespawnObjectEvent(int gameObjectId) {
        super(GameEventType.BATTLEBALL_DESPAWN_OBJECT);
        this.gameObjectId = gameObjectId;
    }

    @Override
    public void serialiseEvent(NettyResponse response) {
        response.writeInt(this.gameObjectId);
    }
}
