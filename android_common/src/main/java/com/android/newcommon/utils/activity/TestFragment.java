package com.android.newcommon.utils.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.common.R;

import androidx.fragment.app.Fragment;

/**
 * @author wangwei
 * @date 2020/9/14.
 */
public class TestFragment extends Fragment {
    private Activity mActivity;

    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_test_jump, container, false);
        Button view = root.findViewById(R.id.btn_hah);
        view.setText("fragment 里面跳转");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZaActivity.class);
                ZAActivityHelper.startActivityForResult(TestFragment.this, intent, 100, new IRequestCallback() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        Log.w("TAG", "---" + requestCode + "resultCode--" + resultCode);
                    }
                });
            }
        });
        return root;
    }

}



