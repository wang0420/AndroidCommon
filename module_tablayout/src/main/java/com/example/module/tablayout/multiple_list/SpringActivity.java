package com.example.module.tablayout.multiple_list;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.module.tablayout.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kyleduo.switchbutton.SwitchButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by wangwei on 2020/5/12.
 */

public class SpringActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        initMenu();
    }

    private void initMenu() {
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("AlphaIn", "ScaleIn", "SlideInBottom", "SlideInLeft", "SlideInRight", "Custom");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Toast.makeText(SpringActivity.this,""+position,Toast.LENGTH_SHORT).show();

            }
        });

        SwitchButton switchButton = (SwitchButton) findViewById(R.id.switch_button);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                    Toast.makeText(SpringActivity.this,""+isChecked,Toast.LENGTH_SHORT).show();

            }
        });

    }

}
