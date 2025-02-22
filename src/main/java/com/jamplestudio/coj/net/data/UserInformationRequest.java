package com.jamplestudio.coj.net.data;

import org.jetbrains.annotations.NotNull;

public record UserInformationRequest(
        @NotNull String accessToken
) {
}
