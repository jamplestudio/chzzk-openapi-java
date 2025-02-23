package com.jamplestudio.coj.chzzk.impl;

import com.google.common.collect.ImmutableSet;
import com.jamplestudio.coj.chzzk.*;
import com.jamplestudio.coj.chzzk.data.ChzzkToken;
import com.jamplestudio.coj.net.data.AccessTokenGrantRequest;
import com.jamplestudio.coj.net.data.AccessTokenGrantResponse;
import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.net.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.net.http.server.AuthServer;
import com.jamplestudio.coj.net.http.server.AuthServerHandler;
import com.jamplestudio.coj.net.http.server.undertow.AuthResult;
import com.jamplestudio.coj.net.http.server.undertow.UndertowAuthServer;
import com.jamplestudio.coj.utils.CSRFTokenGenerator;
import io.undertow.util.StatusCodes;
import lombok.Getter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Optional;
import java.util.Set;

@Getter
public class ChzzkAuthServerImpl implements ChzzkAuthServer, ChzzkEventHandlerHolder {

    private final @NotNull String clientId;
    private final @NotNull String clientSecret;
    private final @NotNull String baseUri;
    private final @NotNull String host;
    private final @Range(from = 0, to = 65535) int port;

    private final @NotNull ImmutableSet<ChzzkEventHandler> handlers;
    private final AuthServer server;

    ChzzkAuthServerImpl(
            @NotNull String clientId, @NotNull String clientSecret, @NotNull String baseUri,
            @NotNull String host, @Range(from = 0, to = 65535) int port,
            @NotNull Set<ChzzkEventHandler> handlers
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.baseUri = baseUri;
        this.host = host;
        this.port = port;

        this.handlers = ImmutableSet.copyOf(handlers);
        this.server = new UndertowAuthServer(this, new AuthServerHandler() {

            @Override
            public void onSuccess(@NotNull AuthServer server, @NotNull AuthResult result) {
                // 치지직 인스턴스 생성 (토큰 바인딩 안된 상태)
                Chzzk chzzk = Chzzk.builder()
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .addEventHandler(handlers)
                        .build();

                // 토큰 요청
                Optional<HttpRequestExecutor<AccessTokenGrantRequest, AccessTokenGrantResponse, OkHttpClient>> requester =
                        chzzk.getHttpRequestExecutorFactory().create("access_token_grant");

                if (requester.isEmpty()) {
                    return;
                }

                AccessTokenGrantRequest requestInst = new AccessTokenGrantRequest(clientId, clientSecret, result.code(), result.state());
                Optional<AccessTokenGrantResponse> responseInst = requester.get().execute(ChzzkHttpClient.okhttp(), requestInst);

                if (responseInst.isEmpty()) {
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

                // 유저 인증 성공 이벤트 호출
                if (chzzk instanceof ChzzkEventHandlerHolder holder) {
                    holder.getHandlers().forEach(handler -> handler.onUserRegistered(chzzk, result.user()));
                }
            }
        });
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
