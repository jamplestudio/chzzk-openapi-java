package com.jamplestudio.coj.chzzk;

import com.google.common.collect.Lists;
import com.jamplestudio.coj.protocol.data.*;
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
    public void refreshToken() {
        Optional<HttpRequestExecutor<AccessTokenRefreshRequest, AccessTokenRefreshResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("access_token_refresh");

        AccessTokenRefreshRequest requestInst = new AccessTokenRefreshRequest(token.refreshToken(), clientId, clientSecret);
        executor.map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(token -> {
                    this.token = new ChzzkToken(token.accessToken(), token.refreshToken());
                });
    }

    @Override
    public void revokeToken() {
        Optional<HttpRequestExecutor<AccessTokenRevokeRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("access_token_revoke");

        AccessTokenRevokeRequest accessTokenRevokeRequestInst = new AccessTokenRevokeRequest(
                clientId, clientSecret, token.accessToken(), AccessTokenRevokeRequest.TokenTypeHint.ACCESS_TOKEN);
        executor.map(it -> it.execute(httpClient, accessTokenRevokeRequestInst));

        AccessTokenRevokeRequest refreshTokenRevokeRequestInst = new AccessTokenRevokeRequest(
                clientId, clientSecret, token.refreshToken(), AccessTokenRevokeRequest.TokenTypeHint.REFRESH_TOKEN);
        executor.map(it -> it.execute(httpClient, refreshTokenRevokeRequestInst));
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkUser>> getCurrentUserAsync() {
        return CompletableFuture.supplyAsync(this::getCurrentUser);
    }

    @Override
    public @NotNull Optional<ChzzkUser> getCurrentUser() {
        Optional<HttpRequestExecutor<UserInformationRequest, UserInformationResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("user_information");

        UserInformationRequest requestInst = new UserInformationRequest(token.accessToken());
        return executor
                .map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ChzzkUser::of);
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkChannel>> getCurrentChannelAsync() {
        return CompletableFuture.supplyAsync(this::getCurrentChannel);
    }

    @Override
    public @NotNull Optional<ChzzkChannel> getCurrentChannel() {
        return getCurrentUser().flatMap(chzzkUser -> getChannel(chzzkUser.getId()));
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkChannel>> getChannelAsync(@NotNull String channelId) {
        return CompletableFuture.supplyAsync(() -> getChannel(channelId));
    }

    @Override
    public @NotNull Optional<ChzzkChannel> getChannel(@NotNull String channelId) {
        Optional<HttpRequestExecutor<ChannelInformationRequest, ChannelInformationResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("channel_information");

        ChannelInformationRequest requestInst = new ChannelInformationRequest(Lists.newArrayList(channelId), token.accessToken());
        return executor
                .map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(it -> !it.data().isEmpty())
                .map(it -> it.data().getFirst())
                .map(ChzzkChannel::of);
    }

    @Override
    public @NotNull CompletableFuture<List<ChzzkChannel>> getChannelsAsync(@NotNull Collection<String> channelIds) {
        return CompletableFuture.supplyAsync(() -> getChannels(channelIds));
    }

    @Override
    public @NotNull List<ChzzkChannel> getChannels(@NotNull Collection<String> channelIds) {
        Optional<HttpRequestExecutor<ChannelInformationRequest, ChannelInformationResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("channel_information");

        ChannelInformationRequest requestInst = new ChannelInformationRequest(Lists.newArrayList(channelIds), token.accessToken());
        return executor
                .map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ChannelInformationResponse::data)
                .orElse(Lists.newArrayList())
                .stream()
                .map(ChzzkChannel::of)
                .toList();
    }

    @Override
    public @NotNull CompletableFuture<List<ChzzkCategorySearchResult>> searchCategoriesAsync(
            @NotNull String categoryName, @Range(from = 1, to = 50) int amount) {
        return CompletableFuture.supplyAsync(() -> searchCategories(categoryName, amount));
    }

    @Override
    public @NotNull List<ChzzkCategorySearchResult> searchCategories(
            @NotNull String categoryName, @Range(from = 1, to = 50) int amount) {

        Optional<HttpRequestExecutor<CategorySearchRequest, CategorySearchResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("channel_information");

        CategorySearchRequest requestInst = new CategorySearchRequest(amount, categoryName, token.accessToken());
        return executor
                .map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(CategorySearchResponse::data)
                .orElse(Lists.newArrayList())
                .stream()
                .map(ChzzkCategorySearchResult::of)
                .toList();
    }

    @Override
    public @NotNull CompletableFuture<List<ChzzkLiveSearchResult>> searchLiveStreamsAsync(@Range(from = 1, to = 50) int amount) {
        return CompletableFuture.supplyAsync(() -> searchLiveStreams(amount));
    }

    @Override
    public @NotNull List<ChzzkLiveSearchResult> searchLiveStreams(@Range(from = 1, to = 50) int amount) {
        return searchLiveStreams(amount, "");
    }

    @Override
    public @NotNull CompletableFuture<List<ChzzkLiveSearchResult>> searchLiveStreamsAsync(@Range(from = 1, to = 50) int amount, @NotNull String next) {
        return CompletableFuture.supplyAsync(() -> searchLiveStreams(amount, next));
    }

    @Override
    public @NotNull List<ChzzkLiveSearchResult> searchLiveStreams(@Range(from = 1, to = 50) int amount, @NotNull String next) {
        Optional<HttpRequestExecutor<LiveSearchRequest, LiveSearchResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("live_search");

        LiveSearchRequest requestInst = new LiveSearchRequest(amount, next);
        List<ChzzkLiveSearchResult> results = Lists.newArrayList();
        executor.map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(it -> {
                    it.data().forEach(data -> {
                        ChzzkLiveSearchResult result = ChzzkLiveSearchResult.of(data, it.page());
                    });
                });
        return results;
    }

    @Override
    public void setChatAnnouncementByMessageAsync(@NotNull String message) {
        CompletableFuture.runAsync(() -> setChatAnnouncementByMessage(message));
    }

    @Override
    public void setChatAnnouncementByMessage(@NotNull String message) {
        Optional<HttpRequestExecutor<ChatAnnouncementSetRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("channel_information");

        ChatAnnouncementSetRequest requestInst = new ChatAnnouncementSetRequest(message, "", token.accessToken());
        executor.map(it -> it.execute(httpClient, requestInst));
    }

    @Override
    public void setChatAnnouncementByIdAsync(@NotNull String messageId) {
        CompletableFuture.runAsync(() -> setChatAnnouncementById(messageId));
    }

    @Override
    public void setChatAnnouncementById(@NotNull String messageId) {
        Optional<HttpRequestExecutor<ChatAnnouncementSetRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("channel_information");

        ChatAnnouncementSetRequest requestInst = new ChatAnnouncementSetRequest("", messageId, token.accessToken());
        executor.map(it -> it.execute(httpClient, requestInst));
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkChatMessageSendResult>> sendChatMessageAsync(@NotNull String message) {
        return CompletableFuture.supplyAsync(() -> sendChatMessage(message));
    }

    @Override
    public @NotNull Optional<ChzzkChatMessageSendResult> sendChatMessage(@NotNull String message) {
        Optional<HttpRequestExecutor<ChatMessageSendRequest, ChatMessageSendResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("chat_message_send");

        ChatMessageSendRequest requestInst = new ChatMessageSendRequest(message, token.accessToken());
        return executor
                .map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(it -> ChzzkChatMessageSendResult.of(it.messageId(), message));
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkChatSettings>> getChatSettingsAsync() {
        return CompletableFuture.supplyAsync(this::getChatSettings);
    }

    @Override
    public @NotNull Optional<ChzzkChatSettings> getChatSettings() {
        Optional<HttpRequestExecutor<ChatSettingsRequest, ChatSettingsResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("chat_settings");

        ChatSettingsRequest requestInst = new ChatSettingsRequest(token.accessToken());
        return executor
                .map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ChzzkChatSettings::of);
    }

    @Override
    public void setChatSettingsAsync(@NotNull ChzzkChatSettings settings) {
        CompletableFuture.runAsync(() -> setChatSettings(settings));
    }

    @Override
    public void setChatSettings(@NotNull ChzzkChatSettings settings) {
        Optional<HttpRequestExecutor<ChatSettingsChangeRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("chat_settings_change");

        ChatSettingsChangeRequest requestInst = new ChatSettingsChangeRequest(
                settings.getChatAvailableCondition().toString(),
                settings.getChatAvailableGroup().toString(),
                settings.getMinimumFollowerTimeInMinutes(),
                settings.isSubscriberAllowedInFollowerMode(),
                token.accessToken()
        );
        executor.map(it -> it.execute(httpClient, requestInst));
    }

}
