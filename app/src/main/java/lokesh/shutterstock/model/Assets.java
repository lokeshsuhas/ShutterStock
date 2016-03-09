package lokesh.shutterstock.model;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assets {

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


    /**
     * @return The preview
     */
    public Preview getPreview() {
        return preview;
    }

    /**
     * @param preview The preview
     */
    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    /**
     * @return The smallThumb
     */
    public SmallThumb getSmallThumb() {
        return smallThumb;
    }

    /**
     * @param smallThumb The small_thumb
     */
    public void setSmallThumb(SmallThumb smallThumb) {
        this.smallThumb = smallThumb;
    }

    /**
     * @return The largeThumb
     */
    public LargeThumb getLargeThumb() {
        return largeThumb;
    }

    /**
     * @param largeThumb The large_thumb
     */
    public void setLargeThumb(LargeThumb largeThumb) {
        this.largeThumb = largeThumb;
    }

}
