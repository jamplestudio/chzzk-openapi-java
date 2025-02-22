package com.jamplestudio.coj.chzzk;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface ChzzkAuthServerBuilder {

    @NotNull ChzzkAuthServerBuilder clientId(@NotNull String clientId);

    @NotNull ChzzkAuthServerBuilder clientSecret(@NotNull String clientSecret);

    @NotNull ChzzkAuthServerBuilder redirectUri(@NotNull String redirectUri);

    @NotNull ChzzkAuthServerBuilder host(@NotNull String host);

    @NotNull ChzzkAuthServerBuilder port(int port);

    @NotNull ChzzkAuthServerBuilder addChzzkEventHandler(@NotNull ChzzkEventHandler... handlers);

    @NotNull ChzzkAuthServerBuilder addChzzkEventHandler(@NotNull Collection<ChzzkEventHandler> handlers);

    @NotNull ChzzkAuthServer build();

}
