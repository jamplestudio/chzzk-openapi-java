package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.JsonObject;
import com.jamplestudio.coj.protocol.data.ChatSettingsChangeRequest;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.Constants;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class ChatSettingsChangeExecutor implements HttpRequestExecutor<ChatSettingsChangeRequest, Void, OkHttpClient> {

    @Override
    public @NotNull Optional<Void> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChatSettingsChangeRequest requestInst) {
        JsonObject json = new JsonObject();
        json.addProperty("chatAvailableCondition", requestInst.chatAvailableCondition());
        json.addProperty("chatAvailableGroup", requestInst.chatAvailableGroup());
        json.addProperty("minFollowerMinute", requestInst.minFollowerMinute());
        json.addProperty("allowSubscriberInFollowerMode", requestInst.allowSubscriberInFollowerMode());

        RequestBody body = RequestBody.create(json.toString(), Constants.MEDIA_TYPE_JSON);

        Request request = new Request.Builder()
                .url(Constants.OPENAPI_URL + "/open/v1/chats/settings")
                .put(body)
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
