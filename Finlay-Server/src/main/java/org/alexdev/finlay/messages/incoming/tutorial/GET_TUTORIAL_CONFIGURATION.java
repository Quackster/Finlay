package org.alexdev.finlay.messages.incoming.tutorial;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.tutorial.TutorialTopic;
import org.alexdev.finlay.messages.outgoing.tutorial.TUTORIAL_CONFIGURATION;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.ArrayList;
import java.util.List;

public class GET_TUTORIAL_CONFIGURATION implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) {
        TutorialTopic topic = new TutorialTopic();
        topic.setId(1);
        topic.setName("own_user");
        topic.setStatus(0);

        List<TutorialTopic> topics = new ArrayList<>();
        topics.add(topic);

        player.send(new TUTORIAL_CONFIGURATION(1, "ur mom", topics));
    }
}