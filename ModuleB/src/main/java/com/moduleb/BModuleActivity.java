package com.moduleb;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.basemodule.ARouterManager;
import com.basemodule.BaseActivity;

/**
 * Created by wangwei on 2018/4/17.
 */
@Route(path = ARouterManager.BModuleActivity)
public class BModuleActivity extends BaseActivity {
    @Autowired
    public String name;

    @Autowired(name = "age")
    int age;


    TextView txt;


    @Override
    protected int getLayoutId() {
        return R.layout.b_module_layout;
    }

    @Override
    protected void initView() {
        txt = findViewById(R.id.txt);
        //  String name = getIntent().getStringExtra("name");
        ARouter.getInstance().inject(this);

        txt.setText("name:" + name + ",age:" + age);


        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
