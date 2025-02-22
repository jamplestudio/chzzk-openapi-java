package com.jamplestudio.coj.chzzk;

import org.jetbrains.annotations.NotNull;

public interface ChzzkAuthServerBuilder {

    @NotNull ChzzkAuthServerBuilder clientId(@NotNull String clientId);

    @NotNull ChzzkAuthServerBuilder clientSecret(@NotNull String clientSecret);

    @NotNull ChzzkAuthServerBuilder redirectUri(@NotNull String redirectUri);

    @NotNull ChzzkAuthServerBuilder host(@NotNull String host);

    @NotNull ChzzkAuthServerBuilder port(int port);

    @NotNull ChzzkAuthServer build();

}
