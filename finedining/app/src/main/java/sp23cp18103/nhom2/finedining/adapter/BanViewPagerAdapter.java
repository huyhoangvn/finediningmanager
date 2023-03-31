package sp23cp18103.nhom2.finedining.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import sp23cp18103.nhom2.finedining.fragment.BanCollectionFragment;
import sp23cp18103.nhom2.finedining.fragment.BanFragment;
import sp23cp18103.nhom2.finedining.fragment.LoaiBanFragment;

/*
 * ViewPagerAdapter để chuyển đổi qua lại giữa hai tab bàn và loại bàn
 * */
public class BanViewPagerAdapter extends FragmentStateAdapter {

    public BanViewPagerAdapter(@NonNull BanCollectionFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LoaiBanFragment();
            case 1:
                return new BanFragment();
            default:
                return new LoaiBanFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
