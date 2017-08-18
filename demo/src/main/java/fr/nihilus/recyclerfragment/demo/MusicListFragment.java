package fr.nihilus.recyclerfragment.demo;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Audio.Media;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class MusicListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private MusicListAdapter mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new MusicListAdapter(getContext(), null);
        setListAdapter(mAdapter);
        setListShown(false);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {Media._ID, Media.TITLE, Media.ALBUM_ID};
        return new CursorLoader(getContext(), Media.EXTERNAL_CONTENT_URI, projection,
                null, null, Media.DEFAULT_SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        Handler delayer = new Handler();
        delayer.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.swapCursor(data);
                setListShown(true);
            }
        }, 1000);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
