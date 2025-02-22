package com.jamplestudio.coj.net.http.executor.okhttp;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jamplestudio.coj.net.data.AccessTokenGrantRequest;
import com.jamplestudio.coj.net.data.AccessTokenGrantResponse;
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

public class AccessTokenGrantExecutor implements HttpRequestExecutor<AccessTokenGrantRequest, AccessTokenGrantResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<AccessTokenGrantResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull AccessTokenGrantRequest requestInst) {

        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("grantType", requestInst.grantType());
        requestJson.addProperty("clientId", requestInst.clientId());
        requestJson.addProperty("clientSecret", requestInst.clientSecret());
        requestJson.addProperty("code", requestInst.code());
        requestJson.addProperty("state", requestInst.state());

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
