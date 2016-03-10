package lokesh.shutterstock;

import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import lokesh.shutterstock.viewmodel.AuthViewModel;
import lokesh.shutterstock.viewmodel.MainViewModel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Lokesh on 10-03-2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainViewModelTest {

    ShutterStockApplication application;
    MainViewModel viewModel;
    MainViewModel.MainViewListener listener;
    SharedPreferences preferences;

    @Before
    public void setUp() {
        application = (ShutterStockApplication) RuntimeEnvironment.application;
        listener = mock(MainViewModel.MainViewListener.class);
        viewModel = new MainViewModel(application,listener);
        preferences = PreferenceManager.getDefaultSharedPreferences(application);
    }


    @Test
    public void shouldCallResumeWithNext()
    {
        preferences.edit().putString(Constants.SP_ACCESS_TOKEN,"test").apply();
        viewModel.onResume();
        verify(listener).onNext();
    }

    @Test
    public void shouldCallResumeWithoutNext()
    {
        preferences.edit().putString(Constants.SP_ACCESS_TOKEN,null).apply();
        viewModel.onResume();
        verify(listener,never()).onNext();
    }

    @Test
    public void shouldCallDestroy()
    {
        viewModel.onDestroy();
        assertEquals(listener, null);
    }

    @Test
    public void shouldCallLogin()
    {
        viewModel.getloginButton_clicked(null);
        verify(listener).onLoginClicked();
    }

    @Test
    public void shouldNotCallLogin()
    {
        listener = null;
        viewModel.getloginButton_clicked(null);
        verify(listener,never()).onLoginClicked();
    }
}
