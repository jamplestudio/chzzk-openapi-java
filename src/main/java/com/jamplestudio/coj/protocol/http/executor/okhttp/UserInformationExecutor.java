package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.UserInformationResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class UserInformationExecutor implements HttpRequestExecutor<Void, UserInformationResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<UserInformationResponse> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull Void unused) {
        return Optional.empty();
    }

}
