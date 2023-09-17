package org.alexdev.finlay.server.netty;

import io.netty.channel.Channel;
import org.alexdev.finlay.game.encryption.HabboHexRC4;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.codec.EncryptionDecoder;

import java.math.BigInteger;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NettyPlayerNetwork {
    private int port;
    private Channel channel;
    private int connectionId;

    public NettyPlayerNetwork(Channel channel, int connectionId) {
        this.channel = channel;
        this.connectionId = connectionId;
        this.port = Integer.parseInt(channel.localAddress().toString().split(":")[1]);
    }

    public void createEncryptionPipeline(HabboHexRC4 decoder) {
        this.channel.pipeline().addBefore("gameDecoder", "encryptionDecoder", new EncryptionDecoder(decoder));
    }

    public Channel getChannel() {
        return this.channel;
    }

    public int getPort() {
        return port;
    }

    public void send(Object response) {
        this.channel.writeAndFlush(response);
    }

    public void sendQueued(MessageComposer response) {
        this.channel.write(response);
    }

    public void flush() {
        this.channel.flush();
    }

    public void disconnect() {
        this.channel.close();
    }

    public int getConnectionId() {
        return connectionId;
    }

    public static String getIpAddress(Channel channel) {
        return channel.remoteAddress().toString().replace("/", "").split(":")[0];
    }

}
