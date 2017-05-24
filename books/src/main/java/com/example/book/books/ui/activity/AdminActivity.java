package com.example.book.books.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.ui.fragment.AHomeFragment;
import com.example.book.books.ui.fragment.AManageFragment;
import com.example.book.books.ui.fragment.AMyFragment;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends SBaseActivity {
    private TabLayout mTablayout;
    private ViewPager mVpAdminHomeActivity;
    private List<String> mTitles = new ArrayList<>();
    private Fragment[] mFragments={new AHomeFragment(),new AManageFragment(),new AMyFragment()};
    @Override
    public int setRootView() {
        return R.layout.activity_admin;
    }

    @Override
    public void initView() {
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mVpAdminHomeActivity = (ViewPager) findViewById(R.id.vp_admin_home_activity);
        mVpAdminHomeActivity.setOffscreenPageLimit(2);
    }

    @Override
    public void initDatas() {
        mTitles.add("首页");
        mTitles.add("管理");
        mTitles.add("我的");

        mTablayout.setupWithViewPager(mVpAdminHomeActivity);
        mVpAdminHomeActivity.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });

    }
}
