package lokesh.shutterstock.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import lokesh.shutterstock.Constants;
import lokesh.shutterstock.R;
import lokesh.shutterstock.databinding.ActivityMainBinding;
import lokesh.shutterstock.viewmodel.MainViewModel;

public class MainActivity extends ViewModelActivity implements MainViewModel.MainViewListener {

    MainViewModel mainViewModel;
    Dialog auth_dialog;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new MainViewModel(this, this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(mainViewModel);
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
        if (auth_dialog != null && auth_dialog.isShowing()) {
            auth_dialog.dismiss();
        }
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
        if (auth_dialog != null && auth_dialog.isShowing()) {
            auth_dialog.dismiss();
        }
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
        auth_dialog = new Dialog(MainActivity.this);
        auth_dialog.setContentView(R.layout.auth_dialog);
        final WebView web = (WebView) auth_dialog.findViewById(R.id.webv);
        final LinearLayout progressLayout = (LinearLayout) auth_dialog.findViewById(R.id.progressLayout);
        final TextView progressText = (TextView) auth_dialog.findViewById(R.id.progressText);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(Constants.API_BASE_URL + "/oauth/authorize" + "?redirect_uri=" + Constants.REDIRECT_URL + "&response_type=code&scope=user.view&state=12345&client_id=" + Constants.CLIENT_ID);
        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressLayout.setVisibility(View.GONE);
                web.setVisibility(View.VISIBLE);
                if (url.contains("?code=")) {
                    Uri uri = Uri.parse(url);
                    String authCode = uri.getQueryParameter("code");
                    web.setVisibility(View.GONE);
                    progressLayout.setVisibility(View.VISIBLE);
                    progressText.setText("Fetching Access Token, please wait");
                    mainViewModel.getAccessToken(authCode);
                } else if (url.contains("error=")) {
                    showToast("Unexpected error while authenticating, try after sometime!");
                    auth_dialog.dismiss();
                }
            }
        });

        auth_dialog.show();
        auth_dialog.setTitle("Authorize ShutterStock APP");
        auth_dialog.setCancelable(true);
    }
}
