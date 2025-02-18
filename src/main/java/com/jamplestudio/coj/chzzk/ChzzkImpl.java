package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServer;
import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServerImpl;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;


@Getter
public class ChzzkImpl implements Chzzk {

    private final @NotNull String clientId;
    private final @NotNull String clientSecret;
    private final @NotNull String redirectUri;
    private final @NotNull String host;
    private final @Range(from = 0, to = 65535) int port;

    private final @NotNull ChzzkAuthServer session;

    ChzzkImpl(
            @NotNull String clientId, @NotNull String clientSecret,
            @NotNull String redirectUri, @NotNull String host,
            @Range(from = 0, to = 65535) int port
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.host = host;
        this.port = port;

        this.session = new ChzzkAuthServerImpl(this);
    }

}
