package sp23cp18103.nhom2.finedining.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.HoaDonAdapter;
import sp23cp18103.nhom2.finedining.database.ThongTinHoaDonDAO;
import sp23cp18103.nhom2.finedining.model.HoaDon;
import sp23cp18103.nhom2.finedining.model.NhaHang;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.model.ThongTinHoaDon;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
* Hiển thị danh sách hóa đơn
* */
public class HoaDonFragment extends Fragment {
    ThongTinHoaDonDAO thongTinHoaDonDAO;
    FloatingActionButton btnThemHoaDon;
    RecyclerView rcv_HoaDon;
    List<ThongTinHoaDon> thongTinHoaDonList;
    HoaDonAdapter hoaDonAdapter;
    TextInputEditText edTimKiem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_HoaDon = view.findViewById(R.id.rcv_hoaDon);
        thongTinHoaDonDAO = new ThongTinHoaDonDAO(getContext());
        thongTinHoaDonList =  thongTinHoaDonDAO.getThongTinHoaDon(PreferencesHelper.getId(getContext()));
        hoaDonAdapter = new HoaDonAdapter(getContext(),thongTinHoaDonList);
        rcv_HoaDon.setAdapter(hoaDonAdapter);
        edTimKiem = view.findViewById(R.id.input_01_hoaDon_timHoaDon);


        edTimKiem.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int maNV = PreferencesHelper.getId(getContext());
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    String timKiem = edTimKiem.getText().toString().trim();
                    if (timKiem.isEmpty()) {
                        thongTinHoaDonList =  thongTinHoaDonDAO.getThongTinHoaDon(PreferencesHelper.getId(getContext()));
                        hoaDonAdapter = new HoaDonAdapter(getContext(),thongTinHoaDonList);
                        rcv_HoaDon.setAdapter(hoaDonAdapter);
                        return false;
                    } else {
                        thongTinHoaDonList.clear();
                        thongTinHoaDonList.addAll(thongTinHoaDonDAO.getTimKiemThongTinHoaDon(maNV,timKiem));
                        hoaDonAdapter.notifyDataSetChanged();
                    }
                    return true;
                }
                return false;
            }
        });


    }
}