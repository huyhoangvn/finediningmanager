package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

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
    private TextInputLayout inputTimNhanVien;
    private EditText edTimNhanVien;

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
        khoiTaoCheckboxListener();
        khoiTaoTimKiem();
    }

    /*
    * Ánh xạ các view cần dùng
    * */
    private void anhXa(View view) {
        rcvNhanVien = view.findViewById(R.id.rcv_fNhanVien_danhSach);
        chkNhanVienDangLam = view.findViewById(R.id.chk_fNhanVien_nhanVienDangLam);
        inputTimNhanVien = view.findViewById(R.id.input_fNhanVien_timNhanVien);
        edTimNhanVien = view.findViewById(R.id.ed_fNhanVien_timNhanVien);
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
                (chkNhanVienDangLam.isChecked())?1:0,
                edTimNhanVien.getText().toString().trim());
        adpNhanVien = new NhanVienAdapter(context, listNhanVien);
        rcvNhanVien.setAdapter(adpNhanVien);
    }

    /*
    * Thêm listener cho checkbox
    * Chọn thì chỉ hiển thị những nhân viên đang đi làm
    * Bỏ chọn thì hiển thị cả những nhân viên đã nghỉ
    * */
    private void khoiTaoCheckboxListener() {
        chkNhanVienDangLam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hienThiDanhSachNhanVien();
            }
        });
    }

    /*
    *
    * */
    private void khoiTaoTimKiem() {
        edTimNhanVien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                hienThiDanhSachNhanVien();
            }
        });
        edTimNhanVien.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH){
                    hienThiDanhSachNhanVien();
                    return true;
                }
                return false;            }
        });
        inputTimNhanVien.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiDanhSachNhanVien();
            }
        });
    }

    /*
    * Tải lại toàn bộ danh sách nhân viên khi quay về từ thêm mới
    * */
    @Override
    public void onResume() {
        hienThiDanhSachNhanVien();
        super.onResume();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void hienThiDanhSachNhanVien() {
        listNhanVien.clear();
        listNhanVien.addAll(nhanVienDAO.getAllNhanVien(PreferencesHelper.getId(context),
                (chkNhanVienDangLam.isChecked())?1:0,
                edTimNhanVien.getText().toString().trim()));
        adpNhanVien.notifyDataSetChanged();
    }

}