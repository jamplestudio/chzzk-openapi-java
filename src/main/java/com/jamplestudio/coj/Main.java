package com.jamplestudio.coj;

import com.jamplestudio.coj.chzzk.Chzzk;

public class Main {

    public static void main(String[] args) {
        // https://ezya.xyz/auth/login/chzzk

        Chzzk chzzk = Chzzk.builder()
                .clientId("e143d821-f006-4e2b-8a25-fbe1643e3aa8")
                .clientSecret("jlvmcaBHfFvJvVm32yXnup6c4RjFwNPqWwWakwOoWQM")
                .redirectUri("https://ezya.xyz/auth/callback")
                .build();

        chzzk.getServer().start();

//        Optional<HttpRequestExecutor<AccessTokenRefreshRequest, AccessTokenRefreshResponse, OkHttpClient>> req =
//                chzzk.getHttpRequestExecutorFactory().create("access_token_refresh");
//
//        req.ifPresent(request -> {
//            Optional<AccessTokenRefreshResponse> resp = request.execute(ChzzkHttpClient.okhttp(), new AccessTokenRefreshRequest(
//                    "gPUr_Zna9F5A6LAwywsm72b1kpkMzXnZmPpCWp7ZpIG7mXICvsUuLt49Bl79ZsRx7QY-d-ba_D49EBxcQxqC6w",
//                    "e143d821-f006-4e2b-8a25-fbe1643e3aa8",
//                    "k5acL5-C2STZ4LLZcVaBl1t-DhJq0Gm9ZyfgYBxgsOQ"
//            ));
//
//            resp.ifPresent(response -> {
//                System.out.println("Response: " + response);
//            });
//        });
    }

}
