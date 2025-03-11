package com.jamplestudio.coj.net.socket.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jamplestudio.coj.net.data.message.*;
import com.jamplestudio.coj.net.socket.SessionSocket;
import com.jamplestudio.coj.net.socket.SessionSocketHandler;
import com.jamplestudio.coj.utils.Constants;
import io.socket.client.IO;
import io.socket.client.Socket;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URISyntaxException;
import java.util.Arrays;

@Getter
public class SessionSocketImpl implements SessionSocket {

    private final @NotNull String url;
    private final @NotNull SessionSocketHandler handler;

    private final @NotNull Socket socket;
    private @Nullable String sessionKey;

    public SessionSocketImpl(@NotNull String url, @NotNull SessionSocketHandler handler) throws URISyntaxException {
        this.url = url;
        this.handler = handler;

        IO.Options options = new IO.Options();
        options.reconnection = false;
        options.forceNew = true;
        options.timeout = 3000;
        options.transports = new String[]{"websocket"};

        socket = IO.socket(url, options);

        // 연결
        socket.on("connect", objects -> {
            System.out.println("Connect Status: " + Arrays.toString(objects));
        });

        // 이벤트 바인드
        socket.on("SYSTEM", objects -> {
            if (objects.length == 0) {
                return;
            }

            String json = objects[0].toString();
            JsonElement element = JsonParser.parseString(json);
            JsonObject object = element.getAsJsonObject();

            if (!object.has("type")) {
                return;
            }

            switch (object.get("type").getAsString().toLowerCase()) {
                case "connected" -> {
                    ConnectedMessage message = Constants.GSON.fromJson(element, ConnectedMessage.class);
                    this.sessionKey = message.data().sessionKey();
                    System.out.println("Connected Session: " + message);
                    handler.onConnected(this, message);
                }
                case "subscribed" -> {
                    EventSubscribedMessage message = Constants.GSON.fromJson(element, EventSubscribedMessage.class);
                    System.out.println("Subscribe Event: " + message);
                    handler.onEventSubscribed(this, message);
                }
                case "unsubscribed" -> {
                    EventUnsubscribedMessage message = Constants.GSON.fromJson(element, EventUnsubscribedMessage.class);
                    System.out.println("Unsubscribed Event: " + message);
                    handler.onEventUnsubscribed(this, message);
                }
                case "revoked" -> {
                    EventRevokedMessage message = Constants.GSON.fromJson(element, EventRevokedMessage.class);
                    System.out.println("Revoked Event: " + message);
                    handler.onEventRevoked(this, message);
                }
            }
        });

        socket.on("CHAT", objects -> {
            if (objects.length == 0) {
                return;
            }

            String json = objects[0].toString();
            ChatMessage message = Constants.GSON.fromJson(json, ChatMessage.class);
            handler.onChatMessageReceived(this, message);
        });

        socket.on("DONATION", objects -> {
            if (objects.length == 0) {
                return;
            }

            String json = objects[0].toString();
            DonationMessage message = Constants.GSON.fromJson(json, DonationMessage.class);
            handler.onDonationMessageReceived(this, message);
        });
    }

    @Override
    public void connect() {
        socket.connect();
        System.out.println("Connect socket with url: " + url);
    }

    @Override
    public void disconnect() {
        socket.disconnect();
    }

    @Override
    public boolean isConnected() {
        return socket.connected();
    }

}
