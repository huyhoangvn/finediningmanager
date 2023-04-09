package sp23cp18103.nhom2.finedining;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import sp23cp18103.nhom2.finedining.database.BanDAO;
import sp23cp18103.nhom2.finedining.database.DBHelper;
import sp23cp18103.nhom2.finedining.database.DatBanDAO;
import sp23cp18103.nhom2.finedining.database.DatMonDAO;
import sp23cp18103.nhom2.finedining.database.HoaDonDAO;
import sp23cp18103.nhom2.finedining.database.KhachDAO;
import sp23cp18103.nhom2.finedining.database.LoaiBanDAO;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.database.NhaHangDAO;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;

import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.DatBan;
import sp23cp18103.nhom2.finedining.model.DatMon;
import sp23cp18103.nhom2.finedining.model.HoaDon;
import sp23cp18103.nhom2.finedining.model.KhachHang;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.model.NhaHang;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.model.LoaiMon;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.NhaHang;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.Custom.CustomProgressDialog;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;
import sp23cp18103.nhom2.finedining.utils.ValueHelper;

/*
 * Đăng nhập
 * Và chuyển qua màn hình chính nếu đã đăng nhập rồi
 * */
public class LoginActivity extends AppCompatActivity{
    Button btnDangnhap;
    TextInputLayout inputTaikhoanDangnhap,inputMatkhauDangnhap;
    TextInputEditText inputEdMatKhau;
    TextInputEditText inputEdTaiKhoan;
    CheckBox chkRemeber;
    NhanVienDAO nhanVienDAO;

    CustomProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
//        insertTest();
        saveTaiKhoanMatKhau();
        login();
        hideErros();
        saveTaiKhoanMatKhau();
//        nhanVienDAO = new NhanVienDAO(this);
//        if (nhanVienDAO.checkDangnhap("myadmin","admin")){
//            int maNV = nhanVienDAO.getIdNhanVienByTaiKhoan("myadmin","admin");
//            int trangthai = nhanVienDAO.getTrangThaiNV(maNV);
//            if (trangthai == 0){
//                Toast.makeText(LoginActivity.this, "Nhân Viên Nghỉ Làm", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            PreferencesHelper.saveIdSharedPref(LoginActivity.this,maNV);
//            // gọi PreferencesHelperđể lưu
//            PreferencesHelper.saveSharedPref(LoginActivity.this,"myadmin","admin",chkRemeber.isChecked());
//            // fix delay
//            loading();
//            //chuyển activity
//            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//            // animation chuyển
//            overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
//        }
//        saveTaiKhoanMatKhau();
    }
    
    private void login() {
        nhanVienDAO = new NhanVienDAO(LoginActivity.this);
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taikhoan = inputTaikhoanDangnhap.getEditText().getText().toString().trim();
                String matkhau = inputMatkhauDangnhap.getEditText().getText().toString().trim();
                //Clear Error cũ
                inputTaikhoanDangnhap.setError(null);
                inputMatkhauDangnhap.setError(null);
                //validate tài khoản
                if (taikhoan.isEmpty()){
                    inputTaikhoanDangnhap.setError("Vui lòng nhập tài khoản");
                    return;
                }
                //validate mật khẩu
                if (matkhau.isEmpty()){
                    inputMatkhauDangnhap.setError("Vui lòng nhập mật khẩu");
                    return;
                }
                if(taikhoan.length() < ValueHelper.MIN_INPUT_LOGIN || taikhoan.length() > ValueHelper.MAX_INPUT_LOGIN){
                    inputTaikhoanDangnhap.setError("Nhập tối thiểu " + ValueHelper.MIN_INPUT_LOGIN
                            + " và tối đa " + ValueHelper.MAX_INPUT_LOGIN + " kí tự");
                    return;
                }
                if(matkhau.length() < 6 || matkhau.length() > 25){
                    inputMatkhauDangnhap.setError("Nhập tối thiểu " + ValueHelper.MIN_INPUT_LOGIN
                            + " và tối đa " + ValueHelper.MAX_INPUT_LOGIN + " kí tự");
                    return;
                }
                // check mạng
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                // nếu có mạng
                if (isConnected) {
                    //Check tài khoản mật khẩu
                    if (nhanVienDAO.checkDangnhap(taikhoan,matkhau)){
                        int maNV = nhanVienDAO.getIdNhanVienByTaiKhoan(taikhoan,matkhau);
                        int trangthai = nhanVienDAO.getTrangThaiNV(maNV);
                        if (trangthai == 0){
                            Toast.makeText(LoginActivity.this, "Nhân viên đã nghỉ Làm", Toast.LENGTH_SHORT).show();
                         return;
                        }
                        PreferencesHelper.saveIdSharedPref(LoginActivity.this,maNV);
                        // gọi PreferencesHelperđể lưu
                        PreferencesHelper.saveSharedPref(LoginActivity.this,taikhoan,matkhau,chkRemeber.isChecked());
                        // fix delay
                        loading();
                        //chuyển activity
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        finish();
                        // animation chuyển
                        overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
                    }else {
                        Toast.makeText(LoginActivity.this, "Thông tin tài khoản mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Kiếm Tra kết nối internet và thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void hideErros(){
        inputEdMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMatkhauDangnhap.setError(null);
            }
        });
        inputEdTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTaikhoanDangnhap.setError(null);
            }
        });
    }


    void loading () {
        // progress dialog custom
        dialog = new CustomProgressDialog(this);
        if (!isFinishing()) {
            dialog.show();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }



    // tắt progress dialog
    protected void onResume () {
        super.onResume();
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    // lưu lại toài khoản mật khẩu khi ghi nhớ
    void saveTaiKhoanMatKhau () {
        inputTaikhoanDangnhap.getEditText().setText(PreferencesHelper.getTaiKhoan(this));
        inputMatkhauDangnhap.getEditText().setText(PreferencesHelper.getMatKhau(this));
        chkRemeber.setChecked(PreferencesHelper.getGhiNho(this));
    }

    private void anhXa () {
        btnDangnhap = findViewById(R.id.btn_dangnhap);
        inputTaikhoanDangnhap = findViewById(R.id.input_taikhoan_dangnhap);
        inputMatkhauDangnhap = findViewById(R.id.input_matkhau_dangphap);
        chkRemeber = findViewById(R.id.chk_Remeber);
        inputEdMatKhau = findViewById(R.id.inputEd_matkhau);
        inputEdTaiKhoan = findViewById(R.id.inputEd_taiKhoan);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferencesHelper.clearId(this);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}