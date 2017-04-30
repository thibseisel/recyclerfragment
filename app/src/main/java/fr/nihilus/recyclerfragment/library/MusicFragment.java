package fr.nihilus.recyclerfragment.library;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Audio.Media;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import fr.nihilus.recyclerfragment.RecyclerFragment;

public class MusicFragment extends RecyclerFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MusicFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutManager(new LinearLayoutManager(getContext()));
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: start loading.");
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
                Log.d(TAG, "onLoadFinished: loading finished !");
                MusicAdapter adapter = (MusicAdapter) getAdapter();
                if (adapter == null) {
                    adapter = new MusicAdapter(getContext(), data);
                    setAdapter(adapter);
                } else {
                    adapter.swapCursor(data);
                }
            }
        }, 1000);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: loader has been reset.");
        MusicAdapter adapter = (MusicAdapter) getAdapter();
        if (adapter == null) {
            adapter = new MusicAdapter(getContext(), null);
            setAdapter(adapter);
        } else {
            adapter.swapCursor(null);
        }
    }
}
