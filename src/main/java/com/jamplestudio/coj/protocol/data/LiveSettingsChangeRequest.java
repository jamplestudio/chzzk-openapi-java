package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record LiveSettingsChangeRequest(
        @NotNull String defaultLiveTitle,
        @NotNull String categoryType,
        @NotNull String categoryId,
        @NotNull List<String> tags
) {
}
