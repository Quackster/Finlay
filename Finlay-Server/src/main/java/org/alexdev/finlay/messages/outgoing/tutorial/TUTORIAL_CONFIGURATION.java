package org.alexdev.finlay.messages.outgoing.tutorial;

import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.game.tutorial.TutorialTopic;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.List;

public class TUTORIAL_CONFIGURATION extends MessageComposer {
    private final int tutorialId;
    private final String tutorialName;
    private final List<TutorialTopic> topics;

    public TUTORIAL_CONFIGURATION(int tutorialId, String tutorialName, List<TutorialTopic> topics) {
        this.tutorialId = tutorialId;
        this.tutorialName = tutorialName;
        this.topics = topics;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.tutorialId);
        response.writeString(this.tutorialName);
        response.writeInt(this.topics.size());

        for (TutorialTopic topic : this.topics) {
            response.writeInt(topic.getId());
            response.writeString(topic.getName());
            response.writeInt(topic.getStatus());
        }
    }

    @Override
    public short getHeader() {
        return 327; // "Dt": [[#tutorial_handler, #handleTutorialConfig]]
    }
}