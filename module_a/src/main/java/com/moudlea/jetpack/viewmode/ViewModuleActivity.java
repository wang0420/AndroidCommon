package com.moudlea.jetpack.viewmode;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.basemodule.base.BaseActivity;
import com.basemodule.base.BasePresenter;
import com.moudlea.R;
import com.moudlea.R2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangwei on 2020/4/29.
 */

public class ViewModuleActivity extends BaseActivity {
    @BindView(R2.id.viewmode_text)
    TextView viewmode_text;
    @BindView(R2.id.btn1)
    Button btn1;

    MyViewMode viewMode;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_viewmode;
    }

    @Override
    protected void initViews() {
        //横竖屏切换  重执行   但是ViewMode的数据不会丢失
        Log.w("TAG", "initViews");
        viewMode = ViewModelProviders.of(this).get(MyViewMode.class);
        //LiveData数据可以再通过observe方法进行数据回调的返回，如上代码中的onChanged回调。
        // 所以我们只要在onChange方法中做好数据刷新UI的操作即可。
        viewMode.getUsers().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String users) {
                viewmode_text.setText("viewMode获取到的数据--" + users);
            }
        });
    }

    @OnClick({R2.id.btn1})
    void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.btn1) {
            viewMode.updateData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("TAG", "onDestroy");
        viewMode.onCleared();

    }



    @Override
    protected void initData() {
        //利用ViewMode进行Fragment之间的数据交互
        showFragment(new MasterFragment(), R.id.fragment1, "fragment1");
        showFragment(new DetailFragment(), R.id.fragment2, "fragment2");

    }


    private void showFragment(Fragment fragment, int id, String tag) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(id, fragment, tag);
        trans.commit();
    }
}
