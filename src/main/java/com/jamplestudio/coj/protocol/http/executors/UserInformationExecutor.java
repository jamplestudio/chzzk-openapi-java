package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.UserInformationResponse;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class UserInformationExecutor implements HttpRequestExecutor<Void, UserInformationResponse> {

    @Override
    public @NotNull Optional<UserInformationResponse> execute(@NotNull OkHttpClient client, @NotNull Void unused) {
        return Optional.empty();
    }

}
