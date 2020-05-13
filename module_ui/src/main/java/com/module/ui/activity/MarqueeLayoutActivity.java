package com.module.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.utils.GlideUtils;
import com.module.ui.R;
import com.module.ui.widget.MarqueeLayout;
import com.module.ui.widget.MarqueeLayoutAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

/**
 * https://github.com/oubowu/MarqueeLayoutLibrary
 * https://github.com/gongwen/MarqueeViewLibrary
 */
public class MarqueeLayoutActivity extends AppCompatActivity {

    private MarqueeLayout mMarqueeLayout;
    private MarqueeLayout mMarqueeLayout1;

    private List<String> mSrcList;
    private MarqueeLayoutAdapter<String> mSrcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        mMarqueeLayout = (MarqueeLayout) findViewById(R.id.marquee_layout);
        mSrcList = new ArrayList<>();
        mSrcList.add("我听见了你的声音 也藏着颗不敢见的心");
        mSrcList.add("我们的爱情到这刚刚好 剩不多也不少 还能忘掉");
        mSrcList.add("像海浪撞过了山丘以后还能撑多久 他可能只为你赞美一句后往回流");
        mSrcList.add("少了有点不甘 但多了太烦");
        mSrcAdapter = new MarqueeLayoutAdapter<String>(mSrcList) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_simple_text;
            }

            @Override
            public void initView(View view, int position, String item) {
                ((TextView) view).setText(item);
            }
        };
        mMarqueeLayout.setAdapter(mSrcAdapter);
        mMarqueeLayout.start();

        mMarqueeLayout1 = (MarqueeLayout) findViewById(R.id.marquee_layout1);
        final List<String> imgs = new ArrayList<>();
        imgs.add("https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2534506313,1688529724&fm=26&gp=0.jpg");
        imgs.add("https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3252521864,872614242&fm=26&gp=0.jpg");
        MarqueeLayoutAdapter<String> adapter1 = new MarqueeLayoutAdapter<String>(imgs) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_simple_image;
            }

            @Override
            public void initView(View view, int position, String item) {
                //Glide.with(view.getContext()).load(item).into((ImageView) view);
                GlideUtils.loadImage(MarqueeLayoutActivity.this, (ImageView) view,
                        item
                );
            }
        };
        adapter1.setItemClickListener(new MarqueeLayoutAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e("TAG", "MainActivity-74行-onClick(): " + position);
            }
        }, R.id.iv);
        mMarqueeLayout1.setAdapter(adapter1);
        mMarqueeLayout1.start();

    }

    // 删歌词
    public void deleteSrc(View view) {
        if (mSrcList.size() != 0) {
            mSrcList.remove(mSrcList.size() - 1);
            mSrcAdapter.notifyDataSetChanged();
        }
    }

    // 添加歌词
    public void addSrc(View view) {
        if (mSrcList != null) {
            Random random = new Random();
            mSrcList.add("添加歌词: " + random.nextInt(12345));
            mSrcAdapter.notifyDataSetChanged();
        }
    }

}
