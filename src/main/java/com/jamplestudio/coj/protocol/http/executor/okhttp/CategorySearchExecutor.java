package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.CategorySearchRequest;
import com.jamplestudio.coj.protocol.data.CategorySearchResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CategorySearchExecutor implements HttpRequestExecutor<CategorySearchRequest, CategorySearchResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<CategorySearchResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull CategorySearchRequest categorySearchRequest) {
        return Optional.empty();
    }

}
