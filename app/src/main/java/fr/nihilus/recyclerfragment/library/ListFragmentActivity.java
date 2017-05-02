package fr.nihilus.recyclerfragment.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ListFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MusicListFragment(), "MusicListFragment")
                    .commit();
        }
    }
}
