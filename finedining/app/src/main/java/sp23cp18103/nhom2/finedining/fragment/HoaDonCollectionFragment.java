package sp23cp18103.nhom2.finedining.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sp23cp18103.nhom2.finedining.R;



public class HoaDonCollectionFragment extends Fragment {

    private FragmentManager fragmentManager;
    FloatingActionButton btnThemHoaDon_collection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoa_don_collection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnThemHoaDon_collection = view.findViewById(R.id.fbtn_them_hoaDon_collection);
        fragmentManager = getActivity().getSupportFragmentManager();
        HoaDonFragment hoaDonFragment = new HoaDonFragment();
        fragmentManager.beginTransaction().add(R.id.frame_collection_hoadon,hoaDonFragment).commit();

        btnThemHoaDon_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frame_collection_hoadon,new ThemHoaDonFragment()).commit();
                btnThemHoaDon_collection.hide();
            }
        });
    }
}