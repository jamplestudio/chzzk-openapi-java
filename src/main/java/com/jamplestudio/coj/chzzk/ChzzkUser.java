package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.data.UserInformationResponse;
import org.jetbrains.annotations.NotNull;

public interface ChzzkUser {

    static @NotNull ChzzkUser of(@NotNull UserInformationResponse response) {
        return of(response.channelId(), response.channelName());
    }

    static @NotNull ChzzkUser of(@NotNull String id, @NotNull String name) {
        return new ChzzkUserImpl(id, name);
    }

    @NotNull String getChannelId();

    @NotNull String getChannelName();

}
