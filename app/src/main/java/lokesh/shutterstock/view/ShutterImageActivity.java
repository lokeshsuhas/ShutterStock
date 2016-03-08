package lokesh.shutterstock.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import lokesh.shutterstock.R;
import lokesh.shutterstock.databinding.ActivityShutterImageBinding;
import lokesh.shutterstock.databinding.RecyclerItemBinding;
import lokesh.shutterstock.model.Item;
import lokesh.shutterstock.rxadapter.RxImageDataSource;
import lokesh.shutterstock.rxadapter.SavedState;
import lokesh.shutterstock.viewmodel.ShutterImagesViewModel;

public class ShutterImageActivity extends ViewModelActivity implements ShutterImagesViewModel.ShutterImageListener {
    private static Bundle mBundleRecyclerViewState;

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private final String KEY_RECYCLER_SCROLL_POSITION = "recycler_state_scroll_position";
    ShutterImagesViewModel viewModel;
    ActivityShutterImageBinding mShutterImageBinding;
    private RxImageDataSource dataSource;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        mShutterImageBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_shutter_image);

        viewModel = new ShutterImagesViewModel(this, this);
        mShutterImageBinding.setViewModel(viewModel);
        mShutterImageBinding.toolbar.setTitle("Shutter Stock Images");
        setSupportActionBar(mShutterImageBinding.toolbar);
    }

    @Override
    public void onError(String header, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(ShutterImageActivity.this).create();
        alertDialog.setTitle(header);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        GridLayoutManager layoutManager = (GridLayoutManager)mShutterImageBinding.RecyclerView.getLayoutManager();
        if(layoutManager != null) {
            mBundleRecyclerViewState = new Bundle();
            Parcelable listState = mShutterImageBinding.RecyclerView.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
            SavedState newState = new SavedState(listState);
            newState.mScrollPosition = ((GridLayoutManager) mShutterImageBinding.RecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_SCROLL_POSITION, newState);
        }
        viewModel.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        GridLayoutManager layoutManager = (GridLayoutManager)mShutterImageBinding.RecyclerView.getLayoutManager();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null && layoutManager!= null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            layoutManager.onRestoreInstanceState(listState);
            //restore the scroll state
            Parcelable scrollState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_SCROLL_POSITION);
            if(scrollState != null && scrollState instanceof SavedState){
                int mScrollPosition = ((SavedState) scrollState).mScrollPosition;
                if(layoutManager != null){
                    int count = layoutManager.getChildCount();
                    if(mScrollPosition != RecyclerView.NO_POSITION && mScrollPosition < count){
                        layoutManager.scrollToPosition(mScrollPosition);
                    }
                }
            }
        } else {
            viewModel.onResume();
        }
    }

    @Override
    public void onNext() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRecyclerView();

    }

    @Override
    public void onUpdate(List<Item> data) {
        dataSource.updateDataSet(data).updateAdapter();
    }

    @Override
    public void onSetup(List<Item> data) {
        dataSource = new RxImageDataSource(context, data);
        setRecyclerView();
    }

    @Override
    public void onEndReached() {
        showToast("No more items to load up!");
    }

    private void setRecyclerView() {
        dataSource
                .<RecyclerItemBinding>bindRecyclerView(mShutterImageBinding.RecyclerView)
                .setScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (viewModel.isHasMore()) {
                            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                            if (layoutManager.findLastCompletelyVisibleItemPosition() >= layoutManager.getItemCount() - 3) {
                                viewModel.loadShutterImages();
                            }
                        }
                    }
                });
    }
}
