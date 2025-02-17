package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.ChannelInformationRequest;
import com.jamplestudio.coj.protocol.data.ChannelInformationResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChannelInformationExecutor implements HttpRequestExecutor<ChannelInformationRequest, ChannelInformationResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<ChannelInformationResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChannelInformationRequest request) {
        return Optional.empty();
    }

}
