package org.alexdev.finlay.game.room.tasks;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.room.enums.StatusType;
import org.alexdev.finlay.messages.outgoing.rooms.user.USER_OBJECTS;
import org.alexdev.finlay.messages.outgoing.rooms.user.USER_STATUSES;

import java.util.List;

public class WaveTask implements Runnable {
    private final Entity entity;

    public WaveTask(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void run() {
        if (this.entity.getRoomUser().getRoom() == null) {
            return;
        }

        this.entity.getRoomUser().removeStatus(StatusType.WAVE);

        if (!this.entity.getRoomUser().isWalking()) {
            this.entity.getRoomUser().getRoom().send(new USER_STATUSES(List.of(this.entity)));
        }
    }
}
