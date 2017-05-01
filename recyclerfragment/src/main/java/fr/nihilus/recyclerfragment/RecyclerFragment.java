package fr.nihilus.recyclerfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * <p>A fragment that hosts a {@link RecyclerView} to display a set of items.</p>
 * <p>RecyclerFragment has a default layout that consists of a single RecyclerView.
 * Howether if yo desire, you can customize the fragment layout by returning your own view hierarchy from
 * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
 * To do this, your view hierarchy <em>must</em> contain the following views :
 * <ul>
 * <li>a {@link RecyclerView} with id "@android:id/list"</li>
 * <li>a {@link ContentLoadingProgressBar} with id "@android:id/progress"</li>
 * </ul>
 * </p>
 * <p>Optionnaly, your view hierarchy can contain another view object of any type to display
 * when the recycler view is empty.
 * This empty view must have an id "@android:id/empty". Note that when an empty view is present,
 * the recycler view will be hidden when there is no data to display.</p>
 */
public class RecyclerFragment extends Fragment {

    private static final String TAG = "RecyclerFragment";

    private Adapter<? extends RecyclerView.ViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private ContentLoadingProgressBar mProgress;
    private RecyclerView.LayoutManager mManager;
    private View mEmptyView;

    /**
     * Listens for changes in adapter to show the empty view when adapter is empty.
     */
    private final AdapterDataObserver mEmptyStateObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Log.v(TAG, "onChanged: dataset has changed");
            RecyclerFragment.this.setEmptyShown(hasEmptyState());
        }
    };
    private boolean mIsShown;

    public RecyclerFragment() {
        // Required empty constructor
    }

    /**
     * <p>Called to have this RecyclerFragment instanciate its view hierarchy.</p>
     * <p>The default implementation creates a layout containing a {@link RecyclerView},
     * a {@link ContentLoadingProgressBar} and a {@link TextView} with a simple empty text.
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
        ensureRecycler();
    }

    @Override
    public void onDestroyView() {
        // Nullify view references to free memory when fragment is retained
        mRecycler = null;
        mEmptyView = null;
        mProgress = null;
        mIsShown = false;
        super.onDestroyView();
    }

    /**
     * Sets the {@link RecyclerView.LayoutManager} object for the {@link RecyclerView} hosted by this fragment.
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
        if (getView() != null) {
            if (mEmptyView == null) {
                Log.v(TAG, "setEmptyShown: no empty view, changing recyclerview visibility");
                mRecycler.setVisibility(shown ? View.GONE : View.VISIBLE);
                return;
            }

            Log.v(TAG, "setEmptyShown() called with: shown = [" + shown + "]");

            mRecycler.setVisibility(shown ? View.GONE : View.VISIBLE);
            mEmptyView.setVisibility(shown ? View.VISIBLE : View.GONE);
        }
    }

    private boolean hasEmptyState() {
        return mAdapter == null || mAdapter.getItemCount() == 0;
    }

    /**
     * <p>Returns the recycler view hosted by this fragment.</p>
     * <p>Note : you <b>must</b> add an adapter to this recycler view with {@link #setAdapter(Adapter)}
     * instead of using {@link RecyclerView#setAdapter(Adapter)} directly.</p>
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
     * an adapter is given with {@link #setAdapter(Adapter)}.</p>
     *
     * @param shown if {@code true} the recycler view is shown, if {@code false} the progress indicator.
     */
    public void setRecyclerShown(boolean shown) {
        Log.v(TAG, "setRecyclerShown() called with: shown = [" + shown + "]");
        ensureRecycler();
        if (mIsShown == shown) {
            // Visibility has not changed, take no action
            return;
        }

        mIsShown = shown;
        Log.v(TAG, "setRecyclerShown: changing progress and recycler view visibility");
        Log.v(TAG, "setRecyclerShown: is empty ? " + hasEmptyState());
        if (shown) {
            mProgress.hide();
            setEmptyShown(hasEmptyState());

        } else {
            mProgress.show();
            mRecycler.setVisibility(View.GONE);

            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.GONE);
            }
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

        Log.v(TAG, "setAdapter: had an adapter ? " + hadAdapter);

        if (hadAdapter) {
            // Stop observing the previous adapter
            mAdapter.unregisterAdapterDataObserver(mEmptyStateObserver);
        }

        if (adapter != null) {
            adapter.registerAdapterDataObserver(mEmptyStateObserver);
        }

        mAdapter = adapter;
        if (mRecycler != null) {
            Log.v(TAG, "setAdapter: successfully setting adapter.");
            mRecycler.setAdapter(adapter);
            if (!mIsShown && !hadAdapter) {
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

        View rawRecycler = root.findViewById(android.R.id.list);
        View rawProgress = root.findViewById(android.R.id.progress);

        if (!(rawRecycler instanceof RecyclerView)) {
            if (mRecycler == null) {
                throw new RuntimeException("Your content must have a RecyclerView " +
                        "whose id attribute is 'android.R.id.list'");
            }
            throw new RuntimeException("Content has view with id attribute 'android.R.id.list'" +
                    "that is not a RecyclerView class");
        }

        if (!(rawProgress instanceof ContentLoadingProgressBar)) {
            if (mProgress == null) {
                throw new RuntimeException("Your content must have a ContentLoadingProgressBar " +
                        "whose id attribute is 'android.R.id.progress'");
            }
            throw new RuntimeException("Content has view with id 'android.R.id.progress'" +
                    "that is not a ContentLoadingProgressBar class");
        }

        mRecycler = (RecyclerView) rawRecycler;
        mProgress = (ContentLoadingProgressBar) rawProgress;
        mEmptyView = root.findViewById(android.R.id.empty);

        setLayoutManager(mManager);

        mIsShown = true;
        if (mAdapter != null) {
            // If adapter is already provided, show the recycler view
            Log.v(TAG, "ensureRecycler: adapter already provided");
            Adapter<? extends ViewHolder> adapter = mAdapter;
            mAdapter = null;
            setAdapter(adapter);
        } else {
            Log.v(TAG, "ensureRecycler: no adapter provided");
            // We are starting without an adapter, so assume we won't
            // have our data right away and start with the progress indicator.
            if (mProgress != null) {
                setRecyclerShown(false);
            }
        }
    }
}
