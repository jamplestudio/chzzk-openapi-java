package com.jamplestudio.coj.chzzk;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ChzzkUserImpl implements ChzzkUser {

    private final @NotNull String id;
    private final @NotNull String name;

    ChzzkUserImpl(@NotNull String id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

}
