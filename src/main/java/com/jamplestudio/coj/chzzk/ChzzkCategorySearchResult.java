package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.data.CategorySearchResponse;
import org.jetbrains.annotations.NotNull;

public interface ChzzkCategorySearchResult {

    static @NotNull ChzzkCategorySearchResult of(@NotNull CategorySearchResponse.Data response) {
        return of(
                response.categoryId(), response.categoryValue(),
                ChzzkCategoryType.fromString(response.categoryType()), response.posterImageUrl()
        );
    }

    static @NotNull ChzzkCategorySearchResult of(
            @NotNull String id, @NotNull String name, @NotNull ChzzkCategoryType type, @NotNull String posterImageUrl) {
        return new ChzzkCategorySearchResultImpl(id, name, type, posterImageUrl);
    }

    @NotNull String getId();

    @NotNull String getName();

    @NotNull ChzzkCategoryType getType();

    @NotNull String getPosterImageUrl();

}
