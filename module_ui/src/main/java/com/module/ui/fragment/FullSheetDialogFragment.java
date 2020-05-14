package com.module.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.module.ui.R;
import com.module.ui.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 描述：
 * BottomSheetDialogFragment的简单使用
 *
 * @author luzhaowei
 * @email 2497727771@qq.com
 * @time 2017/8/17 14:30
 */
public class FullSheetDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.bottom_sheet_dialog_fragment, null);

        List<String> mList;
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("item " + i);
        }
        //创建recyclerView
        RecyclerView recyclerView =  view.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(mList, getContext());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View item, int position) {
                Toast.makeText(getContext(), "item " + position, Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(view);
        // mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return bottomSheetDialog;

    }

    @Override
    public void onStart() {
        super.onStart();
        // mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
    }

    /**
     * 点击布局里的ImageView，触发的点击事件
     *
     * @param v
     */
    public void doclick(View v) {
        //  mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}
