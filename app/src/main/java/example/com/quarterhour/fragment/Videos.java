package example.com.quarterhour.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.quarterhour.R;

/**
 * @author zhangjunyou
 * @date 2018/7/20
 * @description
 * @Copyright 版权所有, 未经授权不得转载其他 .
 */

public class Videos extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videos,container,false);
        return view;
    }
}
