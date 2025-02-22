package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.chzzk.impl.ChzzkAuthServerBuilderV1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface ChzzkAuthServer {

    static @NotNull ChzzkAuthServerBuilder v1() {
        return new ChzzkAuthServerBuilderV1();
    }

    @NotNull String getClientId();

    @NotNull String getClientSecret();

    @NotNull String getRedirectUri();

    @NotNull String getHost();

    @Range(from = 0, to = 65535) int getPort();

    @NotNull ChzzkBuilder newChzzkBuilder();

    void start();

    void stop();

}
