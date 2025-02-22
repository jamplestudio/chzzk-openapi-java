package com.jamplestudio.coj.chzzk;

import org.jetbrains.annotations.NotNull;

public enum ChzzkCategoryType {

    GAME, SPORTS, ETC;

    public static @NotNull ChzzkCategoryType fromString(@NotNull String str) {
        try {
            return ChzzkCategoryType.valueOf(str.toUpperCase());
        } catch (Exception e) {
            return ETC;
        }
    }

}
