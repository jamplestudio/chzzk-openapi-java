package com.jamplestudio.coj.chzzk.impl;

import com.jamplestudio.coj.chzzk.ChzzkAuthServer;
import com.jamplestudio.coj.chzzk.ChzzkBuilder;
import com.jamplestudio.coj.net.http.server.AuthServer;
import com.jamplestudio.coj.net.http.server.undertow.UndertowAuthServer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

@Getter
public class ChzzkAuthServerV1 implements ChzzkAuthServer {

    private final @NotNull String clientId;
    private final @NotNull String clientSecret;
    private final @NotNull String redirectUri;
    private final @NotNull String host;
    private final @Range(from = 0, to = 65535) int port;

    private final AuthServer server;

    ChzzkAuthServerV1(
            @NotNull String clientId, @NotNull String clientSecret, @NotNull String redirectUri,
            @NotNull String host, @Range(from = 0, to = 65535) int port
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.host = host;
        this.port = port;

        this.server = new UndertowAuthServer(this);
    }

    @Override
    public @NotNull ChzzkBuilder newChzzkBuilder() {
        return new ChzzkBuilderV1();
    }

    @Override
    public void start() {
        server.start();
    }

    @Override
    public void stop() {
        server.stop();
    }

}
