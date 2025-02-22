package com.jamplestudio.coj.net.http.server.undertow.exchange;

import com.jamplestudio.coj.chzzk.*;
import com.jamplestudio.coj.net.data.AccessTokenGrantRequest;
import com.jamplestudio.coj.net.data.AccessTokenGrantResponse;
import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.net.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.net.http.server.AuthServer;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;
import io.undertow.server.session.SessionCookieConfig;
import io.undertow.server.session.SessionManager;
import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

        // 치지직 인스턴스 생성 (토큰 바인딩 안된 상태)
        ChzzkAuthServer chzzkServer = server.getChzzkAuthServer();
        ChzzkBuilder chzzkBuilder = server.getChzzkAuthServer().newChzzkBuilder()
                .clientId(chzzkServer.getClientId())
                .clientSecret(chzzkServer.getClientSecret());

        if (chzzkServer instanceof ChzzkEventHandlerHolder holder) {
            chzzkBuilder.addEventHandler(holder.getHandlers());
        }

        Chzzk chzzk = chzzkBuilder.build();

        // 토큰 요청
        Optional<HttpRequestExecutor<AccessTokenGrantRequest, AccessTokenGrantResponse, OkHttpClient>> requester =
                chzzk.getHttpRequestExecutorFactory().create("access_token_grant");

        if (requester.isEmpty()) {
            exchange.setStatusCode(StatusCodes.BAD_REQUEST);
            exchange.getResponseSender().send("Invalid http requester.");
            return;
        }

        AccessTokenGrantRequest requestInst = new AccessTokenGrantRequest(chzzkServer.getClientId(), chzzkServer.getClientSecret(), code, state);
        Optional<AccessTokenGrantResponse> responseInst = requester.get().execute(ChzzkHttpClient.okhttp(), requestInst);

        if (responseInst.isEmpty()) {
            exchange.setStatusCode(StatusCodes.UNAUTHORIZED);
            exchange.getResponseSender().send("Failed to get access token");
            return;
        }

        // 토큰 바인드
        ChzzkToken token = new ChzzkToken(responseInst.get().accessToken(), responseInst.get().refreshToken());
        if (chzzk instanceof ChzzkTokenMutator mutator) {
            mutator.setToken(token);

            // 치지직 토큰 발급 이벤트 호출
            if (chzzk instanceof ChzzkEventHandlerHolder holder) {
                holder.getHandlers().forEach(handler -> handler.onGrantToken(chzzk));
            }
        }

        // 로그인 성공 안내
        exchange.setStatusCode(StatusCodes.OK);
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
