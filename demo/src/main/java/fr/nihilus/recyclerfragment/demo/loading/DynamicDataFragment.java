package fr.nihilus.recyclerfragment.demo.loading;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import fr.nihilus.recyclerfragment.RecyclerFragment;
import fr.nihilus.recyclerfragment.demo.MusicAdapter;
import fr.nihilus.recyclerfragment.demo.MusicLibraryLoader;

public class DynamicDataFragment extends RecyclerFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = DynamicDataFragment.class.getSimpleName();
    private MusicAdapter mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated: setting LayoutManager");
        setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MusicAdapter(getContext(), null);
        Log.d(TAG, "onActivityCreated: setting Adapter");
        setAdapter(mAdapter);
        Log.d(TAG, "onActivityCreated: hiding recyclerview while loading");
        setRecyclerShown(false);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MusicLibraryLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: received new adapter data");
        mAdapter.swapCursor(data);
        setRecyclerShown(true);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
