package lokesh.shutterstock.di.components;

import android.content.SharedPreferences;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;
import lokesh.shutterstock.di.modules.ApplicationModule;
import lokesh.shutterstock.di.modules.NetworkModule;
import retrofit2.Retrofit;

/**
 * Created by Lokesh on 03-03-2016.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {
    Retrofit retrofit();

    SharedPreferences sharedPreferences();

    Picasso picasso();
}
