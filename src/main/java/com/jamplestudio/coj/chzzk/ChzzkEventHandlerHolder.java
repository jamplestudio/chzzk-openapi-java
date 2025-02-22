package com.jamplestudio.coj.chzzk;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ChzzkEventHandlerHolder {

    @NotNull List<ChzzkEventHandler> getHandlers();

}
