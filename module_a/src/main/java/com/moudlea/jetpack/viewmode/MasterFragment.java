package com.moudlea.jetpack.viewmode;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.android.newcommon.base.BaseFragment;

import com.moudlea.R;
import com.moudlea.R2;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;

/**
 * Created by wangwei on 2020/4/29.
 */

public class MasterFragment extends BaseFragment {
    @BindView(R2.id.fragment1)
    TextView fragment1;


    SharedViewModel viewMode;

    @Override
    protected void initView(View view) {
        //注意：这里ViewModelProviders.of(getActivity())这里的参数需要是Activity，而不能是Fragment，否则收不到监听
        viewMode = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        fragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当点击某一个item的时候，更新viewmode中的selected的值
                viewMode.select("fragment1点击后，更新viewmode中的值，fragment2 里面的数据同步更新");
            }
        });
    }

    @Override
    protected void initData() {
        viewMode.getUserLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String users) {
                Log.w("TAG", "====" + users);
            }
        });

    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_master;
    }
}
