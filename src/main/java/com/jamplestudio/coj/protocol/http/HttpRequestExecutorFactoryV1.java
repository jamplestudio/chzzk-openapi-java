package com.jamplestudio.coj.protocol.http;

import com.google.common.collect.Maps;
import com.jamplestudio.coj.protocol.http.executors.HttpRequestExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class HttpRequestExecutorFactoryV1 implements HttpRequestExecutorFactory {

    private final @NotNull Map<String, Class<? extends HttpRequestExecutor<?, ?>>> types = Maps.newHashMap();

    @Override
    public <Request, Response> @NotNull Optional<HttpRequestExecutor<Request, Response>> create(@NotNull String type) {
        HttpRequestExecutor<Request, Response> executor;
        try {
            executor = createInstance(type);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(executor);
    }

    @SuppressWarnings("unchecked")
    private <Request, Response> @Nullable HttpRequestExecutor<Request, Response> createInstance(String type)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends HttpRequestExecutor<?, ?>> clazz = types.get(type);
        if (clazz == null) {
            return null;
        }

        Constructor<?> constructor = clazz.getDeclaredConstructor();
        return (HttpRequestExecutor<Request, Response>) constructor.newInstance();
    }

}
