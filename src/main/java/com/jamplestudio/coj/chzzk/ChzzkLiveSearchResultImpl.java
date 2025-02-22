package com.jamplestudio.coj.chzzk;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class ChzzkLiveSearchResultImpl implements ChzzkLiveSearchResult {

    private final @Range(from = 0, to = Integer.MAX_VALUE) int liveId;
    private final @NotNull String liveTitle;
    private final @NotNull String liveThumbnailImageUrl;
    private final @Range(from = 0, to = Integer.MAX_VALUE) int userCount;
    private final @NotNull ZonedDateTime liveStartTime;
    private final boolean adultMode;
    private final @NotNull ImmutableList<String> tags;
    private final @NotNull ChzzkCategoryType categoryType;
    private final @NotNull String categoryId;
    private final @NotNull String categoryName;
    private final @NotNull String channelId;
    private final @NotNull String channelName;
    private final @NotNull String channelImageUrl;
    private final @NotNull String nextPage;

    ChzzkLiveSearchResultImpl(
            @Range(from = 0, to = Integer.MAX_VALUE) int liveId, @NotNull String liveTitle, @NotNull String liveThumbnailImageUrl,
            @Range(from = 0, to = Integer.MAX_VALUE) int userCount, @NotNull ZonedDateTime liveStartTime,
            boolean adultMode, @NotNull List<String> tags, @NotNull ChzzkCategoryType categoryType,
            @NotNull String categoryId, @NotNull String categoryName, @NotNull String channelId,
            @NotNull String channelName, @NotNull String channelImageUrl, @NotNull String nextPage
    ) {
        this.liveId = liveId;
        this.liveTitle = liveTitle;
        this.liveThumbnailImageUrl = liveThumbnailImageUrl;
        this.userCount = userCount;
        this.liveStartTime = liveStartTime;
        this.adultMode = adultMode;
        this.tags = ImmutableList.copyOf(tags);
        this.categoryType = categoryType;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelImageUrl = channelImageUrl;
        this.nextPage = nextPage;
    }

}
