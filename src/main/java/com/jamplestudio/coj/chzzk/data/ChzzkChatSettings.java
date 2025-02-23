package com.jamplestudio.coj.chzzk.data;

import com.jamplestudio.coj.net.data.ChatSettingsResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record ChzzkChatSettings(
        @NotNull ChzzkChatAvailableCondition chatAvailableCondition,
        @NotNull ChzzkChatAvailableGroup chatAvailableGroup,
        @Range(from = 0, to = Integer.MAX_VALUE) int minimumFollowerTimeInMinutes,
        boolean isSubscriberAllowedInFollowerMode
) {

    public static @NotNull ChzzkChatSettings of(@NotNull ChatSettingsResponse response) {
        return of(
                ChzzkChatAvailableCondition.fromString(response.chatAvailableCondition()),
                ChzzkChatAvailableGroup.fromString(response.chatAvailableGroup()),
                response.minFollowerMinute(),
                response.allowSubscriberInFollowerMode()
        );
    }

    public static @NotNull ChzzkChatSettings of(
            @NotNull ChzzkChatAvailableCondition condition,
            @NotNull ChzzkChatAvailableGroup group,
            @Range(from = 0, to = Integer.MAX_VALUE) int minimumFollowerTimeInMinutes,
            boolean subscriberAllowedInFollowerMode
    ) {
        return new ChzzkChatSettings(condition, group, minimumFollowerTimeInMinutes, subscriberAllowedInFollowerMode);
    }

}
