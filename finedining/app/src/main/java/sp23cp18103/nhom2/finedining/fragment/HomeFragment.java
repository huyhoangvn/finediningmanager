package sp23cp18103.nhom2.finedining.fragment;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.MenuAdapter;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Hiển thị thông tin nhà hàng và sử dụng MapsFragment để hiện địa chỉ
 * Hiện danh sách món còn sử dụng (sơ qua)
 * */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private FragmentManager fragmentManager;
    RecyclerView rcv_menu;
    ImageView imgnhaHang;
    MenuAdapter menuAdapter;
    MonDAO monDAO;
    List<Mon> listMon;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        loadAnh();
        evMap();
        evRCV();

    }



    private void evRCV() {
        monDAO = new MonDAO(getContext());
        int maNV = PreferencesHelper.getId(getContext());
        listMon = monDAO.timKiem(maNV, "");
        menuAdapter = new MenuAdapter(listMon,getContext());
        rcv_menu.setAdapter(menuAdapter);
    }

    private void evMap() {
        //init map
        fragmentManager = getActivity().getSupportFragmentManager();
        //lat: 21.039009000383587, long: 105.74663689630138
        fragmentManager.beginTransaction().replace(R.id.map_01_Main_showMap, new MapsFragment(21.040081, 105.747551, "Tòa nhà FPT Polytechnic, P. Trịnh Văn Bô, Xuân Phương, Nam Từ Liêm, Hà Nội")).commit();
        //init Listener
        geoLocate();
    }


    private void geoLocate() {
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName("", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(list.size() > 0){
            Address address = list.get(0);
            fragmentManager.beginTransaction()
                    .replace(R.id.map_01_Main_showMap, new MapsFragment(
                            address.getLatitude(), address.getLongitude(), address.getFeatureName()))
                    .commit();
        }
    }

    private void loadAnh(){
        Glide.with(this)
                .load("https://chupanhmonan.com/wp-content/uploads/2019/03/ma%CC%82%CC%83u-thie%CC%82%CC%81t-ke%CC%82%CC%81-nha%CC%80-ha%CC%80ng-%C4%91e%CC%A3p.jpg")
                .into(imgnhaHang);
    }
    private void anhXa(View view) {
        rcv_menu = view.findViewById(R.id.rcv_menu);
        imgnhaHang = view.findViewById(R.id.img_nhaHang);
    }

    void checkInternet(){
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            Toast.makeText(getContext(), "Connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Not connected", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}