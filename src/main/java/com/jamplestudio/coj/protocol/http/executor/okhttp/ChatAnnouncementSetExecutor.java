package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.JsonObject;
import com.jamplestudio.coj.protocol.data.ChatAnnouncementSetRequest;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class ChatAnnouncementSetExecutor implements HttpRequestExecutor<ChatAnnouncementSetRequest, Void, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/open/v1/chats/notice";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public @NotNull Optional<Void> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChatAnnouncementSetRequest requestInst) {

        JsonObject json = new JsonObject();
        if (!requestInst.message().isEmpty()) {
            json.addProperty("message", requestInst.message());
        }
        if (!requestInst.messageId().isEmpty()) {
            json.addProperty("messageId", requestInst.messageId());
        }

        RequestBody body = RequestBody.create(json.toString(), JSON);

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + requestInst.accessToken())
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
