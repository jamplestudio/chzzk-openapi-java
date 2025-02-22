package com.jamplestudio.coj;

import com.google.common.collect.Sets;
import com.jamplestudio.coj.chzzk.Chzzk;
import com.jamplestudio.coj.chzzk.ChzzkAuthServer;
import com.jamplestudio.coj.chzzk.ChzzkEventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Main {

    private static final Set<Chzzk> chzzks = Sets.newHashSet();

    public static void main(String[] args) throws InterruptedException {
        // https://ezya.xyz/auth/login/chzzk

        ChzzkAuthServer server = ChzzkAuthServer.v1()
                .clientId("e143d821-f006-4e2b-8a25-fbe1643e3aa8")
                .clientSecret("jlvmcaBHfFvJvVm32yXnup6c4RjFwNPqWwWakwOoWQM")
                .baseUri("https://ezya.xyz")
                .addChzzkEventHandler(new ChzzkEventHandler() {
                    @Override
                    public void onGrantToken(@NotNull Chzzk chzzk) {
                        System.out.println("Token: " + chzzk.getToken());
                        chzzks.add(chzzk);
                    }

                    @Override
                    public void onRefreshToken(@NotNull Chzzk chzzk) {
                        System.out.println("Refresh Token: " + chzzk.getToken());
                    }

                    @Override
                    public void onRevokeToken(@NotNull Chzzk chzzk) {
                        System.out.println("Revoke Token");
                    }

                })
                .build();

        server.start();

//        Chzzk chzzk = Chzzk.v1()
//                .clientId("e143d821-f006-4e2b-8a25-fbe1643e3aa8")
//                .clientSecret("jlvmcaBHfFvJvVm32yXnup6c4RjFwNPqWwWakwOoWQM")
//                .token(new ChzzkToken(
//                        "In2lvhDiAPm8SwCGnY5wjuOZHlxCyIpubNbwz0-nBSUyUzBU-GZP0mYtySzBD97zkCUfHHa87Ezaznqw6fY5oQ",
//                        "igLSvru4XiSsUTYDWFRggSh_lu4SMsvpnj5BLoc6NBVGukHUaXu41J3F9UR2iBcZ1SV9t3uno8c3MG0r8eWixw"
//                ))
//                .addEventHandler(new ChzzkEventHandler() {
//                    @Override
//                    public void onGrantToken(@NotNull Chzzk chzzk) {
//                        System.out.println("Token: " + chzzk.getToken());
//                        chzzks.add(chzzk);
//                    }
//
//                    @Override
//                    public void onRefreshToken(@NotNull Chzzk chzzk) {
//                        System.out.println("Refresh Token: " + chzzk.getToken());
//                    }
//
//                    @Override
//                    public void onRevokeToken(@NotNull Chzzk chzzk) {
//                        System.out.println("Revoke Token");
//                    }
//
//                })
//                .build();
    }

}
