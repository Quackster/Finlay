package org.alexdev.finlay.messages.outgoing.user;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.Set;

public class IGNORED_LIST extends MessageComposer {
    private final Set<String> ignoreList;

    public IGNORED_LIST(Set<String> ignoreList) {
        this.ignoreList = ignoreList;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.ignoreList.size());

        for (String username : this.ignoreList) {
            response.writeString(username);
        }
    }

    @Override
    public short getHeader() {
        return 420;
    }
}
