package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.Interface.IEditListener;
import sp23cp18103.nhom2.finedining.adapter.NhanVienAdapter;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Hiển thị danh sách nhân viên
 * */
public class NhanVienFragment extends Fragment {
    private Context context;
    //Utils
    private FragmentManager fmNhanVien;
    private NhanVienAdapter adpNhanVien;
    private ArrayList<NhanVien> listNhanVien;
    //Database
    private NhanVienDAO nhanVienDAO;
    //Controller
    private RecyclerView rcvNhanVien;
    private CheckBox chkNhanVienDangLam;
    private TextInputLayout inputTimNhanVien;
    private EditText edTimNhanVien;
    private FloatingActionButton fbtnThemNhanVien;

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
        khoiTaoFragmentManager();
        khoiTaoPhanQuyen();
        khoiTaoCheckboxListener();
        khoiTaoTimKiem();
        handleOnBackPressed();
    }

    /*
    * Ánh xạ các view cần dùng
    * */
    private void anhXa(View view) {
        rcvNhanVien = view.findViewById(R.id.rcv_fNhanVien_danhSach);
        chkNhanVienDangLam = view.findViewById(R.id.chk_fNhanVien_nhanVienDangLam);
        inputTimNhanVien = view.findViewById(R.id.input_fNhanVien_timNhanVien);
        edTimNhanVien = view.findViewById(R.id.ed_fNhanVien_timNhanVien);
        fbtnThemNhanVien = view.getRootView().findViewById(R.id.ftbtn_fNhanVien_them);
    }

    /*
    * Khởi tạo các lớp DAO
    * */
    private void khoiTaoDAO() {
        nhanVienDAO = new NhanVienDAO(context);
    }

    /*
     * Lấy fragment manager từ lớp cha
     * */
    private void khoiTaoFragmentManager() {
        fmNhanVien = getParentFragmentManager();
        /*
         * Tải lại toàn bộ danh sách nhân viên khi quay về từ thêm mới, sửa
         * dùng cho fragment manager
         * */
        fmNhanVien.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                hienThiDanhSachNhanVien();
            }
        });
    }

    /*
    * Khởi tạo recycler view và đổ dữ liệu lên màn hình
    * */
    private void khoiTaoRecyclerView() {
        listNhanVien = (ArrayList<NhanVien>) nhanVienDAO.getAllNhanVien(PreferencesHelper.getId(context),
                (chkNhanVienDangLam.isChecked())?0:1,
                edTimNhanVien.getText().toString().trim());
        adpNhanVien = new NhanVienAdapter(context, listNhanVien, new IEditListener() {
            /*
            * Hiển thị màn hình sửa nhân viên khi ấn vào nút sửa trên cardview
            * */
            @Override
            public void showEditFragment(int maNV) {
                fmNhanVien.beginTransaction()
                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                        .add(R.id.lyt_fNhanVienCollection_fragmentManager, new ThemNhanVienFragment(maNV))
                        .addToBackStack(null)
                        .commit();
                FloatingActionButton ftbtnThemNhanVien = getActivity().findViewById(R.id.ftbtn_fNhanVien_them);
                ftbtnThemNhanVien.hide();
            }
        });
        rcvNhanVien.setAdapter(adpNhanVien);
    }

    /*
    * Hiển thị tính năng hiện nhân viên đã nghỉ
    * */
    private void khoiTaoPhanQuyen() {
        if(nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(context)) == 1){
            chkNhanVienDangLam.setVisibility(View.VISIBLE);
        }
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
    * Tạo listener cho edit text tìm kiếm
    * Hiển thị lại danh sách khi nhập chữ
    * Hiển thị lại danh sách khi ấn tìm kiếm trên search bar hoặc keyboard
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
                return false;
            }
        });
        inputTimNhanVien.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiDanhSachNhanVien();
            }
        });
    }

    /*
    * Tải lại toàn bộ danh sách nhân viên khi tải lại activity
    * dùng cho activity
    * */
    @Override
    public void onResume() {
        hienThiDanhSachNhanVien();
        super.onResume();
    }

    /*
    * Hiển thị danh sách nhân viên
    * */
    @SuppressLint("NotifyDataSetChanged")
    private void hienThiDanhSachNhanVien() {
        listNhanVien.clear();
        listNhanVien.addAll(nhanVienDAO.getAllNhanVien(PreferencesHelper.getId(context),
                (chkNhanVienDangLam.isChecked())?0:1,
                edTimNhanVien.getText().toString().trim()));
        adpNhanVien.notifyDataSetChanged();
    }

    /*
     * Quay về fragment quản lý nhân viên khi ấn nút quay về trên thiết bị
     * Hiển thị lại floating action button
     * */
    private void handleOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    fmNhanVien.popBackStack();
                    if(nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(context)) == 1){
                        fbtnThemNhanVien.show();
                    }
                }
            }
        });
    }
}