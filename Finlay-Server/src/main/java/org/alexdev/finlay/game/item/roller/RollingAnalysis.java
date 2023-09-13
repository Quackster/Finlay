package org.alexdev.finlay.game.item.roller;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.pathfinder.Position;
import org.alexdev.finlay.game.room.Room;

public interface RollingAnalysis<T> {
    public Position canRoll(T rollingType, Item roller, Room room);
    public void doRoll(T rollingType, Item roller, Room room, Position fromPosition, Position nextPosition);
}
