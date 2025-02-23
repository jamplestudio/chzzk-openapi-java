package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.chzzk.data.ChzzkChatMessage;
import com.jamplestudio.coj.chzzk.data.ChzzkDonationMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ChzzkEventHandler {

    default void onUserRegistered(@NotNull Chzzk chzzk, @Nullable String user) {}

    default void onGrantToken(@NotNull Chzzk chzzk) {}

    default void onRefreshToken(@NotNull Chzzk chzzk) {}

    default void onRevokeToken(@NotNull Chzzk chzzk) {}

    default void onChatMessage(@NotNull Chzzk chzzk, @NotNull ChzzkChatMessage message) {}

    default void onDonationMessage(@NotNull Chzzk chzzk, @NotNull ChzzkDonationMessage message) {}

}
