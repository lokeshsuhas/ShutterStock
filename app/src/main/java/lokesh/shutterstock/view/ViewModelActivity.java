package lokesh.shutterstock.view;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Base Activity for all the activities
 * Created by Lokesh on 03-03-2016.
 */
abstract class ViewModelActivity extends AppCompatActivity {
    /***
     * Show the toast message
     *
     * @param message
     */
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
