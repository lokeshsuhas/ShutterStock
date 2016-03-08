package lokesh.shutterstock.rxadapter;

import android.support.annotation.LayoutRes;

public class ViewHolderInfo {
    @LayoutRes
    private int layoutRes;
    private int type;
    private boolean orientationResize;

    public ViewHolderInfo(@LayoutRes final int layoutRes, final int type,final boolean orientationResize) {
        this.layoutRes = layoutRes;
        this.type = type;
        this.orientationResize = orientationResize;
    }

    public int getLayoutRes() {
        return layoutRes;
    }

    public int getType() {
        return type;
    }

    public boolean getOrientationResize() { return orientationResize;}

}