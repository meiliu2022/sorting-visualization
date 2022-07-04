package com.meiliu.mysortingvisualizer.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.meiliu.mysortingvisualizer.R;
import com.meiliu.mysortingvisualizer.ui.fragment.SortFragment;

public class SortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        if (savedInstanceState == null) {
            SortFragment fragment = new SortFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment)
                    .commit();
        }
    }
}
