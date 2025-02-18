package com.jamplestudio.coj.protocol.http.server;

import com.jamplestudio.coj.chzzk.Chzzk;
import io.undertow.server.RoutingHandler;
import io.undertow.server.session.InMemorySessionManager;
import io.undertow.server.session.SessionAttachmentHandler;
import io.undertow.server.session.SessionCookieConfig;
import io.undertow.server.session.SessionManager;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ChzzkAuthSessionImpl implements ChzzkAuthSession {

    private final UUID uniqueId = UUID.randomUUID();

    private final SessionManager sessionManager = new InMemorySessionManager("CHZZK_SESSION_MANAGER_" + uniqueId);
    private final SessionCookieConfig sessionCookieConfig = new SessionCookieConfig();
    private final SessionAttachmentHandler sessionHandler = new SessionAttachmentHandler(sessionManager, sessionCookieConfig);

    private final RoutingHandler routing = new RoutingHandler();

    private final @NotNull Chzzk chzzk;

    public ChzzkAuthSessionImpl(@NotNull Chzzk chzzk) {
        this.chzzk = chzzk;
    }


}
