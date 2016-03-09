package lokesh.shutterstock.network;

import java.util.Map;

import lokesh.shutterstock.model.AccessToken;
import lokesh.shutterstock.model.ShutterImages;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Lokesh on 04-03-2016.
 */
public interface ShutterService {
    @FormUrlEncoded
    @POST("oauth/access_token")
    Observable<AccessToken> getAccessToken(@FieldMap Map<String, String> model);

    @Headers("Accept: application/json")
    @GET("images/search")
    Observable<ShutterImages> getImages(@Header("Authorization") String authorization, @Query("page") int page, @Query("per_Page") int perPage);
}
