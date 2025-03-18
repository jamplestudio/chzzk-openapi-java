package com.jamplestudio.coj.chzzk.impl;

import com.jamplestudio.coj.chzzk.Chzzk;
import com.jamplestudio.coj.chzzk.ChzzkEventHandlerHolder;
import com.jamplestudio.coj.chzzk.ChzzkSession;
import com.jamplestudio.coj.chzzk.data.ChzzkChatMessage;
import com.jamplestudio.coj.chzzk.data.ChzzkDonationMessage;
import com.jamplestudio.coj.chzzk.data.ChzzkSessionUrl;
import com.jamplestudio.coj.net.data.message.*;
import com.jamplestudio.coj.net.socket.SessionSocket;
import com.jamplestudio.coj.net.socket.SessionSocketHandler;
import com.jamplestudio.coj.net.socket.impl.SessionSocketImpl;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Getter
public class ChzzkSessionImpl implements ChzzkSession {

    private final @NotNull Chzzk chzzk;

    private SessionSocket socket;

    ChzzkSessionImpl(@NotNull Chzzk chzzk) {
        this.chzzk = chzzk;
    }

    @Override
    public @NotNull Optional<String> getUrl() {
        if (socket == null) {
            return Optional.empty();
        }
        return Optional.of(socket.getUrl());
    }

    @Override
    public @NotNull Optional<String> getSessionKey() {
        if (socket == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(socket.getSessionKey());
    }

    @Override
    public @NotNull CompletableFuture<Void> connectAsync() {
        return CompletableFuture.runAsync(this::connect);
    }

    @Override
    public void connect() {
        if (socket != null) {
            throw new IllegalStateException("Session is already connected");
        }

        try {
            Optional<ChzzkSessionUrl> opt = chzzk.getSessionUrl();
            if (opt.isEmpty()) {
                disconnect();
                throw new RuntimeException("No session url found. disconnecting.");
            }

            ChzzkSessionUrl url = opt.get();
            socket = new SessionSocketImpl(url.url(), new SessionSocketHandler() {

                @Override
                public void onConnected(@NotNull SessionSocket session, @NotNull ConnectedMessage message) {
                    String sessionKey = session.getSessionKey();
                    if (sessionKey == null || sessionKey.isEmpty()) {
                        System.out.println("Session key must not be null or empty. disconnecting.");
                        session.disconnect();
                        return;
                    }

                    chzzk.subscribeChat(sessionKey);
                    chzzk.subscribeDonation(sessionKey);
                }

                @Override
                public void onChatMessageReceived(@NotNull SessionSocket session, @NotNull ChatMessage message) {
                    if (chzzk instanceof ChzzkEventHandlerHolder holder) {
                        ChzzkChatMessage chzzkMessage = ChzzkChatMessage.of(message);
                        holder.getHandlers().forEach(handler -> handler.onChatMessage(chzzk, chzzkMessage));
                    }
                }

                @Override
                public void onDonationMessageReceived(@NotNull SessionSocket session, @NotNull DonationMessage message) {
                    if (chzzk instanceof ChzzkEventHandlerHolder holder) {
                        ChzzkDonationMessage chzzkMessage = ChzzkDonationMessage.of(message);
                        holder.getHandlers().forEach(handler -> handler.onDonationMessage(chzzk, chzzkMessage));
                    }
                }

            });
            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull CompletableFuture<Void> disconnectAsync() {
        return CompletableFuture.runAsync(this::disconnect);
    }

    @Override
    public void disconnect() {
        if (socket == null) {
            throw new IllegalStateException("Session is not connected");
        }

        try {
            String sessionKey = socket.getSessionKey();
            if (sessionKey != null) {
                chzzk.unsubscribeChat(sessionKey);
                chzzk.unsubscribeDonation(sessionKey);
            }
        } finally {
            socket.disconnect();
            socket = null;
        }
    }

    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

}
