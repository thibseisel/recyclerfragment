package fr.nihilus.recyclerfragment.demo;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.content.CursorLoader;
import android.util.Log;

public class MusicLibraryLoader extends CursorLoader {

    private static final String[] PROJECTION = {Media._ID, Media.TITLE, Media.ALBUM, Media.ALBUM_ID};
    private static final String SELECTION = Media.IS_MUSIC + " = 1";

    private static final String TAG = "MusicLibraryLoader";

    private long mDelay;

    public MusicLibraryLoader(Context context) {
        super(context, Media.EXTERNAL_CONTENT_URI, PROJECTION, SELECTION, null, Media.DEFAULT_SORT_ORDER);
    }

    public MusicLibraryLoader(Context context, long delayMillis) {
        super(context, Media.EXTERNAL_CONTENT_URI, PROJECTION, SELECTION, null, Media.DEFAULT_SORT_ORDER);
        if (delayMillis < 0) throw new IllegalArgumentException("Delay must be positive or zero");
        mDelay = delayMillis;
    }

    @Override
    public Cursor loadInBackground() {
        // Wait the specified amount of time before loading.
        try {
            Thread.sleep(mDelay);
        } catch (InterruptedException e) {
            Log.w(TAG, "loadInBackground: exception while waiting", e);
        }

        return super.loadInBackground();
    }
}
