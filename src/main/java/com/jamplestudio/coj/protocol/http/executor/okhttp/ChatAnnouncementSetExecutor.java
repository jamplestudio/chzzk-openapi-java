package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.ChatAnnouncementSetRequest;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChatAnnouncementSetExecutor implements HttpRequestExecutor<ChatAnnouncementSetRequest, Void, OkHttpClient> {

    @Override
    public @NotNull Optional<Void> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChatAnnouncementSetRequest request) {
        return Optional.empty();
    }

}
