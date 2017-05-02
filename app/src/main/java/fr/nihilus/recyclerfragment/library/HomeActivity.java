package fr.nihilus.recyclerfragment.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import fr.nihilus.recyclerfragment.RecyclerFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MenuListFragment(), "Menu")
                    .commit();
        }
    }

    public static class MenuListFragment extends RecyclerFragment
            implements MenuAdapter.OnSampleSelectedListener {

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            setLayoutManager(new LinearLayoutManager(getContext()));
            MenuAdapter adapter = new MenuAdapter();
            adapter.setOnSampleSelectedListener(this);
            setAdapter(adapter);
        }

        @Override
        public void onSampleSelected(Sample selectedSample) {
            Intent intent = new Intent(getContext(), selectedSample.getActivityClass());
            startActivity(intent);
        }
    }

}
