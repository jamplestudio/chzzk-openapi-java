package com.jamplestudio.coj.net.data;

import org.jetbrains.annotations.NotNull;

public record ChatSettingsChangeRequest(
        @NotNull String chatAvailableCondition,
        @NotNull String chatAvailableGroup,
        int minFollowerMinute,
        boolean allowSubscriberInFollowerMode,
        @NotNull String accessToken
) {
}
