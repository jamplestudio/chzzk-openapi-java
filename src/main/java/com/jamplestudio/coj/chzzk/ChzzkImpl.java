package com.jamplestudio.coj.chzzk;

import com.google.common.collect.Lists;
import com.jamplestudio.coj.chzzk.data.ChzzkToken;
import com.jamplestudio.coj.protocol.data.ChannelInformationRequest;
import com.jamplestudio.coj.protocol.data.ChannelInformationResponse;
import com.jamplestudio.coj.protocol.data.UserInformationRequest;
import com.jamplestudio.coj.protocol.data.UserInformationResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.protocol.http.factory.HttpRequestExecutorFactory;
import com.jamplestudio.coj.protocol.http.factory.HttpRequestExecutorFactoryV1;
import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServer;
import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServerImpl;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Getter
@Setter
public class ChzzkImpl implements Chzzk, ChzzkTokenMutator {

    private final @NotNull String clientId;
    private final @NotNull String clientSecret;
    private final @NotNull String redirectUri;
    private final @NotNull String host;
    private final @Range(from = 0, to = 65535) int port;

    private final @NotNull ChzzkAuthServer server;
    private final @NotNull HttpRequestExecutorFactory httpRequestExecutorFactory;

    private final @NotNull ChzzkHttpClient<OkHttpClient> httpClient = ChzzkHttpClient.okhttp();

    private ChzzkToken token;

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

    @Override
    public @NotNull Optional<ChzzkChannel> getCurrentChannel() {
        return getCurrentChannelAsync().join();
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkChannel>> getCurrentChannelAsync() {
        //TODO: 현재 채널 아이디 입력할 것
        return getChannelAsync("current-channel-id");
    }

    @Override
    public @NotNull Optional<ChzzkChannel> getChannel(@NotNull String channelId) {
        return getChannelAsync(channelId).join();
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkChannel>> getChannelAsync(@NotNull String channelId) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<HttpRequestExecutor<ChannelInformationRequest, ChannelInformationResponse, OkHttpClient>> executor =
                    httpRequestExecutorFactory.create("channel_information");

            ChannelInformationRequest requestInst = new ChannelInformationRequest(Lists.newArrayList(channelId), "");
            return executor
                    .map(it -> it.execute(httpClient, requestInst))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(it -> !it.data().isEmpty())
                    .map(it -> it.data().getFirst())
                    .map(ChzzkChannel::of);
        });
    }

    @Override
    public @NotNull List<ChzzkChannel> getChannels(@NotNull Collection<String> channelIds) {
        return getChannelsAsync(channelIds).join();
    }

    @Override
    public @NotNull CompletableFuture<List<ChzzkChannel>> getChannelsAsync(@NotNull Collection<String> channelIds) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<HttpRequestExecutor<ChannelInformationRequest, ChannelInformationResponse, OkHttpClient>> executor =
                    httpRequestExecutorFactory.create("channel_information");

            ChannelInformationRequest requestInst = new ChannelInformationRequest(Lists.newArrayList(channelIds), "");
            return executor
                    .map(it -> it.execute(httpClient, requestInst))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(ChannelInformationResponse::data)
                    .orElse(Lists.newArrayList())
                    .stream()
                    .map(ChzzkChannel::of)
                    .toList();
        });
    }
}
