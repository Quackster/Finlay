package org.alexdev.finlay.messages.outgoing.rooms.user;

import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityState;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;
import org.alexdev.finlay.util.FigureUtil;
import org.alexdev.finlay.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class USER_OBJECTS extends MessageComposer {
    private List<EntityState> states;

    public USER_OBJECTS(ConcurrentLinkedQueue<Entity> entities) {
        createEntityStates(new ArrayList<>(entities));
    }

    public USER_OBJECTS(List<Entity> users) {
        createEntityStates(users);
    }

    public USER_OBJECTS(Entity entity) {
        createEntityStates(List.of(entity));
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
            response.write("\r");

            if (states.getEntityType() == EntityType.PET) {
                response.writeKeyValue("i", states.getInstanceId());
                response.writeKeyValue("n", states.getInstanceId() + Character.toString((char)4) + states.getDetails().getName());
                response.writeKeyValue("f", states.getDetails().getFigure());
                response.writeKeyValue("l", states.getPosition().getX() + " " + states.getPosition().getY() + " " + (int)states.getPosition().getZ());
                response.writeKeyValue("c", "");
            } else {
                response.writeKeyValue("i", states.getInstanceId());
                response.writeKeyValue("a", states.getEntityId());
                response.writeKeyValue("n", states.getDetails().getName());
                response.writeKeyValue("f", FigureUtil.renderFor(states.getDetails().getFigure(), -1));
                response.writeKeyValue("s", states.getDetails().getSex());
                response.writeKeyValue("l", states.getPosition().getX() + " " + states.getPosition().getY() + " " + Double.toString(StringUtil.format(states.getPosition().getZ())));

                if (states.getDetails().getMotto().length() > 0) {
                    response.writeKeyValue("c", states.getDetails().getMotto());
                }

                if (states.getDetails().getShowBadge()) {
                    response.writeKeyValue("b", states.getDetails().getCurrentBadge());
                }

                if (states.getRoom().getModel().getName().startsWith("pool_") ||
                        states.getRoom().getModel().getName().equals("md_a")) {

                    if (states.getDetails().getPoolFigure().length() > 0) {
                        response.writeKeyValue("p", states.getDetails().getPoolFigure());
                    }
                }

                if (states.getEntityType() == EntityType.BOT) {
                    response.writeDelimeter("[bot]", (char) 13);
                }
            }
        }
    }

    @Override
    public short getHeader() {
        return 28; // "@\"
    }
}
