package sp23cp18103.nhom2.finedining.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.Custom.CustomProgressDialog;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.MenuAdapter;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.database.NhaHangDAO;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Hiển thị thônghttps://github.com/huyhoangvn/finediningmanager/pull/55/conflict?name=finedining%252Fapp%252Fsrc%252Fmain%252Fjava%252Fsp23cp18103%252Fnhom2%252Ffinedining%252Ffragment%252FHomeFragment.java&ancestor_oid=92e505c0da8ef683e2bc3df99db5f307e8737749&base_oid=64bced0f52e062f5619bc637b89255ba5c4b1958&head_oid=4b0c60b81f27d0a97dcffc1d0bbe72c1ba9a0e3a tin nhà hàng và sử dụng MapsFragment để hiện địa chỉ
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
    TextView tvTenNhaHang, tvDiaChi;
    MenuAdapter menuAdapter;
    MonDAO monDAO;
//    Button btnShowMap;
    List<Mon> listMon;
    List<Address> addresses;

    CardView cardMap;
    CustomProgressDialog progressDialog;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        cardMap.setVisibility(View.GONE);
        getNameDiachiNH();
        loadAnh();
        evMap();
        evRCV();

    }
    private void evRCV() {
        monDAO = new MonDAO(getContext());

        int maNV = PreferencesHelper.getId(getContext());
        listMon = monDAO.trangThaiLoaiMon(maNV, 1,"");
        menuAdapter = new MenuAdapter(listMon,getContext());
        rcv_menu.setAdapter(menuAdapter);
    }
    private void evMap() {
        //set cứng map
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.map_01_Main_showMap, new MapsFragment(21.040081, 105.747551, "Tòa nhà FPT Polytechnic")).commit();
        cardMap.setVisibility(View.VISIBLE);
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        // có internet
        if (isConnected) {
            // Hiển thị ProgressDialog
            progressDialog = new CustomProgressDialog(getContext());
            progressDialog.show();
            Window window = progressDialog.getWindow();
            if (window == null) {
                return;
            }
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            new Thread(new Runnable() {
                @Override
                public void run() {
//                        Tải bản đồ
                    geoLocate();
//                        Sau khi tải xong, ẩn ProgressDialog
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                }
            }).start();
            // nếu k có internet
        } else {
            Toast.makeText(getContext(), "Kiểm Tra Kết nối mạng và thử lại", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private void geoLocate() {
        NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
        NhaHangDAO nhaHangDAO = new NhaHangDAO(getContext());
        int maNV = PreferencesHelper.getId(getContext());
        int maNH = nhanVienDAO.getMaNH(maNV);
        String location = nhaHangDAO.getDiaChi(maNH);
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(location, 1);
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
    private void loadAnh() {
        NhaHangDAO nhaHangDAO = new NhaHangDAO(getContext());
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/images%2Fma%CC%82%CC%83u-thie%CC%82%CC%81t-ke%CC%82%CC%81-nha%CC%80-ha%CC%80ng-%C4%91e%CC%A3p.jpg?alt=media&token=f88c9bfc-695a-4cfd-82d1-82e38d952231")
                .into(imgnhaHang);
    }
    void getNameDiachiNH() {
        NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
        NhaHangDAO nhaHangDAO = new NhaHangDAO(getContext());
        int maNV = PreferencesHelper.getId(getContext());
        int maNH = nhanVienDAO.getMaNH(maNV);
        String tenNhaHang = nhaHangDAO.getTenNH(maNH);
        String diachi = nhaHangDAO.getDiaChi(maNH);
        tvTenNhaHang.setText(tenNhaHang);
        tvDiaChi.setText("Địa chỉ: " + diachi);
    }

    private void anhXa(View view) {
        rcv_menu = view.findViewById(R.id.rcv_menu);
        imgnhaHang = view.findViewById(R.id.img_nhaHang);
        tvTenNhaHang = view.findViewById(R.id.tv_fragHome_tenNH);
        tvDiaChi = view.findViewById(R.id.tv_diachi);
        cardMap = view.findViewById(R.id.carMap);
    }
}