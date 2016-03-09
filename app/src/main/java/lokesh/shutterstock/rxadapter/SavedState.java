package lokesh.shutterstock.rxadapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * State to maintain the scroll state of the recycler view
 * Created by Lokesh on 09-03-2016.
 */
public class SavedState extends android.view.View.BaseSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR
            = new Parcelable.Creator<SavedState>() {
        @Override
        public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
        }

        @Override
        public SavedState[] newArray(int size) {
            return new SavedState[size];
        }
    };
    public int mScrollPosition;

    public SavedState(Parcel in) {
        super(in);
        mScrollPosition = in.readInt();
    }

    public SavedState(Parcelable superState) {
        super(superState);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mScrollPosition);
    }
}
