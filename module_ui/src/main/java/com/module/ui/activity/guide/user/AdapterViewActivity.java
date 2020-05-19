package com.module.ui.activity.guide.user;

import android.os.Bundle;
import android.view.View;

import com.module.ui.R;
import com.module.ui.activity.guide.user.fragment.GirdViewFragment;
import com.module.ui.activity.guide.user.fragment.ListViewFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdapterViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_guide);
        showList(null);
    }

    public void showList(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root, new ListViewFragment())
                .commitAllowingStateLoss();
    }

    public void showGird(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root, new GirdViewFragment())
                .commitAllowingStateLoss();
    }

}
