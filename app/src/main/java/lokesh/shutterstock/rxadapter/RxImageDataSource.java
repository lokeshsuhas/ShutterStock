package lokesh.shutterstock.rxadapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lokesh.shutterstock.R;
import lokesh.shutterstock.ShutterStockApplication;
import lokesh.shutterstock.Utils;
import lokesh.shutterstock.databinding.RecyclerItemBinding;
import lokesh.shutterstock.model.Datum;
import lokesh.shutterstock.model.Footer;
import lokesh.shutterstock.model.Item;
import rx.functions.Action1;

/***
 * Created by Lokesh on 04-03-2016.
 */
public class RxImageDataSource {

    private final int TYPE_FOOTER = 0;
    private final int TYPE_ITEM = 1;

    private List<Item> mDataSet;
    private RxImageAdapter<Item> mRxAdapterForTypes;
    private Picasso picasso;
    private Context context;
    private RecyclerView recyclerView;

    /***
     * Constructor
     *
     * @param context
     * @param dataSet
     */
    public RxImageDataSource(Context context, List<Item> dataSet) {
        this.mDataSet = dataSet;
        this.context = context;
        picasso = ShutterStockApplication.getInstance(context).getNetworkComponent().picasso();
    }

    /***
     * Call this if you need access to the Adapter!
     * Warning: might return null!
     *
     * @return RxAdapter Instance
     */
    @Nullable
    public RxImageAdapter<Item> getRxAdapterForTypes() {
        return mRxAdapterForTypes;
    }

    /***
     * Call this when you want to bind with multiple Item-Types
     *
     * @param recyclerView RecyclerView instance
     * @return Observable for binding viewHolder
     */
    public RxImageDataSource bindRecyclerView(
            @NonNull final RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        bindRVLayoutManager();
        bindRVAdapters();
        return this;
    }

    /***
     * Set the Recyclerview Scroll listener
     *
     * @param listener
     */
    public void setScrollListener(RecyclerView.OnScrollListener listener) {
        if (listener != null) {
            this.recyclerView.addOnScrollListener(listener);
        }
    }

    /***
     * Check whether the recycler scroll reaches the end of the list
     *
     * @return
     */
    public boolean isScrolledEnd() {
        if (recyclerView != null) {
            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            if (layoutManager != null) {
                return layoutManager.findLastCompletelyVisibleItemPosition() >= layoutManager.getChildCount() - 3;
            }
        }
        return false;
    }

    /***
     * Bind the Recyclerview Adapter
     */
    private void bindRVAdapters() {
        //initialize the viewholders
        List<ViewHolderInfo> viewHolderInfoList = new ArrayList<>();
        viewHolderInfoList.add(new ViewHolderInfo(R.layout.recycler_item, TYPE_ITEM, true)); //TYPE_ITEM = 1
        viewHolderInfoList.add(new ViewHolderInfo(R.layout.recycler_footer, TYPE_FOOTER, false)); //TYPE_FOOTER = 0
        //initialize
        mRxAdapterForTypes = new RxImageAdapter<>(mDataSet, viewHolderInfoList, new OnGetItemViewType() {
            @Override
            protected int getItemViewType(int position) {
                return (mDataSet.get(position) instanceof Footer) ? TYPE_FOOTER : TYPE_ITEM;
            }
        });
        //bind the adapters
        recyclerView.setAdapter(mRxAdapterForTypes);
        mRxAdapterForTypes.asObservable().subscribe(new Action1<TypesViewHolder<Item>>() {
            @Override
            public void call(TypesViewHolder<Item> viewHolder) {
                final ViewDataBinding b = viewHolder.getViewDataBinding();
                if (b != null && b instanceof RecyclerItemBinding) {
                    final RecyclerItemBinding iB = (RecyclerItemBinding) b;
                    Datum item = (Datum) viewHolder.getItem();
                    if (item != null) {
                        picasso.load(Uri.parse(item.getAssets().getPreview().getUrl()))
                                .resize(viewHolder.getWidth(), viewHolder.getHeight())
                                .centerCrop()
                                .noFade()
                                .placeholder(R.mipmap.ic_launcher)
                                .into(iB.shutterImageView);
                    } else {
                        picasso.load(R.mipmap.ic_launcher);// load the default
                    }
                }
            }
        });
    }

    /***
     * Bind the Recyclerview Layout manager and restore the scroll position if already the layout manager is available
     */
    private void bindRVLayoutManager() {
        int scrollPosition = -1;
        final int numOfColumns;
        GridLayoutManager layoutManager = ((GridLayoutManager) this.recyclerView.getLayoutManager());
        if (layoutManager != null) {
            scrollPosition = layoutManager.findFirstVisibleItemPosition();// get the scroll position to restore on orientation change
        }

        if (Utils.isInLandscapeMode(context)) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }
        GridLayoutManager mLayoutManager = new GridLayoutManager(context, numOfColumns);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mDataSet.get(position) instanceof Footer) {
                    return numOfColumns;
                } else {
                    return 1;
                }
            }
        });
        if (scrollPosition != RecyclerView.NO_POSITION) {
            mLayoutManager.scrollToPosition(scrollPosition);// set the scroll position
        }
        recyclerView.setLayoutManager(mLayoutManager);
    }

    /***
     * For setting base dataSet
     */
    public RxImageDataSource updateDataSet(List<Item> dataSet) {
        mDataSet = dataSet;
        return this;
    }

    /***
     * For updating Adapter
     */
    public void updateAdapter() {
        if (mRxAdapterForTypes != null) {
            mRxAdapterForTypes.updateDataSet(mDataSet);
        }
    }

}
