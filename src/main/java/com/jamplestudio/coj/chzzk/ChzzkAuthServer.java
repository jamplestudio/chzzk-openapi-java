package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.chzzk.impl.ChzzkAuthServerBuilderImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface ChzzkAuthServer {

    static @NotNull ChzzkAuthServerBuilder builder() {
        return new ChzzkAuthServerBuilderImpl();
    }

    @NotNull String getClientId();

    @NotNull String getClientSecret();

    @NotNull String getBaseUri();

    @NotNull String getHost();

    @Range(from = 0, to = 65535) int getPort();

    @NotNull ChzzkBuilder newChzzkBuilder();

    void start();

    void stop();

}
