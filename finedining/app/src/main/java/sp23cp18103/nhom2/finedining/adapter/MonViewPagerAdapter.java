package sp23cp18103.nhom2.finedining.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import sp23cp18103.nhom2.finedining.fragment.LoaiMonFragment;
import sp23cp18103.nhom2.finedining.fragment.MonCollectionFragment;
import sp23cp18103.nhom2.finedining.fragment.MonFragment;

/*
 * ViewPagerAdapter để chuyển đổi qua lại giữa hai tab món và loại món
 * */
public class MonViewPagerAdapter extends FragmentStateAdapter {

    public MonViewPagerAdapter(@NonNull MonCollectionFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return new LoaiMonFragment();
            case 1:return new MonFragment();
            default:return new LoaiMonFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
