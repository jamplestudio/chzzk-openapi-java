package com.jamplestudio.coj.net.http.executor.okhttp;

import com.google.gson.JsonObject;
import com.jamplestudio.coj.net.data.ChatAnnouncementSetRequest;
import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.net.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class ChatAnnouncementSetExecutor implements HttpRequestExecutor<ChatAnnouncementSetRequest, Void, OkHttpClient> {

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

        RequestBody body = RequestBody.create(json.toString(), Constants.MEDIA_TYPE_JSON);

        Request request = new Request.Builder()
                .url(Constants.OPENAPI_URL + "/open/v1/chats/notice")
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
