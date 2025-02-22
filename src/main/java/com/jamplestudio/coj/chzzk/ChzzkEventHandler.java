package com.jamplestudio.coj.chzzk;

import org.jetbrains.annotations.NotNull;

public interface ChzzkEventHandler {

    default void onGrantToken(@NotNull Chzzk chzzk) {}

    default void onRefreshToken(@NotNull Chzzk chzzk) {}

    default void onRevokeToken(@NotNull Chzzk chzzk) {}

}
