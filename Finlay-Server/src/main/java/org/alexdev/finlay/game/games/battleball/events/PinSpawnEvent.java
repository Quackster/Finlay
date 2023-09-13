package org.alexdev.finlay.game.games.battleball.events;

import org.alexdev.finlay.game.games.GameEvent;
import org.alexdev.finlay.game.games.battleball.objects.PinObject;
import org.alexdev.finlay.game.games.enums.GameEventType;
import org.alexdev.finlay.game.pathfinder.Position;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class PinSpawnEvent extends GameEvent {
    private final int id;
    private final Position position;

    public PinSpawnEvent(int id, Position position) {
        super(GameEventType.BATTLEBALL_OBJECT_SPAWN);
        this.id = id;
        this.position = position;
    }

    @Override
    public void serialiseEvent(NettyResponse response) {
        response.writeInt(2);
        new PinObject(this.id, this.position).serialiseObject(response);
    }
}
