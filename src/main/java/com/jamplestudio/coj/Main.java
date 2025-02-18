package com.jamplestudio.coj;

import com.jamplestudio.coj.chzzk.Chzzk;

public class Main {

    public static void main(String[] args) {
        // https://ezya.xyz/auth/login/chzzk

        Chzzk chzzk = Chzzk.builder()
                .clientId("e143d821-f006-4e2b-8a25-fbe1643e3aa8")
                .clientSecret("k5acL5-C2STZ4LLZcVaBl1t-DhJq0Gm9ZyfgYBxgsOQ")
                .redirectUri("https://ezya.xyz/auth/callback")
                .build();

        chzzk.getServer().start();
    }

}
