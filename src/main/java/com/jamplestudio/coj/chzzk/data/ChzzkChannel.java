package com.jamplestudio.coj.chzzk.data;

import com.jamplestudio.coj.net.data.ChannelInformationResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record ChzzkChannel(
        @NotNull String id,
        @NotNull String name,
        @NotNull String imageUrl,
        @Range(from = 0, to = Integer.MAX_VALUE) int followerCount
) {

    public static @NotNull ChzzkChannel of(@NotNull ChannelInformationResponse.Data response) {
        return of(response.channelId(), response.channelName(), response.channelImageUrl(), response.followerCount());
    }

    public static @NotNull ChzzkChannel of(
            @NotNull String id, @NotNull String name, @NotNull String imageUrl,
            @Range(from = 0, to = Integer.MAX_VALUE) int followerCount
    ) {
        return new ChzzkChannel(id, name, imageUrl, followerCount);
    }

}
