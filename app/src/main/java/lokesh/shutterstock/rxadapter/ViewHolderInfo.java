package lokesh.shutterstock.rxadapter;

import android.support.annotation.LayoutRes;

class ViewHolderInfo {
    @LayoutRes
    private int layoutRes;
    private int type;
    private boolean orientationResize;

    public ViewHolderInfo(@LayoutRes final int layoutRes, final int type, final boolean orientationResize) {
        this.layoutRes = layoutRes;
        this.type = type;
        this.orientationResize = orientationResize;
    }

    /***
     * Get the Layout resource
     *
     * @return
     */
    public int getLayoutRes() {
        return layoutRes;
    }

    /***
     * Get the type of the resource
     *
     * @return
     */
    public int getType() {
        return type;
    }

    /***
     * Get whether to restructure the layout on orientation change
     *
     * @return
     */
    public boolean getOrientationResize() {
        return orientationResize;
    }

}