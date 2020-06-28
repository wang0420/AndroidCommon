package com.moudlea.jetpack.viewmode;

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

public class DetailFragment extends BaseFragment {
    @BindView(R2.id.fragment2)
    TextView fragment2;
    @Override
    protected int layoutResID() {
        return R.layout.fragment_detail;

    }
    @Override
    protected void initView(View view) {
        //绑定ViewMode的selected的值，当有更新时通知DetailFragment
        SharedViewModel viewMode = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewMode.getUserLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String users) {
                fragment2.setText("fragment2==\n" + users);
            }
        });


    }




    @Override
    protected void initData() {

    }


}
