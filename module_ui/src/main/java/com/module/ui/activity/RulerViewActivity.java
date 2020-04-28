package com.module.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.module.ui.R;
import com.module.ui.widget.RulerView;


/**
 * Created by wangwei on 2018/10/17.
 */

public class RulerViewActivity extends AppCompatActivity {
    private RulerView rulerView;
    EditText value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ruler_view);

        rulerView = (RulerView) findViewById(R.id.rulerView);
        value = findViewById(R.id.edt);

        rulerView.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {

            }
        });
    }

    public void computeScroll(View view) {
        String t = value.getText().toString();
        if (null == t || "".equals(t)) {
            return;
        }
        rulerView.computeScrollTo(Float.parseFloat(t));
    }
}
