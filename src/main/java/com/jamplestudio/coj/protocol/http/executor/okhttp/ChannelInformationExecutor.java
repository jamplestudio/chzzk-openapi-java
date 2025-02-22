package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.reflect.TypeToken;
import com.jamplestudio.coj.protocol.data.ChannelInformationRequest;
import com.jamplestudio.coj.protocol.data.ChannelInformationResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import com.jamplestudio.coj.utils.Constants;
import com.jamplestudio.coj.utils.HttpResponseParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class ChannelInformationExecutor implements HttpRequestExecutor<ChannelInformationRequest, ChannelInformationResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<ChannelInformationResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChannelInformationRequest requestInst) {

        HttpUrl.Builder builder = HttpUrl.get(Constants.OPENAPI_URL + "/open/v1/channels").newBuilder();
        requestInst.channelIds().forEach(id -> builder.addQueryParameter("channelIds", id));

        HttpUrl url = builder.build();

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
