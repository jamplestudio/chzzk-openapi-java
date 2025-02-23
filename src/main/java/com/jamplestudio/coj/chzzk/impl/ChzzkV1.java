package com.jamplestudio.coj.chzzk.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.jamplestudio.coj.chzzk.*;
import com.jamplestudio.coj.chzzk.data.*;
import com.jamplestudio.coj.exception.InvalidTokenException;
import com.jamplestudio.coj.net.data.*;
import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.net.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.net.http.factory.HttpRequestExecutorFactory;
import com.jamplestudio.coj.net.http.factory.impl.HttpRequestExecutorFactoryV1;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;


@Getter
@Setter
public class ChzzkV1 implements Chzzk, ChzzkTokenMutator, ChzzkEventHandlerHolder {

    private final @NotNull String clientId;
    private final @NotNull String clientSecret;
    private @Nullable ChzzkToken token;

    private final @NotNull ImmutableSet<ChzzkEventHandler> handlers;

    private final @NotNull HttpRequestExecutorFactory httpRequestExecutorFactory;
    private final @NotNull ChzzkHttpClient<OkHttpClient> httpClient;

    private final @NotNull ChzzkSession session;

    ChzzkV1(
            @NotNull String clientId, @NotNull String clientSecret,
            @Nullable ChzzkToken token, @NotNull Set<ChzzkEventHandler> handlers
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.token = token;
        this.handlers = ImmutableSet.copyOf(handlers);
        this.httpRequestExecutorFactory = new HttpRequestExecutorFactoryV1();
        this.httpClient = ChzzkHttpClient.okhttp();
        this.session = new ChzzkSessionV1(this);

        if (this.token != null) {
            handlers.forEach(handler -> handler.onGrantToken(this));
        }
    }

    public @NotNull Optional<ChzzkToken> getToken() {
        return Optional.ofNullable(token);
    }

    @Override
    public @NotNull CompletableFuture<Void> refreshTokenAsync() {
        return CompletableFuture.runAsync(this::refreshToken);
    }

    @Override
    public void refreshToken() {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<AccessTokenRefreshRequest, AccessTokenRefreshResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("access_token_refresh");

        AccessTokenRefreshRequest requestInst = new AccessTokenRefreshRequest(token.refreshToken(), clientId, clientSecret);
        executor.map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(token -> {
                    this.token = new ChzzkToken(token.accessToken(), token.refreshToken());
                });

        // 토큰 재발급 이벤트 호출
        handlers.forEach(handler -> handler.onRefreshToken(this));
    }

    @Override
    public @NotNull CompletableFuture<Void> revokeTokenAsync() {
        return CompletableFuture.runAsync(this::revokeToken);
    }

    @Override
    public void revokeToken() {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<AccessTokenRevokeRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("access_token_revoke");

        AccessTokenRevokeRequest accessTokenRevokeRequestInst = new AccessTokenRevokeRequest(
                clientId, clientSecret, token.accessToken(), AccessTokenRevokeRequest.TokenTypeHint.ACCESS_TOKEN);
        executor.map(it -> it.execute(httpClient, accessTokenRevokeRequestInst));

        AccessTokenRevokeRequest refreshTokenRevokeRequestInst = new AccessTokenRevokeRequest(
                clientId, clientSecret, token.refreshToken(), AccessTokenRevokeRequest.TokenTypeHint.REFRESH_TOKEN);
        executor.map(it -> it.execute(httpClient, refreshTokenRevokeRequestInst));

        token = null;

        // 토큰 제거 이벤트 호출
        handlers.forEach(handler -> handler.onRevokeToken(this));
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkUser>> getCurrentUserAsync() {
        return CompletableFuture.supplyAsync(this::getCurrentUser);
    }

    @Override
    public @NotNull Optional<ChzzkUser> getCurrentUser() {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

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
        return getCurrentUser().flatMap(user -> getChannel(user.id()));
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkChannel>> getChannelAsync(@NotNull String channelId) {
        return CompletableFuture.supplyAsync(() -> getChannel(channelId));
    }

    @Override
    public @NotNull Optional<ChzzkChannel> getChannel(@NotNull String channelId) {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

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
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

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
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

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
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<LiveSearchRequest, LiveSearchResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("live_search");

        LiveSearchRequest requestInst = new LiveSearchRequest(amount, next, token.accessToken());
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
    public @NotNull CompletableFuture<Void> setChatAnnouncementByMessageAsync(@NotNull String message) {
        return CompletableFuture.runAsync(() -> setChatAnnouncementByMessage(message));
    }

    @Override
    public void setChatAnnouncementByMessage(@NotNull String message) {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<ChatAnnouncementSetRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("channel_information");

        ChatAnnouncementSetRequest requestInst = new ChatAnnouncementSetRequest(message, "", token.accessToken());
        executor.map(it -> it.execute(httpClient, requestInst));
    }

    @Override
    public @NotNull CompletableFuture<Void> setChatAnnouncementByIdAsync(@NotNull String messageId) {
        return CompletableFuture.runAsync(() -> setChatAnnouncementById(messageId));
    }

    @Override
    public void setChatAnnouncementById(@NotNull String messageId) {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

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
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

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
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

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
    public @NotNull CompletableFuture<Void> setChatSettingsAsync(@NotNull ChzzkChatSettings settings) {
        return CompletableFuture.runAsync(() -> setChatSettings(settings));
    }

    @Override
    public void setChatSettings(@NotNull ChzzkChatSettings settings) {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<ChatSettingsChangeRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("chat_settings_change");

        ChatSettingsChangeRequest requestInst = new ChatSettingsChangeRequest(
                settings.chatAvailableCondition().toString(),
                settings.chatAvailableGroup().toString(),
                settings.minimumFollowerTimeInMinutes(),
                settings.isSubscriberAllowedInFollowerMode(),
                token.accessToken()
        );
        executor.map(it -> it.execute(httpClient, requestInst));
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkLiveSettings>> getLiveSettingsAsync() {
        return CompletableFuture.supplyAsync(this::getLiveSettings);
    }

    @Override
    public @NotNull Optional<ChzzkLiveSettings> getLiveSettings() {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<LiveSettingsRequest, LiveSettingsResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("live_settings");

        LiveSettingsRequest requestInst = new LiveSettingsRequest(token.accessToken());
        return executor
                .map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ChzzkLiveSettings::of);
    }

    @Override
    public @NotNull CompletableFuture<Void> setLiveSettingsAsync(@NotNull ChzzkLiveSettings settings) {
        return CompletableFuture.runAsync(() -> setLiveSettings(settings));
    }

    @Override
    public void setLiveSettings(@NotNull ChzzkLiveSettings settings) {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<LiveSettingsChangeRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("live_settings_change");

        LiveSettingsChangeRequest requestInst = new LiveSettingsChangeRequest(
                settings.liveTitle(), settings.categoryType().toString(),
                settings.categoryId(), settings.tags(),
                token.accessToken()
        );
        executor.map(it -> it.execute(httpClient, requestInst));
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkLiveStreamKey>> getLiveStreamKeyAsync() {
        return CompletableFuture.supplyAsync(this::getLiveStreamKey);
    }

    @Override
    public @NotNull Optional<ChzzkLiveStreamKey> getLiveStreamKey() {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<LiveStreamKeyRequest, LiveStreamKeyResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("live_stream_key");

        LiveStreamKeyRequest requestInst = new LiveStreamKeyRequest(token.accessToken());
        return executor
                .map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ChzzkLiveStreamKey::of);
    }

    @Override
    public @NotNull CompletableFuture<Optional<ChzzkSessionUrl>> getSessionUrlAsync() {
        return CompletableFuture.supplyAsync(this::getSessionUrl);
    }

    @Override
    public @NotNull Optional<ChzzkSessionUrl> getSessionUrl() {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<SessionUrlRequest, SessionUrlResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("session_url");

        SessionUrlRequest requestInst = new SessionUrlRequest(token.accessToken());
        return executor
                .map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(it -> new ChzzkSessionUrl(it.url()));
    }

    @Override
    public @NotNull CompletableFuture<List<ChzzkSessionSearchResult>> searchSessionsAsync(@Range(
            from = 1, to = 50) int amount, @Range(from = 0, to = Integer.MAX_VALUE) int page) {
        return CompletableFuture.supplyAsync(() -> searchSessions(amount, page));
    }

    @Override
    public @NotNull List<ChzzkSessionSearchResult> searchSessions(
            @Range(from = 1, to = 50) int amount, @Range(from = 0, to = Integer.MAX_VALUE) int page) {

        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<SessionSearchRequest, SessionSearchResponse, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("session_search");

        SessionSearchRequest requestInst = new SessionSearchRequest(amount, Integer.toString(page), token.accessToken());
        return executor
                .map(it -> it.execute(httpClient, requestInst))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(SessionSearchResponse::data)
                .orElse(Lists.newArrayList())
                .stream()
                .map(ChzzkSessionSearchResult::of)
                .toList();
    }

    @Override
    public @NotNull CompletableFuture<Void> subscribeChatAsync(@NotNull String sessionKey) {
        return CompletableFuture.runAsync(() -> subscribeChat(sessionKey));
    }

    @Override
    public void subscribeChat(@NotNull String sessionKey) {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<ChatEventSubscribeRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("chat_event_subscribe");

        ChatEventSubscribeRequest requestInst = new ChatEventSubscribeRequest(sessionKey, token.accessToken());
        executor.map(it -> it.execute(httpClient, requestInst));
    }

    @Override
    public @NotNull CompletableFuture<Void> unsubscribeChatAsync(@NotNull String sessionKey) {
        return CompletableFuture.runAsync(() -> unsubscribeChat(sessionKey));
    }

    @Override
    public void unsubscribeChat(@NotNull String sessionKey) {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<ChatEventUnsubscribeRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("chat_event_unsubscribe");

        ChatEventUnsubscribeRequest requestInst = new ChatEventUnsubscribeRequest(sessionKey, token.accessToken());
        executor.map(it -> it.execute(httpClient, requestInst));
    }

    @Override
    public @NotNull CompletableFuture<Void> subscribeDonationAsync(@NotNull String sessionKey) {
        return CompletableFuture.runAsync(() -> subscribeDonation(sessionKey));
    }

    @Override
    public void subscribeDonation(@NotNull String sessionKey) {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<DonationEventSubscribeRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("donation_event_subscribe");

        DonationEventSubscribeRequest requestInst = new DonationEventSubscribeRequest(sessionKey, token.accessToken());
        executor.map(it -> it.execute(httpClient, requestInst));
    }

    @Override
    public @NotNull CompletableFuture<Void> unsubscribeDonationAsync(@NotNull String sessionKey) {
        return CompletableFuture.runAsync(() -> unsubscribeDonation(sessionKey));
    }

    @Override
    public void unsubscribeDonation(@NotNull String sessionKey) {
        if (token == null) {
            throw new InvalidTokenException("Token cannot be null.");
        }

        Optional<HttpRequestExecutor<DonationEventUnsubscribeRequest, Void, OkHttpClient>> executor =
                httpRequestExecutorFactory.create("donation_event_unsubscribe");

        DonationEventUnsubscribeRequest requestInst = new DonationEventUnsubscribeRequest(sessionKey, token.accessToken());
        executor.map(it -> it.execute(httpClient, requestInst));
    }

}
