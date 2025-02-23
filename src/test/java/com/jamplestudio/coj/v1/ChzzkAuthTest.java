package com.jamplestudio.coj.v1;

import com.jamplestudio.coj.ChzzkTestBase;
import com.jamplestudio.coj.chzzk.Chzzk;
import com.jamplestudio.coj.chzzk.ChzzkAuthServer;
import com.jamplestudio.coj.chzzk.ChzzkEventHandler;
import com.jamplestudio.coj.chzzk.data.ChzzkChatMessage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;


public class ChzzkAuthTest extends ChzzkTestBase {

    @Test
    public void test() {
        // https://ezya.xyz/auth/login/chzzk
        ChzzkAuthServer server = ChzzkAuthServer.v1()
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
