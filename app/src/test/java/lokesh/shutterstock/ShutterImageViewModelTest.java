package lokesh.shutterstock;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Collections;

import lokesh.shutterstock.model.Item;
import lokesh.shutterstock.model.ShutterImages;
import lokesh.shutterstock.network.ShutterService;
import lokesh.shutterstock.viewmodel.ShutterImagesViewModel;
import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Lokesh on 09-03-2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ShutterImageViewModelTest {
    ShutterService shutterService;
    ShutterStockApplication application;
    ShutterImagesViewModel viewModel;
    ShutterImagesViewModel.ShutterImageListener listener;
    SharedPreferences preferences;

    @Before
    public void setUp() {
        shutterService = mock(ShutterService.class);
        listener = mock(ShutterImagesViewModel.ShutterImageListener.class);
        application = (ShutterStockApplication) RuntimeEnvironment.application;
        viewModel = new ShutterImagesViewModel(application, listener);
        preferences = PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Test
    public void shouldReachEnd()
    {
        when(shutterService.getImages(1, 10)).thenReturn(Observable.<ShutterImages>empty());
        viewModel.loadShutterImages();
        verify(listener).onEndReached();
    }
}
