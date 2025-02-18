package com.jamplestudio.coj.protocol.http.server;

import com.jamplestudio.coj.chzzk.Chzzk;
import io.undertow.server.session.SessionCookieConfig;
import io.undertow.server.session.SessionManager;
import org.jetbrains.annotations.NotNull;

public interface ChzzkAuthServer {

    void start();

    void stop();

    @NotNull Chzzk getChzzk();

    @NotNull SessionManager getSessionManager();

    @NotNull SessionCookieConfig getSessionCookieConfig();

}
