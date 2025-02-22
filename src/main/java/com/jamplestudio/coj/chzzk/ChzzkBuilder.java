package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.factory.HttpRequestExecutorFactory;
import org.jetbrains.annotations.NotNull;

public interface ChzzkBuilder {

    @NotNull ChzzkBuilder clientId(@NotNull String clientId);

    @NotNull ChzzkBuilder clientSecret(@NotNull String clientSecret);

    @NotNull ChzzkBuilder token(@NotNull ChzzkToken token);

    @NotNull ChzzkBuilder httpExecutorFactory(@NotNull HttpRequestExecutorFactory httpExecutorFactory);

    @NotNull ChzzkBuilder httpClient(@NotNull ChzzkHttpClient<?> httpClient);

    @NotNull Chzzk build();

}
