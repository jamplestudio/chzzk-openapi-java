package com.jamplestudio.coj.protocol.http.executor;

import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface HttpRequestExecutor<Request, Response, HttpClient> {

    @NotNull Optional<Response> execute(@NotNull ChzzkHttpClient<HttpClient> client, @NotNull Request request);

}
