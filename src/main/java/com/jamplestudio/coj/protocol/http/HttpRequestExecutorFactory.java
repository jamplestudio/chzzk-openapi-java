package com.jamplestudio.coj.protocol.http;

import com.jamplestudio.coj.protocol.http.executors.HttpRequestExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface HttpRequestExecutorFactory {

    @NotNull <Request, Response> Optional<HttpRequestExecutor<Request, Response>> create(@NotNull String type);

}
