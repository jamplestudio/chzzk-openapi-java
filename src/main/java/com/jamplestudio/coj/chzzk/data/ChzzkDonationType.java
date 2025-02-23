package com.jamplestudio.coj.chzzk.data;

import org.jetbrains.annotations.NotNull;

public enum ChzzkDonationType {

    CHAT, VIDEO;

    public static @NotNull ChzzkDonationType fromString(@NotNull String str) {
        try {
            return valueOf(str.toUpperCase());
        } catch (Exception e) {
            return CHAT;
        }
    }

}
