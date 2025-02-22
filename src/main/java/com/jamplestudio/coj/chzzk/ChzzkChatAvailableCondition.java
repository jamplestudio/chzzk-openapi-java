package com.jamplestudio.coj.chzzk;

import org.jetbrains.annotations.NotNull;

public enum ChzzkChatAvailableCondition {

    NONE, REAL_NAME;

    public static @NotNull ChzzkChatAvailableCondition fromString(@NotNull String str) {
        try {
            return valueOf(str.toUpperCase());
        } catch (Exception e) {
            return NONE;
        }
    }

}
