package lokesh.shutterstock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import lokesh.shutterstock.viewmodel.AuthViewModel;
/**
 * Created by Lokesh on 10-03-2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AuthViewModelTest {
    ShutterStockApplication application;
    AuthViewModel viewModel;

    @Before
    public void setUp() {
        application = (ShutterStockApplication) RuntimeEnvironment.application;
        viewModel =  new AuthViewModel(application);
    }

    @Test
    public void shouldGetDefault()
    {
        assertEquals(viewModel.showProgress.get(), true);
        assertEquals(viewModel.progressText.get(), application.getResources().getString(R.string.progress_text));
    }

    @Test
    public void shouldNotShowProgress()
    {
        viewModel.setShowProgress(false);
        assertEquals(viewModel.showProgress.get(), false);
    }

    @Test
    public void shouldGetProgressText()
    {
        viewModel.setProgressText("test");
        assertEquals(viewModel.progressText.get(), "test");
    }

}

