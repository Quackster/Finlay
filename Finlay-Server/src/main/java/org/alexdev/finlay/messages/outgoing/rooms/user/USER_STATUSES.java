package org.alexdev.finlay.messages.outgoing.rooms.user;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityState;
import org.alexdev.finlay.game.room.enums.StatusType;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;
import org.alexdev.finlay.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class USER_STATUSES extends MessageComposer {
    private List<EntityState> states;

    public USER_STATUSES(List<Entity> users) {
        createEntityStates(users);
    }

    private void createEntityStates(List<Entity> entities) {
        this.states = new ArrayList<>();

        for (Entity user : entities) {
            this.states.add(new EntityState(
                    user.getDetails().getId(),
                    user.getRoomUser().getInstanceId(),
                    user.getDetails(),
                    user.getType(),
                    user.getRoomUser().getRoom(),
                    user.getRoomUser().getPosition().copy(),
                    user.getRoomUser().getStatuses()));
        }
    }

    @Override
    public void compose(NettyResponse response) {
        for (EntityState states : states) {
            response.writeDelimeter(states.getInstanceId(), ' ');
            response.writeDelimeter(states.getPosition().getX(), ',');
            response.writeDelimeter(states.getPosition().getY(), ',');
            response.writeDelimeter(Double.toString(StringUtil.format(states.getPosition().getZ())), ',');
            response.writeDelimeter(states.getPosition().getHeadRotation(), ',');
            response.writeDelimeter(states.getPosition().getBodyRotation(), '/');

            for (var status : states.getStatuses().values()) {
                response.write(status.getKey().getStatusCode());

                if (status.getValue().length() > 0) {
                    response.write(" ");
                    response.write(status.getValue());
                }

                response.write("/");
            }

            response.write(Character.toString((char) 13));
        }
    }

    @Override
    public short getHeader() {
        return 34; // "@b"
    }
}
