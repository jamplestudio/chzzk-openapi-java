package com.jamplestudio.coj.chzzk;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;


@Getter
public class ChzzkChannelImpl implements ChzzkChannel {

    private final @NotNull String id;
    private final @NotNull String name;
    private final @NotNull String imageUrl;
    private final @Range(from = 0, to = Integer.MAX_VALUE) int followerCount;

    ChzzkChannelImpl(@NotNull String id, @NotNull String name, @NotNull String imageUrl, int followerCount) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.followerCount = followerCount;
    }

}
