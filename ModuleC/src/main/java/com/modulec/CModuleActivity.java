package com.modulec;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.basemodule.ARouterManager;
import com.basemodule.BaseActivity;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterManager.CModuleActivity)
public class CModuleActivity extends BaseActivity {
    TextView txt;

    @Override
    protected int getLayoutId() {
        return R.layout.c_module_layout;
    }

    @Override
    protected void initView() {
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
