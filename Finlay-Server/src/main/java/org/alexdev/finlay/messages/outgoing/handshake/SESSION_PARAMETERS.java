package org.alexdev.finlay.messages.outgoing.handshake;

import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;
import org.alexdev.finlay.util.config.GameConfiguration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SESSION_PARAMETERS extends MessageComposer {
    public enum SessionParamType {
        // conf_coppa is enabled when value higher than 0,
        // conf_strong_coppa_required is enabled when value is higher than 1
        REGISTER_COPPA(0),

        // conf_voucher. Determines if vouchers are enabled in the client (in-game)
        VOUCHER_ENABLED(1),

        // conf_parent_email_request. I think this is to switch parent email on/off
        REGISTER_REQUIRE_PARENT_EMAIL(2),

        // conf_parent_email_request_reregistration. ???
        REGISTER_SEND_PARENT_EMAIL(3),

        // conf_allow_direct_mail. ???
        ALLOW_DIRECT_MAIL(4),

        // Configures date formatting. Value is date string.
        DATE_FORMAT(5);

        private final int paramID;

        SessionParamType(int paramID) {
            this.paramID = paramID;
        }

        public int getParamID() {
            return this.paramID;
        }
    }

    private PlayerDetails details;

    public SESSION_PARAMETERS(PlayerDetails details) {
        this.details = details;
    }

    @Override
    public void compose(NettyResponse response) {
        Map<SessionParamType, String> parameters = new LinkedHashMap<>();

        parameters.put(SessionParamType.REGISTER_COPPA, "1");
        parameters.put(SessionParamType.VOUCHER_ENABLED, GameConfiguration.getInstance().getBoolean("vouchers.enabled") ? "1" : "0"); // conf_voucher. Determines if vouchers are enabled in the client (in-game)
        parameters.put(SessionParamType.REGISTER_SEND_PARENT_EMAIL, "0"); // conf_parent_email_request_reregistration. ???
        parameters.put(SessionParamType.REGISTER_REQUIRE_PARENT_EMAIL, "1"); // conf_parent_email_request. I think this is to switch parent email on/off
        parameters.put(SessionParamType.ALLOW_DIRECT_MAIL, "1"); // conf_allow_direct_mail. ???
        parameters.put(SessionParamType.DATE_FORMAT, "dd-MM-yyyy"); // Configures date formatting. Value is date string.

        response.writeInt(parameters.size());

        for (Map.Entry<SessionParamType, String> entry : parameters.entrySet()) {
            SessionParamType key = entry.getKey();
            String value = entry.getValue();

            response.writeInt(key.getParamID());

            if (value.length() > 0 && Character.isDigit(value.charAt(0))) {
                response.writeInt(Integer.parseInt(value));
            } else {
                response.writeString(value);
            }
        }
    }

    @Override
    public short getHeader() {
        return 257;
    }
}