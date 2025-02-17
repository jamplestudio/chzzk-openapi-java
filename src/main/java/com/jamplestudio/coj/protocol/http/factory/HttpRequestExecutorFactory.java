package com.jamplestudio.coj.protocol.http.factory;

import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface HttpRequestExecutorFactory {

    @NotNull <Request, Response, HttpClient> Optional<HttpRequestExecutor<Request, Response, HttpClient>> create(@NotNull String type);

}
