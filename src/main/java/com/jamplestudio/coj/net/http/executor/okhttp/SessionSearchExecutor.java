package com.jamplestudio.coj.net.http.executor.okhttp;

import com.google.gson.reflect.TypeToken;
import com.jamplestudio.coj.net.data.SessionSearchRequest;
import com.jamplestudio.coj.net.data.SessionSearchResponse;
import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.net.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.Constants;
import com.jamplestudio.coj.utils.HttpResponseParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class SessionSearchExecutor implements HttpRequestExecutor<SessionSearchRequest, SessionSearchResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<SessionSearchResponse> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull SessionSearchRequest requestInst) {
        HttpUrl url = HttpUrl.get(Constants.OPENAPI_URL + "/open/v1/sessions")
                .newBuilder()
                .addQueryParameter("size", Integer.toString(requestInst.size()))
                .addQueryParameter("page", requestInst.page())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + requestInst.accessToken())
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            return HttpResponseParser.parse(response, new TypeToken<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
