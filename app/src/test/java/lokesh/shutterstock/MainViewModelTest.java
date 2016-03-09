package lokesh.shutterstock;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.List;

import lokesh.shutterstock.listeners.BaseListener;
import lokesh.shutterstock.model.AccessToken;
import lokesh.shutterstock.network.ShutterService;
import lokesh.shutterstock.utils.MockModelFabric;
import lokesh.shutterstock.viewmodel.MainViewModel;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.schedulers.Schedulers;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainViewModelTest {

    ShutterService shutterService;
    ShutterStockApplication application;
    MainViewModel mainViewModel;
    MainViewModel.MainViewListener mainViewListener;
    SharedPreferences preferences;

    @Before
    public void setUp() {
        shutterService = mock(ShutterService.class);
        mainViewListener = mock(MainViewModel.MainViewListener.class);
        application = (ShutterStockApplication) RuntimeEnvironment.application;
        mainViewModel = new MainViewModel(application, mainViewListener);
        preferences = PreferenceManager.getDefaultSharedPreferences(application);
    }


    @Test
    public void shouldFailGetToken() {
        HttpException mockHttpException = new HttpException(Response.error(404, null));
        when(shutterService.getAccessToken(MockModelFabric.newAuthModel("test"))).thenReturn(Observable.<AccessToken>error(mockHttpException));
        verify(mainViewListener).onError("Access Token Error", "Unexpected error occured while getting the access token");
        assertEquals(mainViewModel.token.get(),null);
    }

    @Test
    public void shouldGetToken(){
        AccessToken token= MockModelFabric.newAccessToken();
        String expectedToken = token.getTokenType()+" "+token.getAccessToken();
        when(shutterService.getAccessToken(MockModelFabric.newAuthModel("test"))).thenReturn(Observable.just(token));
        assertEquals(mainViewModel.token.get(), preferences.getString(Constants.SP_ACCESS_TOKEN, null));
        assertEquals(preferences.getString(Constants.SP_ACCESS_TOKEN,null),expectedToken);
    }

}
