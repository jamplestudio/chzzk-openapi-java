package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.ChatAnnouncementSetRequest;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChatAnnouncementSetExecutor implements HttpRequestExecutor<ChatAnnouncementSetRequest, Void> {

    @Override
    public @NotNull Optional<Void> execute(
            @NotNull OkHttpClient client, @NotNull ChatAnnouncementSetRequest request) {
        return Optional.empty();
    }

}
