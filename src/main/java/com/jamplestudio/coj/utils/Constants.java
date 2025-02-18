package com.jamplestudio.coj.utils;

import com.google.gson.Gson;
import okhttp3.MediaType;
import org.jetbrains.annotations.NotNull;

public class Constants {

    public static final @NotNull String OPENAPI_URL = "https://openapi.chzzk.naver.com";

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static final @NotNull Gson GSON = new Gson();

}
