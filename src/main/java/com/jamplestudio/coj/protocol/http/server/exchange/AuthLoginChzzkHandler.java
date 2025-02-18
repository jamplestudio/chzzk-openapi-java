package com.jamplestudio.coj.protocol.http.server.exchange;

import com.jamplestudio.coj.chzzk.Chzzk;
import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServer;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;
import io.undertow.server.session.SessionCookieConfig;
import io.undertow.server.session.SessionManager;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AuthLoginChzzkHandler implements HttpHandler {

    private final @NotNull ChzzkAuthServer server;

    public AuthLoginChzzkHandler(@NotNull ChzzkAuthServer chzzk) {
        this.server = chzzk;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        SessionManager manager = server.getSessionManager();
        SessionCookieConfig cookieConfig = server.getSessionCookieConfig();

        Session session = manager.getSession(exchange, cookieConfig);
        if (session == null) {
            session = manager.createSession(exchange, cookieConfig);
        }

        // CSRF 방지를 위한 state 값 생성
        String state = UUID.randomUUID().toString().replace("-", "");
        session.setAttribute("OAUTH_STATE", state);

        Chzzk chzzk = server.getChzzk();
        HttpUrl url = HttpUrl.get("https://chzzk.naver.com/account-interlock")
                .newBuilder()
                .addQueryParameter("clientId", chzzk.getClientId())
                .addQueryParameter("redirectUri", chzzk.getRedirectUri())
                .addQueryParameter("state", state)
                .build();

        exchange.setStatusCode(StatusCodes.FOUND); // 302 redirect
        exchange.getResponseHeaders().put(Headers.LOCATION, url.toString());
        exchange.endExchange();
    }

}
