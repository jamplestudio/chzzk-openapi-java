package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.net.data.ChannelInformationResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface ChzzkChannel {

    static @NotNull ChzzkChannel of(@NotNull ChannelInformationResponse.Data response) {
        return of(response.channelId(), response.channelName(), response.channelImageUrl(), response.followerCount());
    }

    static @NotNull ChzzkChannel of(
            @NotNull String id, @NotNull String name, @NotNull String imageUrl,
            @Range(from = 0, to = Integer.MAX_VALUE) int followerCount
    ) {
        return new ChzzkChannelImpl(id, name, imageUrl, followerCount);
    }

    @NotNull String getId();

    @NotNull String getName();

    @NotNull String getImageUrl();

    @Range(from = 0, to = Integer.MAX_VALUE) int getFollowerCount();

}
