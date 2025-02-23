package com.jamplestudio.coj.chzzk.data;

import com.jamplestudio.coj.net.data.UserInformationResponse;
import org.jetbrains.annotations.NotNull;

public record ChzzkUser(
        @NotNull String id,
        @NotNull String name
) {

    public static @NotNull ChzzkUser of(@NotNull UserInformationResponse response) {
        return of(response.channelId(), response.channelName());
    }

    public static @NotNull ChzzkUser of(@NotNull String id, @NotNull String name) {
        return new ChzzkUser(id, name);
    }

}
