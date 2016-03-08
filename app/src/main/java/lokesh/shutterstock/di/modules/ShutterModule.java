package lokesh.shutterstock.di.modules;

import dagger.Module;
import dagger.Provides;
import lokesh.shutterstock.di.scopes.UserScope;
import lokesh.shutterstock.network.ShutterService;
import retrofit2.Retrofit;

/**
 * Created by Lokesh on 04-03-2016.
 */
@Module
public class ShutterModule {

    @Provides
    @UserScope // needs to be consistent with the component scope
    public ShutterService providesShutterService(Retrofit retrofit) {
        return retrofit.create(ShutterService.class);
    }
}
