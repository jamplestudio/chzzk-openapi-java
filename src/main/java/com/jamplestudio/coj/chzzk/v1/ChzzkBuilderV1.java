package com.jamplestudio.coj.chzzk.v1;

import com.jamplestudio.coj.chzzk.Chzzk;
import com.jamplestudio.coj.chzzk.ChzzkBuilder;
import com.jamplestudio.coj.chzzk.ChzzkToken;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.factory.HttpRequestExecutorFactory;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

public class ChzzkBuilderV1 implements ChzzkBuilder {

    private String clientId;
    private String clientSecret;
    private ChzzkToken token;
    private HttpRequestExecutorFactory httpExecutorFactory;
    private ChzzkHttpClient<OkHttpClient> httpClient;
    
    public ChzzkBuilderV1() {}

    public @NotNull ChzzkBuilder clientId(@NotNull String clientId) {
        this.clientId = clientId;
        return this;
    }
    
    public @NotNull ChzzkBuilder clientSecret(@NotNull String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public @NotNull ChzzkBuilder token(@NotNull ChzzkToken token) {
        this.token = token;
        return this;
    }

    public @NotNull ChzzkBuilder httpExecutorFactory(@NotNull HttpRequestExecutorFactory httpExecutorFactory) {
        this.httpExecutorFactory = httpExecutorFactory;
        return this;
    }

    @SuppressWarnings("unchecked")
    public @NotNull ChzzkBuilder httpClient(@NotNull ChzzkHttpClient<?> httpClient) {
        this.httpClient = (ChzzkHttpClient<OkHttpClient>) httpClient;
        return this;
    }
    
    public @NotNull Chzzk build() {
        if (clientId == null
                || clientSecret == null
                || token == null
                || httpExecutorFactory == null
                || httpClient == null
        ) {
            throw new IllegalArgumentException("Missing required fields.");
        }

        return new ChzzkV1(clientId, clientSecret, token, httpExecutorFactory, httpClient);
    }

}
