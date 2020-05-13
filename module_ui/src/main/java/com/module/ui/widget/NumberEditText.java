package com.module.ui.widget;

/**
 * Created by wangwei.
 * 2016/1/19.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.module.ui.R;


public class NumberEditText extends LinearLayout {

    private EditText numberEditText;
    private ImageButton addBtn;
    private ImageButton reduceBtn;

    public NumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberEditText(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.item_published_grida,
                this);
        init_widget();
        addListener();
    }

    public void init_widget() {
        numberEditText = (EditText) findViewById(R.id.number_edittext_text);
        addBtn = (ImageButton) findViewById(R.id.number_edittext_add);
        reduceBtn = (ImageButton) findViewById(R.id.number_edittext_reduce);
        numberEditText.setText("5");
    }

    public void addListener() {
        addBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                int num = Integer.valueOf(numberEditText.getText().toString());
                num++;
                numberEditText.setText(Integer.toString(num));
            }
        });

        reduceBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(numberEditText.getText().toString());
                if (num != 0) {
                    num--;
                    numberEditText.setText(Integer.toString(num));
                }
            }
        });
    }

    public String getData() {
        return numberEditText.getText().toString();
    }

}
