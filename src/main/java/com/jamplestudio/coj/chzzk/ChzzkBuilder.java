package com.jamplestudio.coj.chzzk;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class ChzzkBuilder {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String host = "0.0.0.0";
    private int port = 80;
    
    ChzzkBuilder() {}

    public @NotNull ChzzkBuilder clientId(@NotNull String clientId) {
        this.clientId = clientId;
        return this;
    }
    
    public @NotNull ChzzkBuilder clientSecret(@NotNull String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }
    
    public @NotNull ChzzkBuilder redirectUri(@NotNull String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }
    
    public @NotNull ChzzkBuilder host(@NotNull String host) {
        this.host = host;
        return this;
    }
    
    public @NotNull ChzzkBuilder port(@Range(from = 0, to = 65535) int port) {
        this.port = port;
        return this;
    }
    
    public @NotNull Chzzk build() {
        if (clientId == null || clientSecret == null || redirectUri == null || host == null) {
            throw new IllegalArgumentException("Missing required fields.");
        }
        return new ChzzkImpl(clientId, clientSecret, redirectUri, host, port);
    }

}
