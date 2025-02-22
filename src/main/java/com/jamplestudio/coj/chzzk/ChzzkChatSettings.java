package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.data.ChatSettingsResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface ChzzkChatSettings {

    static @NotNull ChzzkChatSettings of(@NotNull ChatSettingsResponse response) {
        return of(
                ChzzkChatAvailableCondition.fromString(response.chatAvailableCondition()),
                ChzzkChatAvailableGroup.fromString(response.chatAvailableGroup()),
                response.minFollowerMinute(),
                response.allowSubscriberInFollowerMode()
        );
    }

    static @NotNull ChzzkChatSettings of(
            @NotNull ChzzkChatAvailableCondition condition,
            @NotNull ChzzkChatAvailableGroup group,
            @Range(from = 0, to = Integer.MAX_VALUE) int minimumFollowerTimeInMinutes,
            boolean subscriberAllowedInFollowerMode
    ) {
        return new ChzzkChatSettingsImpl(condition, group, minimumFollowerTimeInMinutes, subscriberAllowedInFollowerMode);
    }

    @NotNull ChzzkChatAvailableCondition getChatAvailableCondition();

    @NotNull ChzzkChatAvailableGroup getChatAvailableGroup();

    @Range(from = 0, to = Integer.MAX_VALUE) int getMinimumFollowerTimeInMinutes();

    boolean isSubscriberAllowedInFollowerMode();

}
