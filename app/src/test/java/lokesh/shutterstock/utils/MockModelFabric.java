package lokesh.shutterstock.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lokesh.shutterstock.Constants;
import lokesh.shutterstock.model.AccessToken;

public class MockModelFabric {

    public static Map<String,String> newAuthModel(String code) {
        Map<String, String> data = new HashMap<>();
        data.put("client_id", Constants.CLIENT_ID);
        data.put("client_secret", Constants.CLIENT_SECRET);
        data.put("code", code);
        data.put("grant_type", Constants.AC_GRANT_TYPE);
        return data;
    }

    public static AccessToken newAccessToken()
    {
        AccessToken token =new AccessToken();
        token.access_token ="test";
        token.token_type="bearer";
        return token;
    }

}
