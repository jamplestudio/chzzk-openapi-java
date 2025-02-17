package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record LiveSearchResponse(
        @NotNull List<Data> data,
        @NotNull Page page
) {

    public record Data(
            int liveId,
            @NotNull String liveTitle,
            @NotNull String liveThumbnailImageUrl,
            int concurrentUserCount,
            @NotNull String openDate,
            boolean adult,
            @NotNull List<String> tags
    ) {}

    public record Page(
            @NotNull String next
    ) {}

}
