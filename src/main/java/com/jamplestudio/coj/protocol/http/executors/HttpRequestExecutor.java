package com.jamplestudio.coj.protocol.http.executors;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface HttpRequestExecutor<Request, Response> {

    @NotNull Optional<Response> execute(@NotNull OkHttpClient client, @NotNull Request request);

}
