package lokesh.shutterstock;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import lokesh.shutterstock.model.Footer;
import lokesh.shutterstock.model.Item;
import lokesh.shutterstock.viewmodel.MainViewModel;
import lokesh.shutterstock.viewmodel.ShutterImagesViewModel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by Lokesh on 10-03-2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ShutterStockViewModelTest {


    ShutterStockApplication application;
    ShutterImagesViewModel viewModel;
    ShutterImagesViewModel.ShutterImageListener listener;
    SharedPreferences preferences;

    @Before
    public void setUp() {
        application = (ShutterStockApplication) RuntimeEnvironment.application;
        listener = mock(ShutterImagesViewModel.ShutterImageListener.class);
        viewModel = new ShutterImagesViewModel(application,listener);
    }

    @Test
    public void shouldGetDefault()
    {
        assertEquals(viewModel.showInfo.get(),false);
        assertEquals(viewModel.showFirstBatchProgress.get(),true);
        assertEquals(viewModel.showRecyclerView.get(),false);
        assertEquals(viewModel.infoMessage.get(),"");
    }

    @Test
    public void shouldGetMore()
    {
        List<Item> data = mock(ArrayList.class);
        data.add(new Footer());
        viewModel.setHasMore(true);
        assertEquals(viewModel.isHasMore(),true);
    }

    @Test
    public void shouldNotGetMore()
    {
        viewModel.setHasMore(false);
        assertEquals(viewModel.isHasMore(),false);
    }
}
