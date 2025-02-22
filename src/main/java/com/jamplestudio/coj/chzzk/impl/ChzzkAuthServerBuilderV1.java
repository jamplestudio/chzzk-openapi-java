package com.jamplestudio.coj.chzzk.impl;

import com.google.common.collect.Sets;
import com.jamplestudio.coj.chzzk.ChzzkAuthServer;
import com.jamplestudio.coj.chzzk.ChzzkAuthServerBuilder;
import com.jamplestudio.coj.chzzk.ChzzkEventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class ChzzkAuthServerBuilderV1 implements ChzzkAuthServerBuilder {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String host = "0.0.0.0";
    private int port = 80;

    private final Set<ChzzkEventHandler> handlers = Sets.newHashSet();

    @Override
    public @NotNull ChzzkAuthServerBuilder clientId(@NotNull String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public @NotNull ChzzkAuthServerBuilder clientSecret(@NotNull String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    @Override
    public @NotNull ChzzkAuthServerBuilder redirectUri(@NotNull String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    @Override
    public @NotNull ChzzkAuthServerBuilder host(@NotNull String host) {
        this.host = host;
        return this;
    }

    @Override
    public @NotNull ChzzkAuthServerBuilder port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public @NotNull ChzzkAuthServerBuilder addChzzkEventHandler(@NotNull ChzzkEventHandler... handlers) {
        Collections.addAll(this.handlers, handlers);
        return this;
    }

    @Override
    public @NotNull ChzzkAuthServerBuilder addChzzkEventHandler(@NotNull Collection<ChzzkEventHandler> handlers) {
        this.handlers.addAll(handlers);
        return this;
    }

    @Override
    public @NotNull ChzzkAuthServer build() {
        if (clientId == null
                || clientSecret == null
                || redirectUri == null
                || host == null
                || port < 0
                || port > 65535
        ) {
            throw new IllegalArgumentException("Missing required fields.");
        }
        return new ChzzkAuthServerV1(clientId, clientSecret, redirectUri, host, port, handlers);
    }

}
