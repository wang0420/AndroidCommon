package com.module.ui.activity.guide.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.module.ui.R;

import androidx.appcompat.app.AppCompatActivity;


/**
 * https://github.com/soulqw/Curtain  Demo
 * <p>
 * https://github.com/binIoter/GuideView
 */
public class GuideMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_main);
    }

    public void showViewGuide(View view) {
        startActivity(new Intent(this, SimpleGuideActivity.class));
    }

    public void showAdapterViewGuide(View view) {
        startActivity(new Intent(this, AdapterViewActivity.class));
    }

    public void showRecyclerViewGuide(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    /**
     * 在复杂多个引导的情况下推荐使用可有效减少方法嵌套
     *
     */
    public void curtainFlow(View view) {
        startActivity(new Intent(this, CurtainFlowGuideActivity.class));
    }

}
