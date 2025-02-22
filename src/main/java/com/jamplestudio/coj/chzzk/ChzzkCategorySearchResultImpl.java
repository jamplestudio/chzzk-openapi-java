package com.jamplestudio.coj.chzzk;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ChzzkCategorySearchResultImpl implements ChzzkCategorySearchResult {

    private final @NotNull String id;
    private final @NotNull String name;
    private final @NotNull ChzzkCategoryType type;
    private final @NotNull String posterImageUrl;

    ChzzkCategorySearchResultImpl(
            @NotNull String id, @NotNull String name, @NotNull ChzzkCategoryType type, @NotNull String posterImageUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.posterImageUrl = posterImageUrl;
    }

}
