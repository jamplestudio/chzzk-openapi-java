package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.JsonObject;
import com.jamplestudio.coj.protocol.data.AccessTokenRevokeRequest;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class AccessTokenRevokeExecutor implements HttpRequestExecutor<AccessTokenRevokeRequest, Void, OkHttpClient> {

    @Override
    public @NotNull Optional<Void> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull AccessTokenRevokeRequest requestInst) {
        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("clientId", requestInst.clientId());
        requestJson.addProperty("clientSecret", requestInst.clientSecret());
        requestJson.addProperty("token", requestInst.token());
        requestJson.addProperty("tokenTypeHint", requestInst.tokenTypeHint().getAsString());

        RequestBody body = RequestBody.create(requestJson.toString(), Constants.MEDIA_TYPE_JSON);

        Request request = new Request.Builder()
                .url(Constants.OPENAPI_URL + "/auth/v1/token/revoke")
                .post(body)
                .build();

        try (Response ignored = client.getNativeHttpClient().newCall(request).execute()) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
