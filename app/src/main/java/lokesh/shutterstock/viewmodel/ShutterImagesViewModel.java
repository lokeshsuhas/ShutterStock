package lokesh.shutterstock.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lokesh.shutterstock.Constants;
import lokesh.shutterstock.ShutterStockApplication;
import lokesh.shutterstock.listeners.BaseListener;
import lokesh.shutterstock.model.Footer;
import lokesh.shutterstock.model.Item;
import lokesh.shutterstock.model.ShutterImages;
import lokesh.shutterstock.network.ShutterService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Lokesh on 04-03-2016.
 */
public class ShutterImagesViewModel implements IViewModel {

    public ObservableBoolean showInfo;
    public ObservableBoolean showFirstBatchProgress;
    public ObservableBoolean showRecyclerView;
    public ObservableField<String> infoMessage;

    @Inject
    ShutterService shutterService;

    private boolean hasMore = true;
    private int pageNo = 1;
    private int retryCount = 3;
    private List<Item> data;
    private ShutterImageListener listener;

    public ShutterImagesViewModel(Context context, ShutterImageListener listener) {
        this.listener = listener;
        this.data = new ArrayList<>();
        showInfo = new ObservableBoolean(false);
        showFirstBatchProgress = new ObservableBoolean(true);
        showRecyclerView = new ObservableBoolean(false);
        infoMessage = new ObservableField<>("");
        ShutterStockApplication.getInstance(context).getShutterComponent().inject(this);
    }

    /***
     * Check whether there are items to load and also is it already loading
     *
     * @return boolean
     */
    public boolean isHasMore() {
        return hasMore && !(data.get(data.size() - 1) instanceof Footer);
    }

    @Override
    public void onResume() {
        if (listener != null) {
            listener.onSetup(data);
        }
        loadShutterImages();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        listener = null;
        data = null;
    }

    /***
     * Load the shutter images
     */
    public void loadShutterImages() {
        if (!showFirstBatchProgress.get()) {
            data.add(new Footer());
            if (listener != null) {
                listener.onUpdate(data);
            }
        }
        shutterService.getImages(pageNo, Constants.PER_PAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ShutterImages>() {
                    @Override
                    public void onCompleted() {
                        if (pageNo == 1) {
                            showFirstBatchProgress.set(false);
                        }
                        showRecyclerView.set(!showInfo.get() && !showFirstBatchProgress.get());
                        pageNo++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!showFirstBatchProgress.get()) {
                            int size = data.size();
                            data.remove(size - 1);
                            if (listener != null) {
                                listener.onUpdate(data);
                            }
                        }
                        if (retryCount != 0) {
                            retryCount--;
                            loadShutterImages();
                        } else {
                            if (listener != null && !showFirstBatchProgress.get()) {
                                listener.onError("Error", "Can't load image,Please check your internet settings or try after sometime");
                            } else {
                                showFirstBatchProgress.set(false);
                                infoMessage.set("Please check your internet settings or click retry");
                                showInfo.set(true);
                            }
                        }
                    }

                    @Override
                    public void onNext(ShutterImages shutterImages) {
                        if (shutterImages.getData().size() > 0) {
                            retryCount = 3;
                            if (!showFirstBatchProgress.get()) {
                                int size = data.size();
                                data.remove(size - 1);
                            }
                            data.addAll(shutterImages.getData());
                            if (listener != null) {
                                listener.onUpdate(data);
                            }
                        } else {
                            hasMore = false;
                            if (listener != null) {
                                listener.onEndReached();
                            }
                        }
                    }
                });
    }

    /***
     * Click event of retry button on network failures
     *
     * @param view
     */
    public void getRetryButton_clicked(View view) {
        showInfo.set(false);
        showFirstBatchProgress.set(true);
        loadShutterImages();
    }

    /***
     * Listener to bind with the activity
     */
    public interface ShutterImageListener extends BaseListener {
        void onUpdate(List<Item> data);

        void onSetup(List<Item> data);

        void onEndReached();
    }
}
