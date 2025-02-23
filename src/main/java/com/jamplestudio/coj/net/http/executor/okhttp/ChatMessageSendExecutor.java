package com.jamplestudio.coj.net.http.executor.okhttp;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jamplestudio.coj.net.data.ChatMessageSendRequest;
import com.jamplestudio.coj.net.data.ChatMessageSendResponse;
import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.net.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.Constants;
import com.jamplestudio.coj.utils.HttpResponseParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class ChatMessageSendExecutor implements HttpRequestExecutor<ChatMessageSendRequest, ChatMessageSendResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<ChatMessageSendResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChatMessageSendRequest requestInst) {

        JsonObject json = new JsonObject();
        json.addProperty("message", requestInst.message());

        RequestBody body = RequestBody.create(json.toString(), Constants.MEDIA_TYPE_JSON);

        Request request = new Request.Builder()
                .url(Constants.OPENAPI_URL + "/open/v1/chats/send")
                .post(body)
                .addHeader("Authorization", "Bearer " + requestInst.accessToken())
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            return HttpResponseParser.parse(response, new TypeToken<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
