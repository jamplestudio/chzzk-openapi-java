package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.http.factory.HttpRequestExecutorFactory;
import com.jamplestudio.coj.protocol.http.server.ChzzkAuthServer;
import org.jetbrains.annotations.NotNull;

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

}
