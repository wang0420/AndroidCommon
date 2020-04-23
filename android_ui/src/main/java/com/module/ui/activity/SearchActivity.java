package com.module.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.module.ui.R;
import com.module.ui.widget.AutoSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwei on 2018/10/11.
 */

public class SearchActivity extends AppCompatActivity {
    private String arr[] = {"ahj", "jjk", "bnnja", "bdhkhek2j", "mqkmsklwma", "njnjsnjna", "ah2222j", "2222jjk", "b0kswwijnnja"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        initWheelView();
        AutoSearchView search_view = findViewById(R.id.search_view);
        for (int i = 0; i < arr.length; i++) {
            search_view.setData(arr[i]);
        }
        search_view.setOnItemClickListener(new AutoSearchView.OnItemViewClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.w("wangwei", "---->" + position);
                Toast.makeText(SearchActivity.this, "-----" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initWheelView() {

        WheelView wheelView = findViewById(R.id.wheelview);
        wheelView.setCyclic(false);
        wheelView.setCurrentItem(2);
        wheelView.setTextSize(12);
        wheelView.setTextColorOut(getResources().getColor(R.color.blue));
        wheelView.setTextColorCenter(getResources().getColor(R.color.red1));
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
                Toast.makeText(SearchActivity.this, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
            }
        });
    }
}