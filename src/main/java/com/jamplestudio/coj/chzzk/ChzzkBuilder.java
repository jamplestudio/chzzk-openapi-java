package com.jamplestudio.coj.chzzk;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ChzzkBuilder {

    @NotNull ChzzkBuilder clientId(@NotNull String clientId);

    @NotNull ChzzkBuilder clientSecret(@NotNull String clientSecret);

    @NotNull ChzzkBuilder token(@Nullable ChzzkToken token);

    @NotNull ChzzkBuilder addEventHandler(@NotNull ChzzkEventHandler... handlers);

    @NotNull Chzzk build();

}
