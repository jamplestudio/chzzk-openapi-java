package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record ChatSettingsRequest(
        @NotNull String chatAvailableCondition,
        @NotNull String chatAvailableGroup,
        int minFollowerMinute,
        boolean allowSubscriberInFollowerMode
) {
}
