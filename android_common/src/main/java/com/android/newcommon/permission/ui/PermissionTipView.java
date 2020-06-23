package com.android.newcommon.permission.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.R;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * User: newSalton@outlook.com
 * Date: 2019/3/21 10:46
 * ModifyTime: 10:46
 * Description: 不强制样式，要求对应的业务使用方提供自行的UI布局，并且要求布局中包含tvTitle,tvSubmit,mRecyclerView,ivClose等id
 */
public class PermissionTipView extends RelativeLayout implements View.OnClickListener {
    private TextView tvTitle, tvSubmit;
    private RecyclerView mRecyclerView;
    private ImageView ivClose;
    private ConcretePermissionAdapter mAdapter;
    private  Context mContext;

    public PermissionTipView(Context context) {
        super(context);
        mContext=context;
        initViewAndData();
    }

    public PermissionTipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;

        initViewAndData();
    }

    public PermissionTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewAndData();
    }

    public void initViewAndData() {
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this);
        tvTitle = findViewById(R.id.tvTitle);
        tvSubmit = findViewById(R.id.tvSubmit);
        mRecyclerView = findViewById(R.id.lvConcretePermission);
        ivClose = findViewById(R.id.ivClose);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ConcretePermissionAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        ivClose.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    public int getLayoutId() {
        return R.layout.view_stub_permission_tip;
    }



    public View f(int viewId) {
        return findViewById(viewId);
    }

    public void updateTitle(String tip) {
        if (tvTitle != null) {
            tvTitle.setText(tip);
        }
    }

    public void updatePermissionList(List<PermissionBean> mLists) {
        if (mAdapter != null) {
            mAdapter.addAll(mLists);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ivClose) {
            onCloseClick();
        } else if (v == tvSubmit) {
            onSubmitClick();
        }
    }

    public void onSubmitClick() {

    }

    public void onCloseClick() {

    }

}
