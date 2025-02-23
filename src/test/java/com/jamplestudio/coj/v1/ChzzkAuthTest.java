package com.jamplestudio.coj.v1;

import com.jamplestudio.coj.ChzzkTestBase;
import com.jamplestudio.coj.chzzk.Chzzk;
import com.jamplestudio.coj.chzzk.ChzzkAuthServer;
import com.jamplestudio.coj.chzzk.ChzzkEventHandler;
import com.jamplestudio.coj.chzzk.data.ChzzkChatMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;


public class ChzzkAuthTest extends ChzzkTestBase {

    @Test
    public void test() {
        // https://ezya.xyz/auth/login/chzzk?user=qwer4321
        ChzzkAuthServer server = ChzzkAuthServer.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .baseUri(baseUri)
                .addChzzkEventHandler(new ChzzkEventHandler() {
                    @Override
                    public void onGrantToken(@NotNull Chzzk chzzk) {
                        chzzk.refreshToken();
                    }

                    @Override
                    public void onRefreshToken(@NotNull Chzzk chzzk) {
                        chzzk.getSession().connect();
                    }

                    @Override
                    public void onChatMessage(@NotNull Chzzk chzzk, @NotNull ChzzkChatMessage message) {
                        System.out.println("Chat: " + message);
                    }

                    @Override
                    public void onUserRegistered(@NotNull Chzzk chzzk, @Nullable String user) {
                        System.out.println("User: " + user);
                    }
                })
                .build();

        server.start();

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
