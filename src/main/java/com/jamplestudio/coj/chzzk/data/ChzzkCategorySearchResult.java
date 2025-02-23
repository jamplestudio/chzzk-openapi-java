package com.jamplestudio.coj.chzzk.data;

import com.jamplestudio.coj.net.data.CategorySearchResponse;
import org.jetbrains.annotations.NotNull;

public record ChzzkCategorySearchResult(
        @NotNull String id,
        @NotNull String name,
        @NotNull ChzzkCategoryType type,
        @NotNull String posterImageUrl
) {

    public static @NotNull ChzzkCategorySearchResult of(@NotNull CategorySearchResponse.Data response) {
        return of(
                response.categoryId(), response.categoryValue(),
                ChzzkCategoryType.fromString(response.categoryType()), response.posterImageUrl()
        );
    }

    public static @NotNull ChzzkCategorySearchResult of(
            @NotNull String id, @NotNull String name, @NotNull ChzzkCategoryType type, @NotNull String posterImageUrl) {
        return new ChzzkCategorySearchResult(id, name, type, posterImageUrl);
    }

}
