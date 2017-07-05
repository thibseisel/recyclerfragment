package fr.nihilus.recyclerfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * <p>A fragment that hosts a RecyclerView to display a set of items.</p>
 * <p>RecyclerFragment has a default layout that consists of a single RecyclerView.
 * Howether if yo desire, you can customize the fragment layout by returning your own view hierarchy from
 * onCreateView.
 * To do this, your view hierarchy <em>must</em> contain the following views :
 * <ul>
 * <li>a RecyclerView with id "@android:id/list"</li>
 * <li>any View with id "@android:id/progress"</li>
 * </ul>
 * <p>Optionnaly, your view hierarchy can contain another view object of any type to display
 * when the recycler view is empty.
 * This empty view must have an id "@android:id/empty". Note that when an empty view is present,
 * the recycler view will be hidden when there is no data to display.
 */
public class RecyclerFragment extends Fragment {
    private static final String TAG = "RecyclerFragment";
    private static final int MIN_DELAY = 500;

    private Adapter<? extends RecyclerView.ViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private View mProgress;
    private View mRecyclerContainer;
    private RecyclerView.LayoutManager mManager;
    private View mEmptyView;
    private boolean mIsShown;

    private final AdapterDataObserver mEmptyStateObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (isVisible()) {
                RecyclerFragment.this.setEmptyShown(isEmpty());
            }
        }
    };

    private final Runnable mDelayedShow = new Runnable() {
        @Override
        public void run() {
            showRecycler(true);
        }
    };

    private final Runnable mDelayedHide = new Runnable() {
        @Override
        public void run() {
            showRecycler(false);
        }
    };

    public RecyclerFragment() {
        // Required empty constructor
    }

    /**
     * <p>Called to have this RecyclerFragment instanciate its view hierarchy.</p>
     * <p>The default implementation creates a layout containing a RecyclerView,
     * a ProgressBar and a TextView with a simple empty text.
     * You can override this method to define your own view hierarchy for this fragment.
     * </p>
     *
     * @return the view for this fragment UI
     */
    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureRecycler();
    }

    @Override
    public void onStop() {
        mRecyclerContainer.removeCallbacks(mDelayedShow);
        mRecyclerContainer.removeCallbacks(mDelayedHide);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mRecycler = null;
        mRecyclerContainer = mEmptyView = mProgress = null;
        mIsShown = false;
        super.onDestroyView();
    }

    /**
     * Sets the RecyclerView.LayoutManager object for the RecyclerView hosted by this fragment.
     * Note that if you don't specify the layout manager no data will be displayed.
     *
     * @param manager the layout manager used to lay out items in this fragment's recycler view
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mManager = manager;
        if (mRecycler != null) {
            mRecycler.setLayoutManager(manager);
        }
    }

    private void setEmptyShown(boolean shown) {
        if (mEmptyView == null) {
            mRecycler.setVisibility(shown ? View.GONE : View.VISIBLE);
            return;
        }

        mRecycler.setVisibility(shown ? View.GONE : View.VISIBLE);
        mEmptyView.setVisibility(shown ? View.VISIBLE : View.GONE);
    }

    boolean isEmpty() {
        return mAdapter == null || mAdapter.getItemCount() == 0;
    }

    /**
     * <p>Returns the recycler view hosted by this fragment.</p>
     * <p>Note : you <b>must</b> add an adapter to this recycler view with setAdapter(Adapter)
     * instead of using RecyclerView#setAdapter(Adapter) directly.</p>
     *
     * @return the recycler view hosted by this fragment
     */
    public RecyclerView getRecyclerView() {
        ensureRecycler();
        return mRecycler;
    }

    /**
     * <p>Control whether the recycler view is beeing displayed.
     * You can make it not displayed if you are waiting for the initial data to be available.
     * During this time an indeterminate progress indicator will be shown instead.</p>
     * <p>The default implementation will start with the recycler view hidden, showing it only once
     * an adapter is given with etAdapter(Adapter).</p>
     *
     * @param shown if {@code true} the recycler view is shown, if {@code false} the progress indicator.
     */
    public void setRecyclerShown(boolean shown) {
        ensureRecycler();

        if (shown) {
            mRecyclerContainer.removeCallbacks(mDelayedHide);
            mRecyclerContainer.postDelayed(mDelayedShow, MIN_DELAY);
        } else {
            mRecyclerContainer.removeCallbacks(mDelayedShow);
            mRecyclerContainer.postDelayed(mDelayedHide, MIN_DELAY);
        }
    }

    private void showRecycler(boolean shown) {
        if (mIsShown == shown) {
            return;
        }

        mIsShown = shown;
        if (shown) {
            mRecyclerContainer.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
        } else {
            mRecyclerContainer.setVisibility(View.GONE);
            mProgress.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Get the adapter associated with this fragment's RecyclerView.
     */
    public Adapter<? extends ViewHolder> getAdapter() {
        return mAdapter;
    }

    /**
     * Sets the adapter for the RecyclerView hosted by this fragment.
     * If the recycler view was hidden and had no adapter, it will be shown.
     *
     * @param adapter the adapter to be associated with this fragment's RecyclerView
     */
    public void setAdapter(Adapter<? extends ViewHolder> adapter) {
        boolean hadAdapter = mAdapter != null;

        if (hadAdapter) {
            // Stop observing the previous adapter
            mAdapter.unregisterAdapterDataObserver(mEmptyStateObserver);
        }

        if (adapter != null) {
            // Start observing the new adapter
            adapter.registerAdapterDataObserver(mEmptyStateObserver);
        }

        mAdapter = adapter;
        if (mRecycler != null) {
            mRecycler.setAdapter(adapter);
            if (!hadAdapter) {
                // The list was hidden, and previously didn't have an adapter.
                // It is now time to show it.
                setRecyclerShown(true);
            }
        }
    }

    /**
     * Check that the view hierarchy provided for this fragment contains at least
     * a recycler view and a progress bar, then if so configure the recycler view
     * with the provided layout manager and adapter.
     */
    private void ensureRecycler() {
        if (mRecycler != null) {
            return;
        }

        View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }

        mProgress = root.findViewById(R.id.progress);
        if (mProgress == null) {
            throw new RuntimeException("Your content must have a View with id 'R.id.progress' " +
                    "to be displayed when RecyclerView is not shown");
        }

        mRecyclerContainer = root.findViewById(R.id.recycler_container);
        if (mRecyclerContainer == null) {
            throw new RuntimeException("Your content must have a ViewGroup " +
                    "whose id attribute is 'R.id.recycler_container'");
        }

        View rawRecycler = root.findViewById(R.id.recycler);
        if (!(rawRecycler instanceof RecyclerView)) {
            if (mRecycler == null) {
                throw new RuntimeException("Your content must have a RecyclerView " +
                        "whose id attribute is 'R.id.recycler' and child of R.id.recycler_container");
            }
            throw new RuntimeException("Content has view with id attribute 'R.id.recycler'" +
                    "that is not a RecyclerView");
        }

        mRecycler = (RecyclerView) rawRecycler;
        mEmptyView = root.findViewById(R.id.empty);

        setLayoutManager(mManager);

        mIsShown = true;
        if (mAdapter != null) {
            // If adapter is already provided, show the recycler view
            Adapter<? extends ViewHolder> adapter = mAdapter;
            mAdapter = null;
            setAdapter(adapter);
        } else {
            // We are starting without an adapter, so assume we won't
            // have our data right away and start with the progress indicator.
            setRecyclerShown(false);
        }
    }
}
