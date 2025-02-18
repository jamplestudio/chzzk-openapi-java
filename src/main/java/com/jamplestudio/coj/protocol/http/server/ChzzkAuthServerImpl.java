package com.jamplestudio.coj.protocol.http.server;

import com.jamplestudio.coj.chzzk.Chzzk;
import com.jamplestudio.coj.protocol.http.server.exchange.AuthCallbackHandler;
import com.jamplestudio.coj.protocol.http.server.exchange.AuthLoginChzzkHandler;
import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.session.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
public class ChzzkAuthServerImpl implements ChzzkAuthServer {

    private final UUID uniqueId = UUID.randomUUID();

    private final SessionManager sessionManager = new InMemorySessionManager("CHZZK_SESSION_MANAGER_" + uniqueId);
    private final SessionCookieConfig sessionCookieConfig = new SessionCookieConfig();
    private final SessionAttachmentHandler sessionHandler = new SessionAttachmentHandler(sessionManager, sessionCookieConfig);

    private final RoutingHandler routing = new RoutingHandler();
    private final Undertow server;

    private final @NotNull Chzzk chzzk;

    public ChzzkAuthServerImpl(@NotNull Chzzk chzzk) {
        this.chzzk = chzzk;

        routing.get("/auth/login/chzzk", new AuthLoginChzzkHandler(this));
        routing.get("/auth/callback", new AuthCallbackHandler(this));

        PathHandler pathHandler = new PathHandler(sessionHandler);
        pathHandler.addPrefixPath("/", routing);

        server = Undertow.builder()
                .addHttpListener(chzzk.getPort(), chzzk.getHost())
                .setHandler(pathHandler)
                .build();
    }

    @Override
    public void start() {
        server.start();
    }

    @Override
    public void stop() {
        server.stop();
    }

}
