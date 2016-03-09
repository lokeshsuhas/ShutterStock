package lokesh.shutterstock;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.support.annotation.NonNull;

/**
 * Created by Lokesh on 08-03-2016.
 */
public class Utils {
    /***
     * Get the device screen width
     *
     * @param context
     * @return int
     */
    public static int getScreenWidth(@NonNull Context context) {
        Point size = new Point();
        ((Activity) context).getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    /***
     * Get the device screen height
     *
     * @param context
     * @return int
     */
    public static int getScreenHeight(@NonNull Context context) {
        Point size = new Point();
        ((Activity) context).getWindowManager().getDefaultDisplay().getSize(size);
        return size.y;
    }

    /***
     * Check whether the orientation is in landscape or portrait
     *
     * @param context
     * @return boolean
     */
    public static boolean isInLandscapeMode(@NonNull Context context) {
        boolean isLandscape = false;
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandscape = true;
        }
        return isLandscape;
    }
}
