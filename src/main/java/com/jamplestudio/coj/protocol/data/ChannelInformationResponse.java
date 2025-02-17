package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.List;

public record ChannelInformationResponse(
        @NotNull List<Data> data
) {

    public record Data(
            @NotNull String channelId,
            @NotNull String channelName,
            @NotNull String channelImageUrl,
            @Range(from = 0, to = Integer.MAX_VALUE) int followerCount
    ) {}

}
