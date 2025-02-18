package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jamplestudio.coj.protocol.data.LiveSettingsChangeRequest;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class LiveSettingsChangeExecutor implements HttpRequestExecutor<LiveSettingsChangeRequest, Void, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/open/v1/lives/setting";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public @NotNull Optional<Void> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull LiveSettingsChangeRequest requestInst) {

        JsonObject json = new JsonObject();
        json.addProperty("defaultLiveTitle", requestInst.defaultLiveTitle());
        json.addProperty("categoryType", requestInst.categoryType());
        json.addProperty("categoryId", requestInst.categoryId());

        JsonArray arr = new JsonArray();
        requestInst.tags().forEach(arr::add);
        json.add("tags", arr);

        RequestBody body = RequestBody.create(json.toString(), JSON);

        Request request = new Request.Builder()
                .url(URL)
                .patch(body)
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
