package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.Gson;
import com.jamplestudio.coj.protocol.data.CategorySearchRequest;
import com.jamplestudio.coj.protocol.data.CategorySearchResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class CategorySearchExecutor implements HttpRequestExecutor<CategorySearchRequest, CategorySearchResponse, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/open/v1/categories/search";
    private static final @NotNull Gson GSON = new Gson();

    @Override
    public @NotNull Optional<CategorySearchResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull CategorySearchRequest requestInst) {

        HttpUrl url = HttpUrl.get(URL)
                .newBuilder()
                .addQueryParameter("query", requestInst.query())
                .addQueryParameter("size", Integer.toString(requestInst.size()))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + requestInst.accessToken())
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                CategorySearchResponse responseInst = GSON.fromJson(response.body().string(), CategorySearchResponse.class);
                return Optional.of(responseInst);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
