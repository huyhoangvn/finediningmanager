package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.NhanVienAdapter;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Hiển thị danh sách nhân viên
 * */
public class NhanVienFragment extends Fragment {
    private Context context;
    private NhanVienAdapter adpNhanVien;
    private ArrayList<NhanVien> listNhanVien;
    //Database
    private NhanVienDAO nhanVienDAO;
    //Controller
    private RecyclerView rcvNhanVien;
    private CheckBox chkNhanVienDangLam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nhan_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        anhXa(view);
        khoiTaoDAO();
        khoiTaoRecyclerView();
        khoiTaoListener();
    }

    /*
    * Ánh xạ các view cần dùng
    * */
    private void anhXa(View view) {
        rcvNhanVien = view.findViewById(R.id.rcv_fNhanVien_danhSach);
        chkNhanVienDangLam = view.findViewById(R.id.chk_fNhanVien_nhanVienDangLam);
    }

    /*
    * Khởi tạo các lớp DAO
    * */
    private void khoiTaoDAO() {
        nhanVienDAO = new NhanVienDAO(context);
    }

    /*
    * Khởi tạo recycler view và đổ dữ liệu lên màn hình
    * */
    private void khoiTaoRecyclerView() {
        listNhanVien = (ArrayList<NhanVien>) nhanVienDAO.getAllNhanVien(PreferencesHelper.getId(context),
                (chkNhanVienDangLam.isChecked())?1:0);
        adpNhanVien = new NhanVienAdapter(context, listNhanVien);
        rcvNhanVien.setAdapter(adpNhanVien);
    }

    /*
    * Thêm listener cho checkbox
    * Chọn thì chỉ hiển thị những nhân viên đang đi làm
    * Bỏ chọn thì hiển thị cả những nhân viên đã nghỉ
    * */
    private void khoiTaoListener() {
        chkNhanVienDangLam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listNhanVien.clear();
                listNhanVien.addAll(nhanVienDAO.getAllNhanVien(PreferencesHelper.getId(context),
                        (chkNhanVienDangLam.isChecked())?1:0));
                adpNhanVien.notifyDataSetChanged();
            }
        });
    }
}