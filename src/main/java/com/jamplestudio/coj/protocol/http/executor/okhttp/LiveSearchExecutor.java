package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.reflect.TypeToken;
import com.jamplestudio.coj.protocol.data.LiveSearchRequest;
import com.jamplestudio.coj.protocol.data.LiveSearchResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.Constants;
import com.jamplestudio.coj.utils.HttpResponseParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class LiveSearchExecutor implements HttpRequestExecutor<LiveSearchRequest, LiveSearchResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<LiveSearchResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull LiveSearchRequest requestInst) {

        HttpUrl.Builder builder = HttpUrl.get(Constants.OPENAPI_URL + "/open/v1/lives").newBuilder();
        builder.addQueryParameter("size", Integer.toString(requestInst.size()));
        builder.addQueryParameter("next", requestInst.next());

        HttpUrl url = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + "YOUR_ACCESS_TOKEN")
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            return HttpResponseParser.parse(response, new TypeToken<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
