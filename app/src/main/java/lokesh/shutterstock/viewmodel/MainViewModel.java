package lokesh.shutterstock.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import lokesh.shutterstock.Constants;
import lokesh.shutterstock.ShutterStockApplication;
import lokesh.shutterstock.listeners.BaseListener;
import lokesh.shutterstock.model.AccessToken;
import lokesh.shutterstock.network.ShutterService;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Lokesh on 03-03-2016.
 */
public class MainViewModel implements IViewModel {

    public ObservableField<String> accessToken;
    @Inject
    ShutterService shutterService;
    @Inject
    SharedPreferences preferences;
    private AccessToken token;
    private MainViewListener listener;

    public MainViewModel(Context context, MainViewListener listener) {
        this.listener = listener;
        ShutterStockApplication.getInstance(context).getShutterComponent().inject(this);
        accessToken = new ObservableField<>(null);
    }

    @Override
    public void onResume() {
        accessToken.set(preferences.getString(Constants.SP_ACCESS_TOKEN, null));
        if (accessToken.get() != null) {
            if (listener != null) {
                listener.onNext();
            }
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        listener = null;
    }


    // get the access token
    public void getAccessToken(String code) {
        Map<String, String> data = new HashMap<>();
        data.put("client_id", Constants.CLIENT_ID);
        data.put("client_secret", Constants.CLIENT_SECRET);
        data.put("code", code);
        data.put("grant_type", Constants.AC_GRANT_TYPE);
        Observable<AccessToken> call = shutterService.getAccessToken(data);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AccessToken>() {
                    @Override
                    public void onCompleted() {
                        preferences.edit().putString(Constants.SP_ACCESS_TOKEN, token.getTokenType() + " " + token.getAccessToken()).commit();
                        if (listener != null) {
                            listener.onNext();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onError("Access Token Error", "Unexpected error occured while getting the access token");
                        }
                    }

                    @Override
                    public void onNext(AccessToken accessToken) {
                        token = accessToken;
                    }
                });
    }

    public void getloginButton_clicked(View view) {
        if (listener != null) {
            listener.onLoginClicked();
        }
    }

    public interface MainViewListener extends BaseListener {
        void onLoginClicked();
    }
}
