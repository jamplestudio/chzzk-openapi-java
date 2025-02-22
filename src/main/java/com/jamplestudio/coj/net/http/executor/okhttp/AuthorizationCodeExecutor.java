package com.jamplestudio.coj.net.http.executor.okhttp;

import com.google.gson.reflect.TypeToken;
import com.jamplestudio.coj.net.data.AuthorizationCodeRequest;
import com.jamplestudio.coj.net.data.AuthorizationCodeResponse;
import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.net.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.HttpResponseParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class AuthorizationCodeExecutor implements HttpRequestExecutor<AuthorizationCodeRequest, AuthorizationCodeResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<AuthorizationCodeResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull AuthorizationCodeRequest requestInst) {

        HttpUrl url = HttpUrl.get("https://chzzk.naver.com/account-interlock").newBuilder()
                .addQueryParameter("clientId", requestInst.clientId())
                .addQueryParameter("redirectUri", requestInst.redirectUri())
                .addQueryParameter("state", requestInst.state())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            return HttpResponseParser.parse(response, new TypeToken<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
