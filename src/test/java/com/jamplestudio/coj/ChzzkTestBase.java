package com.jamplestudio.coj;

import io.github.cdimascio.dotenv.Dotenv;

public abstract class ChzzkTestBase {

    protected String clientId;
    protected String clientSecret;
    protected String baseUri;

    protected ChzzkTestBase() {
        Dotenv dotenv = Dotenv.load();
        clientId = dotenv.get("CLIENT_ID");
        clientSecret = dotenv.get("CLIENT_SECRET");
        baseUri = dotenv.get("BASE_URI");
    }

}
