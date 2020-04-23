package com.module.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.module.ui.R;
import com.module.ui.adapter.MainBean;
import com.module.ui.adapter.RecycleTabMainAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewTabActivity extends AppCompatActivity {

    private List<MainBean> list;
    private RecycleTabMainAdapter adapter;
    private RecyclerView recyclerView;

    private LinearLayoutManager manager;
    private TabLayout tabLayout;

    //tab的标签
    private String[] str = {"啤酒", "饮料", "矿泉水", "瓜子", "二手车", "孙红雷", "极限挑战", "潜伏", "余则成", "姚晨"};

    /**
     * 需要定位的地方，从小到大排列，需要和tab对应起来，长度一样
     */
    private int[] str1 = {0, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    private boolean isScrolled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);
        initData();
        init();
        initTab();
    }

    private void initTab() {
        for (int i = 0; i < str.length; i++) {
            //插入tab标签
            tabLayout.addTab(tabLayout.newTab().setText(str[i]));
        }
        //标签页可以滑动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                Log.w("Mainity", "tabpos=" + pos);
                if (!isScrolled) {
                    //滑动时不能点击,
                    //第一个参数是指定的位置，锚点
                    // 第二个参数表示 Item 移动到第一项后跟 RecyclerView 上边界或下边界之间的距离（默认是 0）
                    manager.scrollToPositionWithOffset(str1[pos], 0);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void init() {
        recyclerView = findViewById(R.id.mian_recy);
        tabLayout = findViewById(R.id.main_tab);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new RecycleTabMainAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecycleViewTabActivity.this, "点击的item=" + position, Toast.LENGTH_SHORT).show();
//                switch (position) {
//                    case 0:
//
//                        ToastShow.show(MainActivity.this, "item---" + 0000);
//                        break;
//                    default:
//                        break;
//                }
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //重写该方法主要是判断recyclerview是否在滑动
                //0停止 ，12都是滑动
                if (newState == 0) {
                    isScrolled = false;
                } else {
                    isScrolled = true;
                }
                setMsg("isScrolled" + isScrolled + "--newState=" + newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //这个主要是recyclerview滑动时让tab定位的方法
                if (isScrolled) {
                    int top = manager.findFirstVisibleItemPosition();
                    int bottom = manager.findLastVisibleItemPosition();

                    int pos = 0;
                    if (bottom == list.size() - 1) {
                        //先判断滑到底部，tab定位到最后一个
                        pos = str1.length - 1;
                    } else if (top == str1[str1.length - 1]) {
                        //如果top等于指定的位置，对应到tab即可，
                        pos = str1[str1.length - 1];
                    } else {
                        //循环遍历，需要比较i+1的位置，所以循环长度要减1，
                        //  如果 i<top<i+1,  那么tab应该定位到i位置的字符，不管是向上还是向下滑动
                        for (int i = 0; i < str1.length - 1; i++) {
                            if (top == str1[i]) {
                                pos = i;
                                break;
                            } else if (top > str1[i] && top < str1[i + 1]) {
                                pos = i;
                                break;
                            }
                        }
                    }

                    //设置tab滑动到第pos个
                    tabLayout.setScrollPosition(pos, 0f, true);
                }

            }
        });

    }

    public static void setMsg(String str) {
        Log.i("tab", str);
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new MainBean("title--" + i));
        }

    }
}
