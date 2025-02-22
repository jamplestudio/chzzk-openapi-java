package com.jamplestudio.coj.chzzk.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jamplestudio.coj.chzzk.Chzzk;
import com.jamplestudio.coj.chzzk.ChzzkBuilder;
import com.jamplestudio.coj.chzzk.ChzzkEventHandler;
import com.jamplestudio.coj.chzzk.ChzzkToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ChzzkBuilderV1 implements ChzzkBuilder {

    private String clientId;
    private String clientSecret;
    private ChzzkToken token;
    private Set<ChzzkEventHandler> handlers = Sets.newHashSet();
    
    public ChzzkBuilderV1() {}

    public @NotNull ChzzkBuilder clientId(@NotNull String clientId) {
        this.clientId = clientId;
        return this;
    }
    
    public @NotNull ChzzkBuilder clientSecret(@NotNull String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public @NotNull ChzzkBuilder token(@Nullable ChzzkToken token) {
        this.token = token;
        return this;
    }

    @Override
    public @NotNull ChzzkBuilder addEventHandler(@NotNull ChzzkEventHandler... handlers) {
        this.handlers.addAll(Lists.newArrayList(handlers));
        return this;
    }

    public @NotNull Chzzk build() {
        if (clientId == null || clientSecret == null) {
            throw new IllegalArgumentException("Missing required fields.");
        }

        return new ChzzkV1(clientId, clientSecret, token, handlers);
    }

}
