package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServer;
import org.jetbrains.annotations.NotNull;

public interface Chzzk {

    static @NotNull ChzzkBuilder builder() {
        return new ChzzkBuilder();
    }

    @NotNull String getClientId();

    @NotNull String getClientSecret();

    @NotNull String getRedirectUri();

    @NotNull String getHost();

    int getPort();

    @NotNull ChzzkAuthServer getSession();

}
