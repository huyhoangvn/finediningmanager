package sp23cp18103.nhom2.finedining.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.BanViewPagerAdapter;

/*
* Để quản lý viewpager của Bàn
* */
public class BanCollectionFragment extends Fragment {
private TabLayout mTabLayout;
private ViewPager2 mViewPager;
BanViewPagerAdapter banViewPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ban_collection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout=view.findViewById(R.id.tablyt_ban);
        mViewPager=view.findViewById(R.id.vp_ban);
        banViewPagerAdapter=new BanViewPagerAdapter(this);
        mViewPager.setAdapter(banViewPagerAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Loại bàn");
                    break;
                case 1:
                    tab.setText("Bàn");
                    break;
            }
        }).attach();
    }
}