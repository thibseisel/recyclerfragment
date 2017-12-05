package fr.nihilus.recyclerfragment.demo.custom;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.nihilus.recyclerfragment.demo.R;
import fr.nihilus.recyclerfragment.demo.loading.AsynchronousLoadingFragment;

public class CustomEmptyFragment extends AsynchronousLoadingFragment {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate a custom view that includes a TextView to show when empty
        return inflater.inflate(R.layout.fragment_custom_empty, container, false);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Pretend not to have any data
        super.onLoadFinished(loader, null);
    }
}
