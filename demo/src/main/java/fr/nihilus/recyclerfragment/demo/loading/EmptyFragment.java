package fr.nihilus.recyclerfragment.demo.loading;

import android.database.Cursor;
import android.support.v4.content.Loader;

public class EmptyFragment extends AsynchronousLoadingFragment {

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Pretend not to have any data
        super.onLoadFinished(loader, null);
    }
}
