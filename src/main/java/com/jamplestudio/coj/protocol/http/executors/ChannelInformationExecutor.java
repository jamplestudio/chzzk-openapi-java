package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.ChannelInformationRequest;
import com.jamplestudio.coj.protocol.data.ChannelInformationResponse;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChannelInformationExecutor implements HttpRequestExecutor<ChannelInformationRequest, ChannelInformationResponse> {

    @Override
    public @NotNull Optional<ChannelInformationResponse> execute(
            @NotNull OkHttpClient client, @NotNull ChannelInformationRequest request) {
        return Optional.empty();
    }

}
