package com.jamplestudio.coj.chzzk;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

@Getter
public class ChzzkChatSettingsImpl implements ChzzkChatSettings {

    private final @NotNull ChzzkChatAvailableCondition chatAvailableCondition;
    private final @NotNull ChzzkChatAvailableGroup chatAvailableGroup;
    private final @Range(from = 0, to = Integer.MAX_VALUE) int minimumFollowerTimeInMinutes;
    private final boolean subscriberAllowedInFollowerMode;

    ChzzkChatSettingsImpl(
            @NotNull ChzzkChatAvailableCondition chatAvailableCondition,
            @NotNull ChzzkChatAvailableGroup chatAvailableGroup,
            @Range(from = 0, to = Integer.MAX_VALUE) int minimumFollowerTimeInMinutes,
            boolean subscriberAllowedInFollowerMode
    ) {
        this.chatAvailableCondition = chatAvailableCondition;
        this.chatAvailableGroup = chatAvailableGroup;
        this.minimumFollowerTimeInMinutes = minimumFollowerTimeInMinutes;
        this.subscriberAllowedInFollowerMode = subscriberAllowedInFollowerMode;
    }

}
