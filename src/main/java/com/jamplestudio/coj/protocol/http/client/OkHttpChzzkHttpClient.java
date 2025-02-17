package com.jamplestudio.coj.protocol.http.client;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

public class OkHttpChzzkHttpClient implements ChzzkHttpClient<OkHttpClient> {

    private final @NotNull OkHttpClient client;

    OkHttpChzzkHttpClient() {
        this.client = new OkHttpClient();
    }

    @Override
    public @NotNull OkHttpClient getNativeHttpClient() {
        return client;
    }

}
