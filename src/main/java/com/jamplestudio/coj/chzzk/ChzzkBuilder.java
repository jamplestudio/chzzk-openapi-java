package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.chzzk.data.ChzzkToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ChzzkBuilder {

    @NotNull ChzzkBuilder clientId(@NotNull String clientId);

    @NotNull ChzzkBuilder clientSecret(@NotNull String clientSecret);

    @NotNull ChzzkBuilder token(@Nullable ChzzkToken token);

    @NotNull ChzzkBuilder addEventHandler(@NotNull ChzzkEventHandler... handlers);

    @NotNull ChzzkBuilder addEventHandler(@NotNull Collection<ChzzkEventHandler> handlers);

    @NotNull Chzzk build();

}
