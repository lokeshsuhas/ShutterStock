package lokesh.shutterstock.di.components;

import dagger.Component;
import lokesh.shutterstock.di.modules.ShutterModule;
import lokesh.shutterstock.di.scopes.UserScope;
import lokesh.shutterstock.viewmodel.MainViewModel;
import lokesh.shutterstock.viewmodel.ShutterImagesViewModel;

/**
 * Created by Lokesh on 04-03-2016.
 */
@UserScope // using the previously defined scope, note that @Singleton will not work
@Component(dependencies = NetworkComponent.class, modules = ShutterModule.class)
public interface ShutterComponent {
    void inject(MainViewModel model);

    void inject(ShutterImagesViewModel model);

}