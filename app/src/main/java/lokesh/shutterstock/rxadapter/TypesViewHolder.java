package lokesh.shutterstock.rxadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/***
 * Created by Lokesh on 04-03-2016.
 *
 * @param <T>
 */
public class TypesViewHolder<T> extends RecyclerView.ViewHolder {
    private ViewDataBinding mViewDataBinding;

    private T mItem;
    private int mWidth;
    private int mHeight;

    /***
     * Constructor
     *
     * @param itemView
     */
    public TypesViewHolder(final View itemView) {
        super(itemView);
        mViewDataBinding = DataBindingUtil.bind(itemView);
    }

    /***
     * Get the view binding
     *
     * @return
     */
    public ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    /***
     * Get the item
     *
     * @return
     */
    public T getItem() {
        return mItem;
    }

    /***
     * Set the item
     *
     * @param item
     */
    protected void setItem(final T item) {
        mItem = item;
    }

    /***
     * Get the width of the grid
     *
     * @return
     */
    public int getWidth() {
        return mWidth;
    }

    /***
     * Set the width for the grid
     *
     * @param width
     */
    protected void setWidth(int width) {
        this.mWidth = width;
    }

    /***
     * Get the height of the grid
     *
     * @return
     */
    public int getHeight() {
        return mHeight;
    }

    /***
     * Set the height for the grid
     *
     * @param height
     */
    protected void setHeight(int height) {
        this.mHeight = height;
    }
}
