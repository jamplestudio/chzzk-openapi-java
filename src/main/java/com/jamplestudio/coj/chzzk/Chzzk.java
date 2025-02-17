package com.jamplestudio.coj.chzzk;

import org.jetbrains.annotations.NotNull;

public interface Chzzk {

    static @NotNull Chzzk of(@NotNull String clientId, @NotNull String clientSecret) {
        return new ChzzkImpl(clientId, clientSecret);
    }

    @NotNull String getClientId();

    @NotNull String getClientSecret();

}
