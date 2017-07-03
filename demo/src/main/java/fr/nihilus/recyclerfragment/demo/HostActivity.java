package fr.nihilus.recyclerfragment.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import fr.nihilus.recyclerfragment.RecyclerFragment;

public class HostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        if (savedInstanceState == null) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 99);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            String fragmentClassName = getIntent().getDataString();
            addSampleFragment(fragmentClassName);
        }
    }

    private void addSampleFragment(String fragmentClassName) {
        RecyclerFragment fragment;
        try {
            Class<?> fragmentClass = Class.forName(fragmentClassName);
            fragment = (RecyclerFragment) fragmentClass.newInstance();
        } catch (ClassNotFoundException cne) {
            throw new AssertionError("Class name is not correctly spelled. Check xml file.");
        } catch (Exception e) {
            throw new AssertionError("Cannot instantiate fragment class");
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, "FRAG")
                .commit();
    }
}
