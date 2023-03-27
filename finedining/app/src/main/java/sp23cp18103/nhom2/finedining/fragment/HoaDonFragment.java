package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.IEditListener;
import sp23cp18103.nhom2.finedining.Interface.IEditListenerHoaDon;
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
    private FragmentManager fmNhanVien;
    RecyclerView rcv_HoaDon;
    List<ThongTinHoaDon> thongTinHoaDonList;
    HoaDonAdapter hoaDonAdapter;
    TextInputEditText edTimKiem;
    CheckBox chkFragmentHoaDon;
    TextInputLayout inputTimKiemHoaDon;

    Context context;
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
        inputTimKiemHoaDon = view.findViewById(R.id.inputTimKiemHoaDon);
        edTimKiem = view.findViewById(R.id.input_01_hoaDon_timHoaDon);
        chkFragmentHoaDon = view.findViewById(R.id.chkFragmentHoaDon);
        thongTinHoaDonDAO = new ThongTinHoaDonDAO(getContext());
        context = getContext();
//        timKiemHoaDon();


        thongTinHoaDonList =  thongTinHoaDonDAO.getTrangThaiHoaDon(PreferencesHelper.getId(context),(chkFragmentHoaDon.isChecked())?1:2,edTimKiem.getText().toString());
        hoaDonAdapter = new HoaDonAdapter(getContext(), thongTinHoaDonList, new IEditListenerHoaDon() {
            @Override
            public void showEditFragment(int maHD) {
                fmNhanVien.beginTransaction()
                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                        .add(R.id.frame_collection_hoadon, new SuaHoaDonFragment(maHD))
                        .addToBackStack(null)
                        .commit();
                FloatingActionButton ftbtnThemHoaDon = getActivity().findViewById(R.id.fbtn_them_hoaDon_collection);
                ftbtnThemHoaDon.hide();
            }
        });
        rcv_HoaDon.setAdapter(hoaDonAdapter);
        khoiTaoFragmentManager();
        chkFragmentHoaDon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hienThiDanhSachHoaDon();
            }
        });



    }

    public void timKiemHoaDon(){
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                hienThiHoaDon();
            }
        });
        edTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH){
                    hienThiHoaDon();
                    return true;
                }
                return false;
            }
        });
        inputTimKiemHoaDon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiHoaDon();
            }
        });
    }
    public void hienThiHoaDon(){
        int maNV = PreferencesHelper.getId(getContext());
        int trangThai = (chkFragmentHoaDon.isChecked())?1:0;
        String timKiem = edTimKiem.getText().toString().trim();
        if(timKiem.isEmpty()){
            thongTinHoaDonList = thongTinHoaDonDAO.getTrangThaiHoaDon(maNV, trangThai, "");
            hoaDonAdapter = new HoaDonAdapter(getContext(), thongTinHoaDonList,null);
            rcv_HoaDon.setAdapter(hoaDonAdapter);
            return ;
        }else{
            thongTinHoaDonList = thongTinHoaDonDAO.getTrangThaiHoaDon(maNV, trangThai, timKiem);
            hoaDonAdapter = new HoaDonAdapter(getActivity(), thongTinHoaDonList,null);
            rcv_HoaDon.setAdapter(hoaDonAdapter);
        }
    }
    private void khoiTaoFragmentManager() {
        fmNhanVien = getParentFragmentManager();
        /*
         * Tải lại toàn bộ danh sách nhân viên khi quay về từ thêm mới, sửa
         * dùng cho fragment manager
         * */
        fmNhanVien.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
               hienThiDanhSachHoaDon();
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    public void hienThiDanhSachHoaDon(){
        thongTinHoaDonList.clear();
        thongTinHoaDonList.addAll( thongTinHoaDonDAO.getTrangThaiHoaDon(PreferencesHelper.getId(context),
                (chkFragmentHoaDon.isChecked())?1:2,
                edTimKiem.getText().toString().trim()));
        hoaDonAdapter.notifyDataSetChanged();
    }
}