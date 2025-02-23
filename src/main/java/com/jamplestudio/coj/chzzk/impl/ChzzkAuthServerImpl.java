package com.jamplestudio.coj.chzzk.impl;

import com.google.common.collect.ImmutableSet;
import com.jamplestudio.coj.chzzk.ChzzkAuthServer;
import com.jamplestudio.coj.chzzk.ChzzkBuilder;
import com.jamplestudio.coj.chzzk.ChzzkEventHandler;
import com.jamplestudio.coj.chzzk.ChzzkEventHandlerHolder;
import com.jamplestudio.coj.net.http.server.AuthServer;
import com.jamplestudio.coj.net.http.server.undertow.UndertowAuthServer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Set;

@Getter
public class ChzzkAuthServerImpl implements ChzzkAuthServer, ChzzkEventHandlerHolder {

    private final @NotNull String clientId;
    private final @NotNull String clientSecret;
    private final @NotNull String baseUri;
    private final @NotNull String host;
    private final @Range(from = 0, to = 65535) int port;

    private final @NotNull ImmutableSet<ChzzkEventHandler> handlers;
    private final AuthServer server;

    ChzzkAuthServerImpl(
            @NotNull String clientId, @NotNull String clientSecret, @NotNull String baseUri,
            @NotNull String host, @Range(from = 0, to = 65535) int port,
            @NotNull Set<ChzzkEventHandler> handlers
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.baseUri = baseUri;
        this.host = host;
        this.port = port;

        this.handlers = ImmutableSet.copyOf(handlers);
        this.server = new UndertowAuthServer(this);
    }

    @Override
    public @NotNull ChzzkBuilder newChzzkBuilder() {
        return new ChzzkBuilderImpl();
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
