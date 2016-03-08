package lokesh.shutterstock.view;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Lokesh on 03-03-2016.
 */
abstract class ViewModelActivity extends AppCompatActivity {

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
