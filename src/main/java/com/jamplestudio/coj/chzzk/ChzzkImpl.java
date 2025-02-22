package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.data.UserInformationRequest;
import com.jamplestudio.coj.protocol.data.UserInformationResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.protocol.http.factory.HttpRequestExecutorFactory;
import com.jamplestudio.coj.protocol.http.factory.HttpRequestExecutorFactoryV1;
import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServer;
import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServerImpl;
import lombok.Getter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Getter
public class ChzzkImpl implements Chzzk {

    private final @NotNull String clientId;
    private final @NotNull String clientSecret;
    private final @NotNull String redirectUri;
    private final @NotNull String host;
    private final @Range(from = 0, to = 65535) int port;

    private final @NotNull ChzzkAuthServer server;
    private final @NotNull HttpRequestExecutorFactory httpRequestExecutorFactory;

    private final @NotNull ChzzkHttpClient<OkHttpClient> httpClient = ChzzkHttpClient.okhttp();

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

        this.server = new ChzzkAuthServerImpl(this);
        this.httpRequestExecutorFactory = new HttpRequestExecutorFactoryV1();
    }

    @Override
    public @NotNull Optional<ChzzkUser> getCurrentUser() {
        return getCurrentUserAsync().join();
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkUser>> getCurrentUserAsync() {
        return CompletableFuture.supplyAsync(() -> {
            Optional<HttpRequestExecutor<UserInformationRequest, UserInformationResponse, OkHttpClient>> executor =
                    httpRequestExecutorFactory.create("user_information");

            UserInformationRequest requestInst = new UserInformationRequest("");
            return executor
                    .map(it -> it.execute(httpClient, requestInst))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(ChzzkUser::of);
        });
    }

}
