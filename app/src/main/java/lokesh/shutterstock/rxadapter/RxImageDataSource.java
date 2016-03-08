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
import java.util.Collections;
import java.util.List;

import lokesh.shutterstock.R;
import lokesh.shutterstock.ShutterStockApplication;
import lokesh.shutterstock.Utils;
import lokesh.shutterstock.databinding.RecyclerItemBinding;
import lokesh.shutterstock.model.Datum;
import lokesh.shutterstock.model.Footer;
import lokesh.shutterstock.model.Item;
import rx.Observable;
import rx.Scheduler;
import rx.annotations.Beta;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by ahmedrizwan on 26/12/2015.
 */
public class RxImageDataSource {

    final int TYPE_FOOTER = 0;
    final int TYPE_ITEM = 1;


    private List<Item> mDataSet;
    private RxImageAdapter<Item> mRxAdapterForTypes;
    private Picasso picasso;
    private Context context;
    private RecyclerView recyclerView;

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
        int scrollPosition = -1;
        int count =-1;
        GridLayoutManager layoutManager = ((GridLayoutManager)this.recyclerView.getLayoutManager());
        if(layoutManager != null)
        {
            scrollPosition = layoutManager.findFirstVisibleItemPosition();
            count = layoutManager.getChildCount();
        }
        bindRVLayoutManager(scrollPosition,count);

        //initialize the viewholders
        List<ViewHolderInfo> viewHolderInfoList = new ArrayList<>();
        viewHolderInfoList.add(new ViewHolderInfo(R.layout.recycler_item, TYPE_ITEM, true)); //TYPE_ITEM = 1
        viewHolderInfoList.add(new ViewHolderInfo(R.layout.recycler_footer, TYPE_FOOTER, false)); //TYPE_FOOTER = 0
        //initialize
        mRxAdapterForTypes = new RxImageAdapter<>(mDataSet, viewHolderInfoList, new OnGetItemViewType() {
            @Override
            protected int getItemViewType(int position) {
                if (mDataSet.get(position) instanceof Footer) {
                    return TYPE_FOOTER;
                } else {
                    return TYPE_ITEM;
                }
            }
        });
        //bind the adapters
        recyclerView.setAdapter(mRxAdapterForTypes);
        mRxAdapterForTypes.asObservable().subscribe(new Action1<TypesViewHolder<Item>>() {
            @Override
            public void call(TypesViewHolder<Item> viewHolder) {
                final ViewDataBinding b = viewHolder.getViewDataBinding();
                if (b instanceof RecyclerItemBinding) {
                    final RecyclerItemBinding iB = (RecyclerItemBinding) b;
                    Datum item = (Datum) viewHolder.getItem();
                    picasso.load(Uri.parse(item.getAssets().getPreview().getUrl()))
                            .resize(viewHolder.getWidth(), viewHolder.getHeight())
                            .centerCrop()
                            .placeholder(R.mipmap.ic_launcher)
                            .into(iB.shutterImageView);
                }
            }
        });
        return this;
    }

    public void setScrollListener(RecyclerView.OnScrollListener listener) {
        if (listener != null) {
            this.recyclerView.addOnScrollListener(listener);
        }
    }

    private void bindRVLayoutManager(int scrollPosition, int count) {
        final int numOfColumns;
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
        if(scrollPosition != RecyclerView.NO_POSITION){
            mLayoutManager.scrollToPosition(scrollPosition);
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

    public RxImageDataSource map(Func1<? super Item, ? extends Item> func) {
        mDataSet = Observable.from(mDataSet).map(func).toList().toBlocking().first();
        return this;
    }

    public RxImageDataSource filter(Func1<? super Item, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).filter(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource last() {
        mDataSet = Observable.from(mDataSet).last().toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource last(Func1<? super Item, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).last(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource lastOrDefault(Item defaultValue) {
        mDataSet = Observable.from(mDataSet)
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxImageDataSource lastOrDefault(Item defaultValue,
                                                 Func1<? super Item, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet)
                .filter(predicate)
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxImageDataSource limit(int count) {
        mDataSet = Observable.from(mDataSet).limit(count).toList().toBlocking().first();
        return this;
    }

    public RxImageDataSource empty() {
        mDataSet = Collections.emptyList();
        return this;
    }

    public final <R> RxImageDataSource concatMap(
            Func1<? super Item, ? extends Observable<? extends Item>> func) {
        mDataSet = Observable.from(mDataSet).concatMap(func).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource concatWith(Observable<? extends Item> t1) {
        mDataSet = Observable.from(mDataSet).concatWith(t1).toList().toBlocking().first();
        return this;
    }

    public RxImageDataSource distinct() {
        mDataSet = Observable.from(mDataSet).distinct().toList().toBlocking().first();
        return this;
    }

    public RxImageDataSource distinct(Func1<? super Item, ? extends Object> keySelector) {
        mDataSet = Observable.from(mDataSet).distinct(keySelector).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource elementAt(int index) {
        mDataSet = Observable.from(mDataSet).elementAt(index).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource elementAtOrDefault(int index, Item defaultValue) {
        mDataSet = Observable.from(mDataSet)
                .elementAtOrDefault(index, defaultValue)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxImageDataSource first() {
        mDataSet = Observable.from(mDataSet).first().toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource first(Func1<? super Item, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).first(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource firstOrDefault(Item defaultValue) {
        mDataSet = Observable.from(mDataSet).firstOrDefault(defaultValue).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource firstOrDefault(Item defaultValue,
                                                  Func1<? super Item, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet)
                .firstOrDefault(defaultValue, predicate)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxImageDataSource flatMap(
            Func1<? super Item, ? extends Observable<? extends Item>> func) {
        mDataSet = Observable.from(mDataSet).flatMap(func).toList().toBlocking().first();
        return this;
    }

    @Beta
    public final RxImageDataSource flatMap(
            Func1<? super Item, ? extends Observable<? extends Item>> func, int maxConcurrent) {
        mDataSet = Observable.from(mDataSet).flatMap(func, maxConcurrent).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource flatMap(
            Func1<? super Item, ? extends Observable<? extends Item>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends Item>> onError,
            Func0<? extends Observable<? extends Item>> onCompleted) {
        mDataSet = Observable.from(mDataSet)
                .flatMap(onNext, onError, onCompleted)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    @Beta
    public final RxImageDataSource flatMap(
            Func1<? super Item, ? extends Observable<? extends Item>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends Item>> onError,
            Func0<? extends Observable<? extends Item>> onCompleted, int maxConcurrent) {
        mDataSet = Observable.from(mDataSet)
                .flatMap(onNext, onError, onCompleted, maxConcurrent)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final <U, R> RxImageDataSource flatMap(
            final Func1<? super Item, ? extends Observable<? extends U>> collectionSelector,
            final Func2<? super Item, ? super U, ? extends Item> resultSelector) {
        mDataSet = Observable.from(mDataSet)
                .flatMap(collectionSelector, resultSelector)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxImageDataSource flatMapIterable(
            Func1<? super Item, ? extends Iterable<? extends Item>> collectionSelector) {
        mDataSet =
                Observable.from(mDataSet).flatMapIterable(collectionSelector).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource reduce(Func2<Item, Item, Item> accumulator) {
        mDataSet = Observable.from(mDataSet).reduce(accumulator).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource reduce(Item initialValue,
                                          Func2<Item, ? super Item, Item> accumulator) {
        mDataSet =
                Observable.from(mDataSet).reduce(initialValue, accumulator).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource repeat(final long count) {
        List<Item> dataSet = mDataSet;
        mDataSet = Observable.from(dataSet).repeat(count).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource repeat(final long count, Scheduler scheduler) {
        mDataSet = Observable.from(mDataSet).repeat(count, scheduler).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource take(final int count) {
        mDataSet = Observable.from(mDataSet).take(count).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource takeFirst(Func1<? super Item, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).takeFirst(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxImageDataSource takeLast(final int count) {
        mDataSet = Observable.from(mDataSet).takeLast(count).toList().toBlocking().first();
        return this;
    }
}
