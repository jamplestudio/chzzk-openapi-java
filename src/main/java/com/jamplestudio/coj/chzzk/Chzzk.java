package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.http.factory.HttpRequestExecutorFactory;
import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface Chzzk {

    static @NotNull ChzzkBuilder builder() {
        return new ChzzkBuilder();
    }

    @NotNull String getClientId();

    @NotNull String getClientSecret();

    @NotNull String getRedirectUri();

    @NotNull String getHost();

    int getPort();

    @NotNull ChzzkAuthServer getServer();

    @NotNull HttpRequestExecutorFactory getHttpRequestExecutorFactory();

    @NotNull ChzzkToken getToken();

    void refreshToken();

    void revokeToken();

    @NotNull CompletableFuture<Optional<ChzzkUser>> getCurrentUserAsync();

    @NotNull Optional<ChzzkUser> getCurrentUser();

    @NotNull CompletableFuture<Optional<ChzzkChannel>> getCurrentChannelAsync();

    @NotNull Optional<ChzzkChannel> getCurrentChannel();

    @NotNull CompletableFuture<Optional<ChzzkChannel>> getChannelAsync(@NotNull String channelId);

    @NotNull Optional<ChzzkChannel> getChannel(@NotNull String channelId);

    @NotNull CompletableFuture<List<ChzzkChannel>> getChannelsAsync(@NotNull Collection<String> channelIds);

    @NotNull List<ChzzkChannel> getChannels(@NotNull Collection<String> channelIds);

    @NotNull CompletableFuture<List<ChzzkCategorySearchResult>> searchCategoriesAsync(@NotNull String categoryName, @Range(from = 1, to = 50) int amount);

    @NotNull List<ChzzkCategorySearchResult> searchCategories(@NotNull String categoryName, @Range(from = 1, to = 50) int amount);

    @NotNull CompletableFuture<List<ChzzkLiveSearchResult>> searchLiveStreamsAsync(@Range(from = 1, to = 50) int amount);

    @NotNull List<ChzzkLiveSearchResult> searchLiveStreams(@Range(from = 1, to = 50) int amount);

    @NotNull CompletableFuture<List<ChzzkLiveSearchResult>> searchLiveStreamsAsync(@Range(from = 1, to = 50) int amount, @NotNull String next);

    @NotNull List<ChzzkLiveSearchResult> searchLiveStreams(@Range(from = 1, to = 50) int amount, @NotNull String next);

    @NotNull CompletableFuture<Void> setChatAnnouncementByMessageAsync(@NotNull String message);

    void setChatAnnouncementByMessage(@NotNull String message);

    @NotNull CompletableFuture<Void> setChatAnnouncementByIdAsync(@NotNull String messageId);

    void setChatAnnouncementById(@NotNull String messageId);

    @NotNull CompletableFuture<Optional<ChzzkChatMessageSendResult>> sendChatMessageAsync(@NotNull String message);

    @NotNull Optional<ChzzkChatMessageSendResult> sendChatMessage(@NotNull String message);

    @NotNull CompletableFuture<Optional<ChzzkChatSettings>> getChatSettingsAsync();

    @NotNull Optional<ChzzkChatSettings> getChatSettings();

    @NotNull CompletableFuture<Void> setChatSettingsAsync(@NotNull ChzzkChatSettings settings);

    void setChatSettings(@NotNull ChzzkChatSettings settings);

    @NotNull CompletableFuture<Optional<ChzzkLiveSettings>> getLiveSettingsAsync();

    @NotNull Optional<ChzzkLiveSettings> getLiveSettings();

    @NotNull CompletableFuture<Void> setLiveSettingsAsync(@NotNull ChzzkLiveSettings settings);

    void setLiveSettings(@NotNull ChzzkLiveSettings settings);

}
