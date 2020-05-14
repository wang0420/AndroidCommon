package com.module.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.module.ui.R;
import com.module.ui.adapter.SearchItemAdapter;

import java.util.ArrayList;

/**
 * Created by wangwei on 2018/10/11.
 */

public class AutoSearchView extends RelativeLayout {
    private EditText et_keyword;
    private RecyclerView mRecyclerView;
    private SearchItemAdapter mAdapter;
    private Context mContext;
    String hintText;
    public ArrayList<String> mDatas = new ArrayList<>();


    private OnItemViewClickListener mOnItemViewClickListener;

    public interface OnItemViewClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemViewClickListener listener) {
        this.mOnItemViewClickListener = listener;
    }

    public AutoSearchView(Context context) {
        this(context, null);
    }

    public AutoSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_layout_view, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchView, 0, 0);
        hintText = typedArray.getString(R.styleable.SearchView_hintText);
        typedArray.recycle();
        initView();
        setListener();
    }


    private void setListener() {
        mAdapter.setOnItemClickLitener(new SearchItemAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int index, String keyWord) {
                if (mOnItemViewClickListener != null) {
                    et_keyword.setText(keyWord);
                    mOnItemViewClickListener.onItemClick(index);
                }
            }
        });
    }

    private void initView() {
        et_keyword = findViewById(R.id.et_keyword);
        mRecyclerView = findViewById(R.id.recycler);
        if (null != hintText && hintText.length() > 0) {
            et_keyword.setHint(hintText);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());    // 设置item动画
        mAdapter = new SearchItemAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        et_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyWord = et_keyword.getText().toString().trim();
                if (mAdapter.getItemCount() > 0) {
                    foldAnimation(mAdapter.getItemCount() * 48, 0);
                    mAdapter.clear();
                } else {
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).contains(keyWord)) {
                            mAdapter.addItem(mDatas.get(i));
                        }
                    }
                    foldAnimation(0, mAdapter.getItemCount() * 48);
                }
                mAdapter.notifyDataSetChanged();


            }
        });

    }


    public void setData(String data) {
        mDatas.add(data);
    }

    public void foldAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 通过这样一个监听事件，我们就可以获取
                 * 到ValueAnimator每一步所产生的值。
                 *
                 * 通过调用getAnimatedValue()获取到每个时间因子所产生的Value。
                 * */

                //获取动画过程中的渐变值
                int animatedValue = (int) animation.getAnimatedValue();
                Log.w("wangwei", "--->" + animatedValue);
                ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
                layoutParams.height = animatedValue;
                mRecyclerView.setLayoutParams(layoutParams);
            }
        });

        if (mAdapter.getItemCount() > 0) {
            animator.setDuration(300);
            animator.start();
        }
    }

}
