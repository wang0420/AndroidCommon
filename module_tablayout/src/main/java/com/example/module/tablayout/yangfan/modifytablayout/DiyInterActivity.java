package com.example.module.tablayout.yangfan.modifytablayout;

import android.os.Bundle;

import com.android.newcommon.viewpage.CustomFragmentPagerAdapter;
import com.example.module.tablayout.R;
import com.example.module.tablayout.yangfan.widget.ModifyTabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class DiyInterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter);
        ModifyTabLayout tabLayout = findViewById(R.id.modiftTabLayout);
        ViewPager vp = findViewById(R.id.vp);
        tabLayout.setViewHeight(dp2px(35));
        tabLayout.setBottomLineWidth(dp2px(20));
        tabLayout.setBottomLineHeight(dp2px(5));
       // tabLayout.setBottomLineHeightBgResId(R.drawable.ttt);
        tabLayout.setItemInnerPaddingLeft(dp2px(6));
        tabLayout.setItemInnerPaddingRight(dp2px(6));
        tabLayout.setmTextColorSelect(ContextCompat.getColor(this, R.color.color_14805E));
        tabLayout.setmTextColorUnSelect(ContextCompat.getColor(this, R.color.color_666666));
        tabLayout.setTextSize(16);

//        int width=getResources().getDisplayMetrics().widthPixels;
//        tabLayout.setNeedEqual(true,width);
        CustomFragmentPagerAdapter adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TestFragment(), "巴西");
        adapter.addFragment(new TestFragment(), "西班牙");
        adapter.addFragment(new TestFragment(), "阿根廷");
        adapter.addFragment(new TestFragment(), "葡萄牙");
        adapter.addFragment(new TestFragment(), "俄罗斯");
        adapter.addFragment(new TestFragment(), "巴西");
        adapter.addFragment(new TestFragment(), "西班牙");
        adapter.addFragment(new TestFragment(), "阿根廷");
        adapter.addFragment(new TestFragment(), "葡萄牙");
        adapter.addFragment(new TestFragment(), "俄罗斯");
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(adapter.getCount());
        //不使用viewpager tabLayout.setTabData(vp);
        tabLayout.setupWithViewPager(vp);
    }

    /**
     * dp转换成px
     */
    public int dp2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
