package com.jamplestudio.coj.net.http.executor.okhttp;

import com.google.gson.JsonObject;
import com.jamplestudio.coj.net.data.DonationEventUnsubscribeRequest;
import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.net.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.Constants;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class DonationEventUnsubscribeExecutor implements HttpRequestExecutor<DonationEventUnsubscribeRequest, Void, OkHttpClient> {

    @Override
    public @NotNull Optional<Void> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull DonationEventUnsubscribeRequest requestInst) {
        RequestBody body = new FormBody.Builder()
                .add("sessionKey", requestInst.sessionKey())
                .build();

        Request request = new Request.Builder()
                .url(Constants.OPENAPI_URL + "/open/v1/sessions/events/unsubscribe/donation")
                .post(body)
                .addHeader("Authorization", "Bearer " + requestInst.accessToken())
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
