package com.jamplestudio.coj.net.http.executor.okhttp;

import com.google.gson.reflect.TypeToken;
import com.jamplestudio.coj.net.data.LiveStreamKeyRequest;
import com.jamplestudio.coj.net.data.LiveStreamKeyResponse;
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

public class LiveStreamKeyExecutor implements HttpRequestExecutor<LiveStreamKeyRequest, LiveStreamKeyResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<LiveStreamKeyResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull LiveStreamKeyRequest requestInst) {

        Request request = new Request.Builder()
                .url(Constants.OPENAPI_URL + "/open/v1/streams/key")
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
