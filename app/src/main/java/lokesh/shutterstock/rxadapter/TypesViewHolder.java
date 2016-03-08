package lokesh.shutterstock.rxadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ahmedrizwan on 13/12/2015.
 */
public class TypesViewHolder<T> extends RecyclerView.ViewHolder {
    private ViewDataBinding mViewDataBinding;

    public T getItem() {
        return mItem;
    }

    public ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    private T mItem;
    private int mWidth;
    private int mHeight;

    protected void setItem(final T item) {
        mItem = item;
    }

    public int getWidth()
    {
        return mWidth;
    }

    public int getHeight()
    {
        return mHeight;
    }

    protected void setWidth(int width)
    {
        this.mWidth = width;
    }

    protected void setHeight(int height)
    {
        this.mHeight = height;
    }

    public TypesViewHolder(final View itemView) {
        super(itemView);
        mViewDataBinding = DataBindingUtil.bind(itemView);
    }
}
