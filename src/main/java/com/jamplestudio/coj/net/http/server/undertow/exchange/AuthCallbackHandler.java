package com.jamplestudio.coj.net.http.server.undertow.exchange;

import com.jamplestudio.coj.chzzk.*;
import com.jamplestudio.coj.chzzk.data.ChzzkToken;
import com.jamplestudio.coj.net.data.AccessTokenGrantRequest;
import com.jamplestudio.coj.net.data.AccessTokenGrantResponse;
import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.net.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.net.http.server.AuthServer;
import com.jamplestudio.coj.net.http.server.AuthServerHandler;
import com.jamplestudio.coj.net.http.server.undertow.AuthResult;
import com.jamplestudio.coj.utils.HttpExchangeQueryParameterParser;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;
import io.undertow.server.session.SessionCookieConfig;
import io.undertow.server.session.SessionManager;
import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Deque;
import java.util.Map;
import java.util.Optional;

public class AuthCallbackHandler implements HttpHandler {

    private final @NotNull AuthServer server;

    public AuthCallbackHandler(@NotNull AuthServer server) {
        this.server = server;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        SessionManager manager = server.getSessionManager();
        SessionCookieConfig cookieConfig = server.getSessionCookieConfig();

        Session session = manager.getSession(exchange, cookieConfig);
        if (session == null) {
            session = manager.createSession(exchange, cookieConfig);
        }

        String code = HttpExchangeQueryParameterParser.parse(exchange, "code");
        String state = HttpExchangeQueryParameterParser.parse(exchange, "state");

        if (code == null) {
            exchange.setStatusCode(StatusCodes.BAD_REQUEST);
            exchange.getResponseSender().send("Invalid code parameter");
            AuthResult result = new AuthResult("", "", AuthResult.Type.INVALID_CODE_PARAMETER, null);
            server.getHandler().onFailure(server, result);
            return;
        }

        // 세션에 저장된 state와 비교
        String savedState = (String) session.getAttribute("OAUTH_STATE");
        if (savedState == null || !savedState.equals(state)) {
            exchange.setStatusCode(StatusCodes.BAD_REQUEST);
            exchange.getResponseSender().send("Invalid state parameter");
            AuthResult result = new AuthResult("", "", AuthResult.Type.INVALID_STATE_PARAMETER, null);
            server.getHandler().onFailure(server, result);
            return;
        }

        // 로그인 성공 안내
        String user = (String) session.getAttribute("USER");
        AuthResult result = new AuthResult(code, state, AuthResult.Type.SUCCESS, user);
        server.getHandler().onSuccess(server, result);

        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseSender().send("Success!");
        exchange.endExchange();
    }

}
