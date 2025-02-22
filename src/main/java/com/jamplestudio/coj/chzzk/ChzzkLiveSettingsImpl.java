package com.jamplestudio.coj.chzzk;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class ChzzkLiveSettingsImpl implements ChzzkLiveSettings {

    private final @NotNull String liveTitle;
    private final @NotNull ChzzkCategoryType categoryType;
    private final @NotNull String categoryId;
    private final @NotNull String categoryName;
    private final @NotNull String categoryPosterImageUrl;
    private final @NotNull ImmutableList<String> tags;

    ChzzkLiveSettingsImpl(
            @NotNull String liveTitle, @NotNull ChzzkCategoryType categoryType,
            @NotNull String categoryId, @NotNull String categoryName,
            @NotNull String categoryPosterImageUrl, @NotNull List<String> tags
    ) {
        this.liveTitle = liveTitle;
        this.categoryType = categoryType;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPosterImageUrl = categoryPosterImageUrl;
        this.tags = ImmutableList.copyOf(tags);
    }

}
