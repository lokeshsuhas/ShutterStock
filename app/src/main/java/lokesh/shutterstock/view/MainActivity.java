package lokesh.shutterstock.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import lokesh.shutterstock.Constants;
import lokesh.shutterstock.R;
import lokesh.shutterstock.databinding.ActivityMainBinding;
import lokesh.shutterstock.databinding.AuthDialogBinding;
import lokesh.shutterstock.viewmodel.AuthViewModel;
import lokesh.shutterstock.viewmodel.MainViewModel;

public class MainActivity extends ViewModelActivity implements MainViewModel.MainViewListener {

    private MainViewModel mainViewModel;
    private ActivityMainBinding activityMainBinding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        mainViewModel = new MainViewModel(this, this);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewModel(mainViewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainViewModel.onPause();
    }

    @Override
    public void onError(String header, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(header);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onNext() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                    Intent intent = new Intent(MainActivity.this, ShutterImageActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }


    @Override
    public void onLoginClicked() {
        /*auth_dialog = new Dialog(MainActivity.this);
        auth_dialog.setContentView(R.layout.auth_dialog);*/
        final AuthViewModel authViewModel = new AuthViewModel(context);
        AuthDialogBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.auth_dialog);
        binding.setViewModel(authViewModel);
        binding.webv.getSettings().setJavaScriptEnabled(true);
        binding.webv.loadUrl(Constants.API_BASE_URL + "/oauth/authorize" + "?redirect_uri=" + Constants.REDIRECT_URL + "&response_type=code&scope=user.view&state=12345&client_id=" + Constants.CLIENT_ID);
        binding.webv.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                authViewModel.setShowProgress(false);
                if (url.contains("?code=")) {
                    Uri uri = Uri.parse(url);
                    String authCode = uri.getQueryParameter("code");
                    authViewModel.setShowProgress(true);
                    authViewModel.setProgressText(context.getString(R.string.progress_auth_token));
                    mainViewModel.getAccessToken(authCode);
                } else if (url.contains("error=")) {
                    activityMainBinding= DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
                    activityMainBinding.setViewModel(mainViewModel);
                    showToast(context.getResources().getString(R.string.error_auth_failed));
                }
            }
        });

        /*auth_dialog.show();
        auth_dialog.setTitle(R.string.auth_title);
        auth_dialog.setCancelable(true);*/
    }
}
