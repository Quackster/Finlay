package org.alexdev.finlay.messages.outgoing.user.settings;

import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class ACCOUNT_PREFERENCES extends MessageComposer {
    private final PlayerDetails details;

    public ACCOUNT_PREFERENCES(PlayerDetails details) {
        this.details = details;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeBool(this.details.getSoundSetting());
        response.writeBool(!this.details.isTutorialFinished());
    }

    @Override
    public short getHeader() {
        return 308; // "Dt": [[#tutorial_handler, #handleAccountPreferences]]
    }
}