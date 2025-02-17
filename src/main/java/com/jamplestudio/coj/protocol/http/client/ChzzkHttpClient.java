package com.jamplestudio.coj.protocol.http.client;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

public interface ChzzkHttpClient<Client> {

    static @NotNull ChzzkHttpClient<OkHttpClient> okhttp() {
        return new OkHttpChzzkHttpClient();
    }

    @NotNull Client getNativeHttpClient();

}
