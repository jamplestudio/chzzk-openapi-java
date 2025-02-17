package com.jamplestudio.coj.protocol.http.factories;

import com.google.common.collect.Maps;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutorFactory;
import com.jamplestudio.coj.protocol.http.executors.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class HttpRequestExecutorFactoryV1 implements HttpRequestExecutorFactory {

    private final @NotNull Map<String, Class<? extends HttpRequestExecutor<?, ?>>> types = Maps.newHashMap();

    {
        types.put("access_token_grant", AccessTokenGrantExecutor.class);
        types.put("access_token_refresh", AccessTokenRefreshExecutor.class);
        types.put("access_token_revoke", AccessTokenRevokeExecutor.class);
        types.put("authorization_code", AuthorizationCodeExecutor.class);
        types.put("category_search", CategorySearchExecutor.class);
        types.put("channel_information", ChannelInformationExecutor.class);
        types.put("chat_announcement_set", ChatAnnouncementSetExecutor.class);
        types.put("chat_message_send", ChatMessageSendExecutor.class);
        types.put("chat_settings_change", ChatSettingsChangeExecutor.class);
        types.put("chat_settings", ChatSettingsExecutor.class);
        types.put("live_search", LiveSearchExecutor.class);
        types.put("live_settings_change", LiveSettingsChangeExecutor.class);
        types.put("live_settings", LiveSettingsExecutor.class);
        types.put("live_stream_key", LiveStreamKeyExecutor.class);
        types.put("user_information", UserInformationExecutor.class);
    }

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
