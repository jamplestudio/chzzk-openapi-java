package com.jamplestudio.coj.chzzk;

import com.google.common.collect.ImmutableList;
import com.jamplestudio.coj.protocol.data.LiveSettingsResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ChzzkLiveSettings {

    static @NotNull ChzzkLiveSettings of(@NotNull LiveSettingsResponse response) {
        return of(
                response.defaultLiveTitle(), ChzzkCategoryType.fromString(response.category().categoryType()),
                response.category().categoryId(), response.category().categoryValue(),
                response.category().posterImageUrl(), response.tags()
        );
    }

    static @NotNull ChzzkLiveSettings of(
            @NotNull String title, @NotNull ChzzkCategoryType categoryType,
            @NotNull String categoryId, @NotNull List<String> tags
    ) {
        return of(title, categoryType, categoryId, "", "", tags);
    }

    static @NotNull ChzzkLiveSettings of(
            @NotNull String liveTitle, @NotNull ChzzkCategoryType categoryType,
            @NotNull String categoryId, @NotNull String categoryName,
            @NotNull String categoryPosterImageUrl, @NotNull List<String> tags
    ) {
        return new ChzzkLiveSettingsImpl(liveTitle, categoryType, categoryId, categoryName, categoryPosterImageUrl, tags);
    }

    @NotNull String getLiveTitle();

    @NotNull ChzzkCategoryType getCategoryType();

    @NotNull String getCategoryId();

    @NotNull String getCategoryName();

    @NotNull String getCategoryPosterImageUrl();

    @NotNull List<String> getTags();

}
