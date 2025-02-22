package com.jamplestudio.coj.net.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ChannelInformationRequest(
        @NotNull List<String> channelIds,
        @NotNull String accessToken
) {
}
