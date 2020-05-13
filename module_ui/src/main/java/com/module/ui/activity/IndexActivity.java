package com.module.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.gavin.com.library.StickyDecoration;
import com.gavin.com.library.listener.GroupListener;
import com.module.ui.R;
import com.module.ui.adapter.MainMemberAdapter;
import com.module.ui.bean.MemberBean;
import com.module.ui.util.DisplayUtil;
import com.module.ui.util.PinyinComparator;
import com.module.ui.util.PinyinUtils;
import com.module.ui.widget.IndexBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IndexActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName() + "-kmm";
    private RecyclerView mRecyclerView;
    private MainMemberAdapter mAdapter;
    private LinearLayoutManager mManager;
    private IndexBar sideBar;
    private TextView dialog;

    private List<MemberBean> SourceDateList = new ArrayList<>();
    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mRecyclerView = findViewById(R.id.recyclerView);
        sideBar = findViewById(R.id.sideBar);
        dialog = findViewById(R.id.dialog);
        sideBar.setSelectedIndexTextView(dialog);
        initData();
    }

    protected void initData() {
        pinyinComparator = new PinyinComparator();
        mManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());    // 设置item动画
        mAdapter = new MainMemberAdapter(this);
        //------------- StickyDecoration 使用部分  ----------------
        StickyDecoration decoration = StickyDecoration.Builder
                .init(new GroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //组名回调
                        if (SourceDateList.size() > position && position > -1) {
                            //获取组名，用于判断是否是同一组
                            return SourceDateList.get(position).getLetters();
                        }
                        return null;
                    }
                })
                .setGroupBackground(getResources().getColor(R.color.colorAccent))       //背景色
                .setGroupHeight(DisplayUtil.dip2px(this, 35))     //高度
                .setGroupTextColor(getResources().getColor(R.color.colorPrimaryDark)) //字体颜色 （默认）
                .setGroupTextSize(DisplayUtil.dip2px(this, 15))    //字体大小
                .setTextSideMargin(DisplayUtil.dip2px(this, 10))  // 边距   靠左时为左边距  靠右时为右边距
                .build();
        //------------- StickyDecoration 使用部分  ----------------
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mAdapter);

        //设置右侧SideBar触摸监听
        sideBar.setOnIndexChangedListener(new IndexBar.OnIndexChangedListener() {
            @Override
            public void onIndexChanged(String index) {

                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(index.charAt(0));
                if (position != -1) {
                    mManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        SourceDateList = filledData(getResources().getStringArray(R.array.dateName));

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        List<String> head = new ArrayList<>();
        for (int i = 0; i < SourceDateList.size(); i++) {
            if (!head.contains(SourceDateList.get(i).getLetters())) {
                head.add(SourceDateList.get(i).getLetters());
            }
        }
        String[] arr = new String[head.size()];
        head.toArray(arr);
        sideBar.setIndexs(arr);
        mAdapter.addAll(SourceDateList);
        mAdapter.notifyDataSetChanged();

    }

    /**
     * 为RecyclerView填充数据
     *
     * @param date
     * @return
     */
    private List<MemberBean> filledData(String[] date) {
        List<MemberBean> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            MemberBean sortModel = new MemberBean();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

}
