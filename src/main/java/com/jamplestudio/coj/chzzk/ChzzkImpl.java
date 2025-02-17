package com.jamplestudio.coj.chzzk;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ChzzkImpl implements Chzzk {

    private final @NotNull String clientId;
    private final @NotNull String clientSecret;

    ChzzkImpl(@NotNull String clientId, @NotNull String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

}
