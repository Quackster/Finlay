package org.alexdev.finlay.messages.incoming.tutorial;

import org.alexdev.finlay.dao.mysql.TutorialDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class SET_TUTORIAL_MODE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int tutorialMode = reader.readInt();

        if (tutorialMode != 0 && tutorialMode != 1) {
            tutorialMode = 0;
        }

        boolean finishedTutorial = (tutorialMode == 0);

        player.getDetails().setTutorialFinished(finishedTutorial);
        TutorialDao.updateTutorialMode(player.getDetails().getId(), finishedTutorial);
    }
}
