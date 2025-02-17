package com.jamplestudio.coj.protocol.http;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface HttpRequestExecutorFactory {

    @NotNull <Request, Response> Optional<HttpRequestExecutor<Request, Response>> create(@NotNull String type);

}
