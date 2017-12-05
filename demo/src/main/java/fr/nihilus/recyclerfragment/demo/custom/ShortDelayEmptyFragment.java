package fr.nihilus.recyclerfragment.demo.custom;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;

import fr.nihilus.recyclerfragment.demo.MusicLibraryLoader;

public class ShortDelayEmptyFragment extends CustomEmptyFragment {

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Reduce loading delays
        return new MusicLibraryLoader(getContext(), 200L);
    }
}
