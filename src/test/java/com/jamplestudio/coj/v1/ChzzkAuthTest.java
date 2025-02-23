package com.jamplestudio.coj.v1;

import com.jamplestudio.coj.ChzzkTestBase;
import com.jamplestudio.coj.chzzk.Chzzk;
import com.jamplestudio.coj.chzzk.ChzzkAuthServer;
import com.jamplestudio.coj.chzzk.ChzzkEventHandler;
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
                        System.out.println("Token: " + chzzk.getToken());

                        chzzk.getSessionUrl().ifPresentOrElse(url -> {
                            System.out.println("SessionUrl: " + url);
                        }, () -> {
                            System.out.println("SessionUrl empty");
                        });
                    }

                    @Override
                    public void onRefreshToken(@NotNull Chzzk chzzk) {
                        ChzzkEventHandler.super.onRefreshToken(chzzk);
                    }

                    @Override
                    public void onRevokeToken(@NotNull Chzzk chzzk) {
                        ChzzkEventHandler.super.onRevokeToken(chzzk);
                    }
                })
                .build();

        server.start();

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
