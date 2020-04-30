package com.moudlea.jetpackStudy.navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moudlea.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class FragmentFour extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("Fragment", "FragmentFour onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.w("Fragment", "FragmentFour onCreateView");
        return inflater.inflate(R.layout.fragment_four, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w("Fragment", "FragmentFour onViewCreate");
        //返回
        view.findViewById(R.id.btn_go1).setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        //跳 1
        view.findViewById(R.id.btn_go2).setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.go_fragment2));


    }
}
