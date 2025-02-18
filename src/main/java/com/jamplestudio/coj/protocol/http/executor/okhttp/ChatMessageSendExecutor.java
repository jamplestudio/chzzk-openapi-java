package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.JsonObject;
import com.jamplestudio.coj.protocol.data.ChatMessageSendRequest;
import com.jamplestudio.coj.protocol.data.ChatMessageSendResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChatMessageSendExecutor implements HttpRequestExecutor<ChatMessageSendRequest, ChatMessageSendResponse, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/open/v1/chats/send";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public @NotNull Optional<ChatMessageSendResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChatMessageSendRequest requestInst) {

        JsonObject json = new JsonObject();
        json.addProperty("message", requestInst.message());

        RequestBody body = RequestBody.create(json.toString(), JSON);

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + requestInst.accessToken())
                .addHeader("Content-Type", "application/json")
                .build();

        return Optional.empty();
    }

}
