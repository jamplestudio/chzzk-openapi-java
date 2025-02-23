package com.jamplestudio.coj.net.http.server;

import com.jamplestudio.coj.chzzk.ChzzkAuthServer;
import io.undertow.server.session.SessionCookieConfig;
import io.undertow.server.session.SessionManager;
import org.jetbrains.annotations.NotNull;

public interface AuthServer {

    @NotNull ChzzkAuthServer getChzzkAuthServer();

    @NotNull SessionManager getSessionManager();

    @NotNull SessionCookieConfig getSessionCookieConfig();

    void start();

    void stop();

}
