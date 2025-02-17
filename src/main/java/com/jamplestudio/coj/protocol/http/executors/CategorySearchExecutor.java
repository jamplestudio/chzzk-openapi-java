package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.CategorySearchRequest;
import com.jamplestudio.coj.protocol.data.CategorySearchResponse;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CategorySearchExecutor implements HttpRequestExecutor<CategorySearchRequest, CategorySearchResponse> {

    @Override
    public @NotNull Optional<CategorySearchResponse> execute(
            @NotNull OkHttpClient client, @NotNull CategorySearchRequest categorySearchRequest) {
        return Optional.empty();
    }

}
