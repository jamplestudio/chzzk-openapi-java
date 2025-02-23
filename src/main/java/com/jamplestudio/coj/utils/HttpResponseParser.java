package com.jamplestudio.coj.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class HttpResponseParser {

    public static <ResponseInst> Optional<ResponseInst> parse(@NotNull Response response, @NotNull TypeToken<ResponseInst> type) throws IOException {
        if (response.isSuccessful() && response.body() != null) {
            JsonObject responseJson = JsonParser.parseString(response.body().string()).getAsJsonObject();
            if (responseJson.has("content")) {
                ResponseInst responseInst = Constants.GSON.fromJson(responseJson.get("content"), type.getType());
                return Optional.of(responseInst);
            }
        }
        return Optional.empty();
    }

}
