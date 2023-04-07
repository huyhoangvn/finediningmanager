package sp23cp18103.nhom2.finedining.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
* Đổi mật khẩu nhân viên hiện tại
* */
public class DoiMatKhauFragment extends Fragment {
    private static final String TAG = "DebugDoiMatKhau";
    private Context context;
    //Database
    private NhanVienDAO nhanVienDAO;
    //View
    private TextInputLayout inputMatKhauCu;
    private TextInputLayout inputMatKhauMoi;
    private TextInputLayout inputXacNhanMatKhau;
    private EditText edMatKhauCu;
    private EditText edMatKhauMoi;
    private EditText edXacNhanMatKhau;
    private Button btnLuu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        anhXa(view);
        khoiTaoDAO();
        khoiTaoListener();
    }

    /*
    * Ánh xạ các view
    * */
    private void anhXa(View view) {
        inputMatKhauCu = view.findViewById(R.id.input_fDoiMatKhau_matKhauCu);
        inputMatKhauMoi = view.findViewById(R.id.input_fDoiMatKhau_matKhauMoi);
        inputXacNhanMatKhau = view.findViewById(R.id.input_fDoiMatKhau_xacNhanMatKhau);
        edMatKhauCu = view.findViewById(R.id.ed_fDoiMatKhau_matKhauCu);
        edMatKhauMoi = view.findViewById(R.id.ed_fDoiMatKhau_matKhauMoi);
        edXacNhanMatKhau = view.findViewById(R.id.ed_fDoiMatKhau_xacNhanMatKhau);
        btnLuu = view.findViewById(R.id.btn_fDoiMatKhau_luu);
    }

    /*
    * Khởi tạo các lớp DAO
    * */
    private void khoiTaoDAO() {
        nhanVienDAO = new NhanVienDAO(context);
    }

    /*
    * Gán các listener cho các view
    * */
    private void khoiTaoListener() {
        btnLuu.setOnClickListener(v -> {
            clearError();

            if(!validateForm()){
                return;
            }

            doiMatKhau();
        });
        edMatKhauCu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                inputMatKhauCu.setError(null);
            }
        });
        edMatKhauMoi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                inputMatKhauMoi.setError(null);
            }
        });
        edXacNhanMatKhau.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                inputXacNhanMatKhau.setError(null);
            }
        });
    }

    /*
    * Xóa thông báo lỗi
    * */
    private void clearError() {
        inputMatKhauCu.setError(null);
        inputMatKhauMoi.setError(null);
        inputXacNhanMatKhau.setError(null);
        inputMatKhauCu.clearFocus();
        inputMatKhauMoi.clearFocus();
        inputMatKhauMoi.clearFocus();
    }

    /*
    * Xác nhận tính hợp lệ của giá trị người dùng nhập
    * */
    private boolean validateForm() {
        if(edMatKhauCu.getText().toString().trim().equals("")){
            inputMatKhauCu.setError("Chưa nhập mật khẩu cũ");
            return false;
        }
        if(edMatKhauMoi.getText().toString().trim().equals("")){
            inputMatKhauMoi.setError("Chưa nhập mật khẩu mới");
            return false;
        }
        if(edXacNhanMatKhau.getText().toString().trim().equals("")){
            inputXacNhanMatKhau.setError("Chưa nhập xác nhận mật khẩu mới");
            return false;
        }
        if(!edXacNhanMatKhau.getText().toString().trim().equals(edMatKhauMoi.getText().toString().trim())){
            inputXacNhanMatKhau.setError("Xác nhận mật khẩu sai");
            return false;
        }
        return true;
    }

    /*
    * Đổi mật khẩu
    * */
    private void doiMatKhau() {
        if( nhanVienDAO.updateMatKhauNhanvien( PreferencesHelper.getId(context),
                edMatKhauCu.getText().toString().trim(), edMatKhauMoi.getText().toString().trim() ) > 0 ){
            Toast.makeText(context, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
            inputMatKhauCu.setError("Nhập sai mật khẩu");
        } else {
            Toast.makeText(context, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
        }
    }
}