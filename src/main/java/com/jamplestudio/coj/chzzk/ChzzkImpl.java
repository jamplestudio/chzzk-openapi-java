package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.http.server.ChzzkAuthSession;
import com.jamplestudio.coj.protocol.http.server.ChzzkAuthSessionImpl;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;


@Getter
public class ChzzkImpl implements Chzzk {

    private final @NotNull String clientId;
    private final @NotNull String clientSecret;
    private final @NotNull String redirectUri;
    private final @NotNull String host;
    private final @Range(from = 0, to = 65535) int port;

    private final @NotNull ChzzkAuthSession session;

    ChzzkImpl(
            @NotNull String clientId, @NotNull String clientSecret,
            @NotNull String redirectUri, @NotNull String host,
            @Range(from = 0, to = 65535) int port
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.host = host;
        this.port = port;

        this.session = new ChzzkAuthSessionImpl(this);
    }

}
