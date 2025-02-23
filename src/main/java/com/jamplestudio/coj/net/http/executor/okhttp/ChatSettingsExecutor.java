package com.jamplestudio.coj.net.http.executor.okhttp;

import com.google.gson.reflect.TypeToken;
import com.jamplestudio.coj.net.data.ChatSettingsRequest;
import com.jamplestudio.coj.net.data.ChatSettingsResponse;
import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.net.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.Constants;
import com.jamplestudio.coj.utils.HttpResponseParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class ChatSettingsExecutor implements HttpRequestExecutor<ChatSettingsRequest, ChatSettingsResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<ChatSettingsResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChatSettingsRequest requestInst) {

        Request request = new Request.Builder()
                .url(Constants.OPENAPI_URL + "/open/v1/chats/settings")
                .get()
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
