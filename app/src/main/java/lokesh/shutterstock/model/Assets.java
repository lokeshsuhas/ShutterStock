
package lokesh.shutterstock.model;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assets implements Parcelable {

    @SerializedName("preview")
    @Expose
    private Preview preview;
    @SerializedName("small_thumb")
    @Expose
    private SmallThumb smallThumb;
    @SerializedName("large_thumb")
    @Expose
    private LargeThumb largeThumb;

    protected Assets(Parcel in) {
    }

    public static final Creator<Assets> CREATOR = new Creator<Assets>() {
        @Override
        public Assets createFromParcel(Parcel in) {
            return new Assets(in);
        }

        @Override
        public Assets[] newArray(int size) {
            return new Assets[size];
        }
    };

    /**
     * 
     * @return
     *     The preview
     */
    public Preview getPreview() {
        return preview;
    }

    /**
     * 
     * @param preview
     *     The preview
     */
    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    /**
     * 
     * @return
     *     The smallThumb
     */
    public SmallThumb getSmallThumb() {
        return smallThumb;
    }

    /**
     * 
     * @param smallThumb
     *     The small_thumb
     */
    public void setSmallThumb(SmallThumb smallThumb) {
        this.smallThumb = smallThumb;
    }

    /**
     * 
     * @return
     *     The largeThumb
     */
    public LargeThumb getLargeThumb() {
        return largeThumb;
    }

    /**
     * 
     * @param largeThumb
     *     The large_thumb
     */
    public void setLargeThumb(LargeThumb largeThumb) {
        this.largeThumb = largeThumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
