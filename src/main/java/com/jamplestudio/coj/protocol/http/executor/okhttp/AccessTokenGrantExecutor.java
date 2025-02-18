package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jamplestudio.coj.protocol.data.AccessTokenGrantRequest;
import com.jamplestudio.coj.protocol.data.AccessTokenGrantResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class AccessTokenGrantExecutor implements HttpRequestExecutor<AccessTokenGrantRequest, AccessTokenGrantResponse, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/auth/v1/token";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final @NotNull Gson GSON = new Gson();

    @Override
    public @NotNull Optional<AccessTokenGrantResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull AccessTokenGrantRequest requestInst) {

        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("grantType", requestInst.grantType());
        requestJson.addProperty("clientId", requestInst.clientId());
        requestJson.addProperty("clientSecret", requestInst.clientSecret());
        requestJson.addProperty("code", requestInst.code());
        requestJson.addProperty("state", requestInst.state());

        RequestBody body = RequestBody.create(requestJson.toString(), JSON);

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JsonObject responseJson = GSON.fromJson(response.body().string(), JsonObject.class);
                if (responseJson.has("content")) {
                    AccessTokenGrantResponse responseInst = GSON.fromJson(responseJson.get("content"), AccessTokenGrantResponse.class);
                    return Optional.of(responseInst);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
