package org.alexdev.finlay.server.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.alexdev.finlay.game.encryption.HabboHexRC4;
import org.alexdev.finlay.util.StringUtil;

import java.util.List;

public class EncryptionDecoder extends ByteToMessageDecoder {
    private HabboHexRC4 decoder;

    public EncryptionDecoder(HabboHexRC4 decoder) {
        this.decoder = decoder;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        ByteBuf result = Unpooled.buffer();

        buffer.markReaderIndex();

        try {
            byte[] request = new byte[buffer.readableBytes()];
            buffer.readBytes(request);

            String temp = new String(request);
            String deciphered = this.decoder.decipher(temp);

            result.writeBytes(deciphered.getBytes(StringUtil.getCharset()));
        } catch (Exception ex) {
            buffer.resetReaderIndex();
        }

        out.add(result);
    }
}