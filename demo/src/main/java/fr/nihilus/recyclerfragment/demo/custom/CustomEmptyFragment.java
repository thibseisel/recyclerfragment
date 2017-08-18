package fr.nihilus.recyclerfragment.demo.custom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.nihilus.recyclerfragment.demo.R;
import fr.nihilus.recyclerfragment.demo.loading.EmptyFragment;

public class CustomEmptyFragment extends EmptyFragment {

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_empty, container, false);
    }
}
