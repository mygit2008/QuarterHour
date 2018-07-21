package example.com.quarterhour.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import example.com.quarterhour.R;
import example.com.quarterhour.adapter.TitleFragmentPagerAdapter;

/**
 * @author zhangjunyou
 * @date 2018/7/20
 * @description
 * @Copyright 版权所有, 未经授权不得转载其他 .
 */

public class Referrals extends Fragment {
    private View view;
    private TabLayout tab;
    private ViewPager viewpager;
    private String[] titles = new String[]{"热门", "关注"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.referrals, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tab = (TabLayout) view.findViewById(R.id.tab);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HotFragment());
        fragments.add(new FocusFragment());
        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getFragmentManager(), fragments, titles);
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
    }

}
