package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ChannelInformationRequest(
        @NotNull List<String> channelIds
) {
}
