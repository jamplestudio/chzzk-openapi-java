package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jamplestudio.coj.protocol.data.AccessTokenRefreshRequest;
import com.jamplestudio.coj.protocol.data.AccessTokenRefreshResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.Constants;
import com.jamplestudio.coj.utils.HttpResponseParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class AccessTokenRefreshExecutor implements HttpRequestExecutor<AccessTokenRefreshRequest, AccessTokenRefreshResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<AccessTokenRefreshResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull AccessTokenRefreshRequest requestInst) {

        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("grantType", requestInst.grantType());
        requestJson.addProperty("refreshToken", requestInst.refreshToken());
        requestJson.addProperty("clientId", requestInst.clientId());
        requestJson.addProperty("clientSecret", requestInst.clientSecret());

        RequestBody body = RequestBody.create(requestJson.toString(), Constants.MEDIA_TYPE_JSON);

        Request request = new Request.Builder()
                .url(Constants.OPENAPI_URL + "/auth/v1/token")
                .post(body)
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            return HttpResponseParser.parse(response, new TypeToken<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
