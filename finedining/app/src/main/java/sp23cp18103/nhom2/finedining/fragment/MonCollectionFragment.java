package sp23cp18103.nhom2.finedining.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.MonViewPagerAdapter;

/*
 * Để quản lý viewpager của Món
 * */
public class MonCollectionFragment extends Fragment {
    TabLayout tablytMon;
    ViewPager2 vpMon;
    MonViewPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mon_collection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tablytMon = view.findViewById(R.id.tablytMon);
        vpMon = view.findViewById(R.id.vpMon);
        adapter = new MonViewPagerAdapter(this);
        vpMon.setAdapter(adapter);
        TabLayoutMediator mediator = new TabLayoutMediator(tablytMon, vpMon, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0){
                    tab.setText("Loại món");
                }else{
                    tab.setText("Món");
                }
            }
        });mediator.attach();
    }
}