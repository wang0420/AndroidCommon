package com.modulec;

import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.basemodule.ARouterManager;
import com.basemodule.base.BaseActivity;
import com.basemodule.base.BasePresenter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterManager.CModuleActivity)
public class CModuleActivity extends BaseActivity {
    TextView txt;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.c_module_layout;
    }


    @Override
    protected void initData() {

    }


    @Override
    protected void initViews() {
        txt = findViewById(R.id.txt);


        WheelView wheelView = findViewById(R.id.wheelview);
        wheelView.setCyclic(false);
        wheelView.setCurrentItem(2);
        wheelView.setDividerType(WheelView.DividerType.WRAP);
        wheelView.setLineSpacingMultiplier(2f);
        final List<String> mOptionsItems = new ArrayList<>();
        mOptionsItems.add("item0");
        mOptionsItems.add("item123456765432");
        mOptionsItems.add("item2");
        wheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(CModuleActivity.this, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
