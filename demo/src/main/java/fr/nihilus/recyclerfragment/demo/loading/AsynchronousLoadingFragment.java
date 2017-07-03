package fr.nihilus.recyclerfragment.demo.loading;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;

import fr.nihilus.recyclerfragment.demo.MusicLibraryLoader;

public class AsynchronousLoadingFragment extends DynamicDataFragment {

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Add a fake delay of 1 second to see the progress indicator
        return new MusicLibraryLoader(getContext(), 1000);
    }
}
