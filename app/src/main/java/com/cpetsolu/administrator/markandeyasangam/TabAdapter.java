package com.cpetsolu.administrator.markandeyasangam;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cpetsolu.administrator.markandeyasangam.fragment.HomeFragment;
import com.cpetsolu.administrator.markandeyasangam.fragment.MemberFragment;
import com.cpetsolu.administrator.markandeyasangam.fragment.ProfileFragemnt;

public class TabAdapter extends FragmentStatePagerAdapter {

    String[] tab=new String[]{"DashBoard","Members"};
    Integer tabnumber=2;

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tab[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                HomeFragment homeFragment1 = new HomeFragment();
                return homeFragment1;
            case 1:
                MemberFragment memberFragment = new MemberFragment();
                return memberFragment;

            case 2:
                ProfileFragemnt profileFragemnt = new ProfileFragemnt();
                return profileFragemnt;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabnumber;
    }
}
