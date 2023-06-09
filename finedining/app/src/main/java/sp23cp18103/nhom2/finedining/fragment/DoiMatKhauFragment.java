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
import sp23cp18103.nhom2.finedining.utils.ValueHelper;

/*
* Đổi mật khẩu nhân viên hiện tại
* */
public class DoiMatKhauFragment extends Fragment {
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
        edMatKhauCu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMatKhauCu.setError(null);
            }
        });
        edMatKhauMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMatKhauMoi.setError(null);
            }
        });
        edXacNhanMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputXacNhanMatKhau.setError(null);
            }
        });
        btnLuu.setOnClickListener(v -> {
            clearError();

            if(!validateForm()){
                return;
            }

            doiMatKhau();
        });
    }

    /*
    * Xóa thông báo lỗi
    * */
    private void clearError() {
        inputMatKhauCu.setError(null);
        inputMatKhauMoi.setError(null);
        inputXacNhanMatKhau.setError(null);
    }

    /*
    * Xác nhận tính hợp lệ của giá trị người dùng nhập
    * */
    private boolean validateForm() {
        if(edMatKhauCu.getText().toString().trim().equals("")){
            inputMatKhauCu.setError("Chưa nhập mật khẩu cũ");
            inputMatKhauCu.requestFocus();
            return false;
        }
        if(edMatKhauMoi.getText().toString().trim().equals("")){
            inputMatKhauMoi.setError("Chưa nhập mật khẩu mới");
            inputMatKhauMoi.requestFocus();
            return false;
        }
        if(edMatKhauCu.getText().toString().trim().length() > ValueHelper.MAX_INPUT_LOGIN){
            inputMatKhauCu.setError("Nhập tối đa " + ValueHelper.MAX_INPUT_NAME + " kí tự");
            inputMatKhauCu.requestFocus();
            return false;
        }
        if(edMatKhauMoi.getText().toString().trim().length() > ValueHelper.MAX_INPUT_LOGIN){
            inputMatKhauMoi.setError("Nhập tối đa " + ValueHelper.MAX_INPUT_NAME + " kí tự");
            inputMatKhauMoi.requestFocus();
            return false;
        }
        if(edXacNhanMatKhau.getText().toString().trim().equals("")){
            inputXacNhanMatKhau.setError("Chưa nhập xác nhận mật khẩu mới");
            inputXacNhanMatKhau.requestFocus();
            return false;
        }
        if(!edXacNhanMatKhau.getText().toString().trim().equals(edMatKhauMoi.getText().toString().trim())){
            inputXacNhanMatKhau.setError("Xác nhận mật khẩu sai");
            inputXacNhanMatKhau.requestFocus();
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
            Toast.makeText(context, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
            inputMatKhauCu.setError("Nhập sai mật khẩu");
            inputMatKhauCu.requestFocus();
        }
    }
}