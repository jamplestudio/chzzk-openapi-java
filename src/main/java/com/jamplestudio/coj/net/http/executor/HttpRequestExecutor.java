package com.jamplestudio.coj.net.http.executor;

import com.jamplestudio.coj.net.http.client.ChzzkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface HttpRequestExecutor<Request, Response, HttpClient> {

    @NotNull Optional<Response> execute(@NotNull ChzzkHttpClient<HttpClient> client, @NotNull Request request);

}
