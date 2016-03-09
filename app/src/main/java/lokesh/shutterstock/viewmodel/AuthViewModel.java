package lokesh.shutterstock.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import lokesh.shutterstock.R;

/**
 * Created by Lokesh on 09-03-2016.
 */
public class AuthViewModel implements IViewModel {
    public ObservableBoolean showProgress;
    public ObservableField<String> progressText;

    public AuthViewModel(Context context) {
        showProgress = new ObservableBoolean(true);
        progressText = new ObservableField<>(context.getResources().getString(R.string.progress_auth_webview));
    }

    /***
     * Set to show the progress
     *
     * @param value
     */
    public void setShowProgress(boolean value) {
        showProgress.set(value);
    }

    /***
     * Set to show the progress text
     *
     * @param value
     */
    public void setProgressText(String value) {
        progressText.set(value);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }
}
