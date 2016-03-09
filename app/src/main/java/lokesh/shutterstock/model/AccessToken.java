package lokesh.shutterstock.model;

/**
 * Created by Lokesh on 03-03-2016.
 */
public class AccessToken {
    public String access_token;
    public String token_type;


    public String getAccessToken()
    {
        return this.access_token;
    }

    public String getTokenType()
    {
        return this.token_type;
    }
}
