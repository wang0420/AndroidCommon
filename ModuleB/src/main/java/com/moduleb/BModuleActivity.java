package com.moduleb;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.basemodule.ARouterManager;
import com.basemodule.base.BaseActivity;
import com.basemodule.base.BasePresenter;
import com.basemodule.bean.Author;

/**
 * Created by wangwei on 2018/4/17.
 */
@Route(path = ARouterManager.BModuleActivity)
public class BModuleActivity extends BaseActivity {
//    @Autowired
//    Author author;
//

    TextView txt;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_module_layout);
        initView();
    }

    protected void initView() {
        txt = findViewById(R.id.txt);
       // ARouter.getInstance().inject(this);

      //  Author author = (Author) getIntent().getSerializableExtra("author");
      //  txt.setText("name:" + author.getName());

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
