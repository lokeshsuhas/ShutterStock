package lokesh.shutterstock.rxadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lokesh.shutterstock.Utils;
import rx.subjects.PublishSubject;

/**
 * Created by ahmedrizwan on 10/12/2015.
 */
public class RxImageAdapter<T> extends RecyclerView.Adapter<TypesViewHolder<T>> {

    private List<T> mDataSet;
    private List<ViewHolderInfo> mViewHolderInfoList;
    private OnGetItemViewType mViewTypeCallback;
    private PublishSubject<TypesViewHolder<T>> mPublishSubject;
    private int mGridItemWidth;
    private int mGridItemHeight;

    public RxImageAdapter(final List<T> dataSet, List<ViewHolderInfo> viewHolderInfoList, OnGetItemViewType viewTypeCallback) {
        mDataSet = dataSet;
        mViewHolderInfoList = viewHolderInfoList;
        mViewTypeCallback = viewTypeCallback;
        mPublishSubject = PublishSubject.create();
    }


    public rx.Observable<TypesViewHolder<T>> asObservable() {
        return mPublishSubject.asObservable();
    }

    @Override
    public TypesViewHolder<T> onCreateViewHolder(final ViewGroup parent,
                                                 final int viewType) {
        for (ViewHolderInfo viewHolderInfo : mViewHolderInfoList) {
            if (viewType == viewHolderInfo.getType()) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(viewHolderInfo.getLayoutRes(), parent, false);
                if (viewHolderInfo.getOrientationResize()) {
                    view.setLayoutParams(getGridItemLayoutParams(view));
                }
                return new TypesViewHolder<>(view);
            }
        }
        throw new RuntimeException("View Type in RxAdapter not found!");
    }

    @Override
    public void onBindViewHolder(final TypesViewHolder<T> holder, final int position) {
        holder.setItem(mDataSet.get(position));
        holder.setHeight(mGridItemHeight);
        holder.setWidth(mGridItemWidth);
        mPublishSubject.onNext(holder);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return mViewTypeCallback.getItemViewType(position);
    }

    public List<T> getDataSet() {
        return mDataSet;
    }

    public void updateDataSet(List<T> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    private ViewGroup.LayoutParams getGridItemLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int screenWidth = Utils.getScreenWidth(view.getContext());
        int numOfColumns;
        if (Utils.isInLandscapeMode(view.getContext())) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        mGridItemWidth = screenWidth / numOfColumns;
        mGridItemHeight = screenWidth / numOfColumns;

        layoutParams.width = mGridItemWidth;
        layoutParams.height = mGridItemHeight;

        return layoutParams;
    }

}