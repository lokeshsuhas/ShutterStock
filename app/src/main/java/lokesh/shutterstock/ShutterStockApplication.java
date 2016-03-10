package lokesh.shutterstock;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import lokesh.shutterstock.di.components.DaggerNetworkComponent;
import lokesh.shutterstock.di.components.DaggerShutterComponent;
import lokesh.shutterstock.di.components.NetworkComponent;
import lokesh.shutterstock.di.components.ShutterComponent;
import lokesh.shutterstock.di.modules.ApplicationModule;
import lokesh.shutterstock.di.modules.NetworkModule;
import lokesh.shutterstock.di.modules.ShutterModule;

/**
 * Created by Lokesh on 03-03-2016.
 */
public class ShutterStockApplication extends MultiDexApplication {
    private NetworkComponent mNetComponent;
    private ShutterComponent mShutterComponent;

    /***
     * Get ShutterStock Application instance
     *
     * @param context
     * @return ShutterStockApplication
     */
    public static ShutterStockApplication getInstance(Context context) {
        return (ShutterStockApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetworkComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(Constants.API_BASE_URL))
                .build();

        mShutterComponent = DaggerShutterComponent.builder()
                .networkComponent(mNetComponent)
                .shutterModule(new ShutterModule())
                .build();
    }

    /***
     * Get the shutter componenet instance
     *
     * @return ShutterComponent
     */
    public ShutterComponent getShutterComponent() {
        return mShutterComponent;
    }

    /***
     * Get the Network component instance
     *
     * @return NetworkComponent
     */
    public NetworkComponent getNetworkComponent() {
        return mNetComponent;
    }
}
