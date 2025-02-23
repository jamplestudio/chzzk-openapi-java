package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.chzzk.data.ChzzkChatMessage;
import com.jamplestudio.coj.chzzk.data.ChzzkDonationMessage;
import org.jetbrains.annotations.NotNull;

public interface ChzzkEventHandler {

    default void onGrantToken(@NotNull Chzzk chzzk) {}

    default void onRefreshToken(@NotNull Chzzk chzzk) {}

    default void onRevokeToken(@NotNull Chzzk chzzk) {}

    default void onChatMessage(@NotNull Chzzk chzzk, @NotNull ChzzkChatMessage message) {}

    default void onDonationMessage(@NotNull Chzzk chzzk, @NotNull ChzzkDonationMessage message) {}

}
