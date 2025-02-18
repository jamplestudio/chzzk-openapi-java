package com.jamplestudio.coj.protocol.http.server.exchange;

import com.jamplestudio.coj.chzzk.Chzzk;
import com.jamplestudio.coj.chzzk.data.ChzzkToken;
import com.jamplestudio.coj.protocol.data.AccessTokenGrantRequest;
import com.jamplestudio.coj.protocol.data.AccessTokenGrantResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServer;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;
import io.undertow.server.session.SessionCookieConfig;
import io.undertow.server.session.SessionManager;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;
import java.util.Map;
import java.util.Optional;

public class AuthCallbackHandler implements HttpHandler {

    private final @NotNull ChzzkAuthServer server;

    public AuthCallbackHandler(@NotNull ChzzkAuthServer server) {
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

        System.out.println("params: " + exchange.getQueryParameters().keySet());
        System.out.println("query: " + exchange.getQueryString());

        String code = getQueryParam(exchange, "code");
        String state = getQueryParam(exchange, "state");

        if (code == null) {
            exchange.setStatusCode(StatusCodes.BAD_REQUEST);
            exchange.getResponseSender().send("Invalid code parameter");
            return;
        }

        // 세션에 저장된 state와 비교
        String savedState = (String) session.getAttribute("OAUTH_STATE");
        if (savedState == null || !savedState.equals(state)) {
            exchange.setStatusCode(StatusCodes.BAD_REQUEST);
            exchange.getResponseSender().send("Invalid state parameter");
            return;
        }

        // (백엔드) 치지직 토큰 발급 API 호출 -> accessToken 획득
        Optional<HttpRequestExecutor<AccessTokenGrantRequest, AccessTokenGrantResponse, OkHttpClient>> requester =
                server.getChzzk().getHttpRequestExecutorFactory().create("access_token_grant");

        if (requester.isEmpty()) {
            exchange.setStatusCode(StatusCodes.BAD_REQUEST);
            exchange.getResponseSender().send("Invalid http requester.");
            return;
        }

        Chzzk chzzk = server.getChzzk();
        AccessTokenGrantRequest requestInst = new AccessTokenGrantRequest(chzzk.getClientId(), chzzk.getClientSecret(), code, state);
        Optional<AccessTokenGrantResponse> responseInst = requester.get().execute(ChzzkHttpClient.okhttp(), requestInst);

        if (responseInst.isEmpty()) {
            exchange.setStatusCode(StatusCodes.UNAUTHORIZED);
            exchange.getResponseSender().send("Failed to get access token");
            return;
        }

        ChzzkToken accessToken = new ChzzkToken(responseInst.get().accessToken(), responseInst.get().refreshToken());

        // 세션에 토큰 저장 (또는 DB/Redis 등)
        session.setAttribute("CHZZK_ACCESS_TOKEN", accessToken.accessToken());
        session.setAttribute("CHZZK_REFRESH_TOKEN", accessToken.refreshToken());

        // 로그인 성공 안내
        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain; charset=utf-8");
        exchange.getResponseSender().send("AccessToken = " + accessToken);
    }

    private @Nullable String getQueryParam(HttpServerExchange exchange, String key) {
        Map<String, Deque<String>> queryParams = exchange.getQueryParameters();
        Deque<String> values = queryParams.get(key);
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.getFirst();
    }

}
