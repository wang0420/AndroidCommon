package com.module.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.basemodule.ARouterManager;
import com.basemodule.net.NetActivity;
import com.module.ui.R;
import com.module.ui.activity.guide.user.GuideMainActivity;
import com.module.ui.adapter.MainAdapter;
import com.module.ui.bean.UIItem;
import com.module.ui.util.DividerItemDecoration;
import com.module.ui.widget.drop_down.DropDownActivity;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


@Route(path = ARouterManager.UIActivity)
public class UIActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    private List<UIItem> getUIItemData() {
        return Arrays.asList(
                new UIItem("圆形进度条", CircleProgressActivity.class),
                new UIItem("UI-Widget使用", WidgetExampleActivity.class),
                new UIItem("尺子刻度", RulerViewActivity.class),
                new UIItem("图片预览", PhotoGalleryActivity.class),
                new UIItem("横向滚动View", HorizontalScrollViewActivity.class),
                new UIItem("支付宝城市服务", RecycleViewTabActivity.class),
                new UIItem("高斯模糊圖片", MoHuImageActivity.class),
                new UIItem("步骤条Step", StepActivity.class),
                new UIItem("视频播放", VideoPlayActivity.class),
                new UIItem("视频播放2", MainVideoActivity.class),
                new UIItem("通讯录", IndexActivity.class),
                new UIItem("NET", NetActivity.class),
                new UIItem("tabIndex", TabIndexActivity.class),
                new UIItem("BottomSheetDialog", BottomSheetActivity.class),
                new UIItem("浮沉引导页效果", GuideMainActivity.class),
                new UIItem("一个支持四个方向循环滚动的自定义控件", MarqueeLayoutActivity.class),
                new UIItem("下拉菜单", DropDownActivity.class)


        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        mRecyclerView = findViewById(R.id.rv_list);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());    // 设置item动画
        mAdapter = new MainAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        for (UIItem item : getUIItemData()) {
            mAdapter.addItem(item);
        }
        setListener();

    }


    private void setListener() {
        mAdapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int index) {
                UIItem item = (UIItem) mAdapter.getItem(index);
                Intent intent = new Intent(UIActivity.this, item.getActivity());
                startActivity(intent);
            }
        });
    }
}
