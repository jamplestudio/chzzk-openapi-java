package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.net.data.LiveSearchResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.time.ZonedDateTime;
import java.util.List;

public interface ChzzkLiveSearchResult {

    static @NotNull ChzzkLiveSearchResult of(@NotNull LiveSearchResponse.Data response, @NotNull LiveSearchResponse.Page page) {
        ZonedDateTime date = ZonedDateTime.parse(response.openDate());
        ChzzkCategoryType categoryType = ChzzkCategoryType.fromString(response.categoryType());
        return of(
                response.liveId(), response.liveTitle(), response.liveThumbnailImageUrl(),
                response.concurrentUserCount(), date,
                response.adult(), response.tags(), categoryType,
                response.liveCategory(), response.liveCategoryValue(), response.channelId(),
                response.channelName(), response.channelImageUrl(), page.next()
        );
    }

    static @NotNull ChzzkLiveSearchResult of(
            @Range(from = 0, to = Integer.MAX_VALUE) int liveId, @NotNull String liveTitle, @NotNull String liveThumbnailImageUrl,
            @Range(from = 0, to = Integer.MAX_VALUE) int userCount, @NotNull ZonedDateTime liveStartTime,
            boolean adultMode, @NotNull List<String> tags, @NotNull ChzzkCategoryType categoryType,
            @NotNull String categoryId, @NotNull String categoryName, @NotNull String channelId,
            @NotNull String channelName, @NotNull String channelImageUrl, @NotNull String nextPage
    ) {
        return new ChzzkLiveSearchResultImpl(
                liveId, liveTitle, liveThumbnailImageUrl, userCount,
                liveStartTime, adultMode, tags, categoryType, categoryId,
                categoryName, channelId, channelName, channelImageUrl, nextPage
        );
    }

    @Range(from = 0, to = Integer.MAX_VALUE) int getLiveId();

    @NotNull String getLiveTitle();

    @NotNull String getLiveThumbnailImageUrl();

    @Range(from = 0, to = Integer.MAX_VALUE) int getUserCount();

    @NotNull ZonedDateTime getLiveStartTime();

    boolean isAdultMode();

    @NotNull List<String> getTags();

    @NotNull ChzzkCategoryType getCategoryType();

    @NotNull String getCategoryId();

    @NotNull String getCategoryName();

    @NotNull String getChannelId();

    @NotNull String getChannelName();

    @NotNull String getChannelImageUrl();

    @NotNull String getNextPage();

}
