package com.jamplestudio.coj.net.http.executor.okhttp;

import com.google.gson.reflect.TypeToken;
import com.jamplestudio.coj.net.data.UserInformationRequest;
import com.jamplestudio.coj.net.data.UserInformationResponse;
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

public class UserInformationExecutor implements HttpRequestExecutor<UserInformationRequest, UserInformationResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<UserInformationResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull UserInformationRequest requestInst) {

        Request request = new Request.Builder()
                .url(Constants.OPENAPI_URL + "/open/v1/users/me")
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
