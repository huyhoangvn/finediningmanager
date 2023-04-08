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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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

/*
 * Đăng nhập
 * Và chuyển qua màn hình chính nếu đã đăng nhập rồi
 * */
public class LoginActivity extends AppCompatActivity{
    Button btnDangnhap;
    TextInputLayout inputTaikhoanDangnhap,inputMatkhauDangnhap;
    TextInputEditText inputEdMatKhau;
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
                String taikhoan = inputTaikhoanDangnhap.getEditText().getText().toString();
                String matkhau = inputMatkhauDangnhap.getEditText().getText().toString();

                //validate tài khoản
                if (taikhoan.isEmpty()){
                    inputTaikhoanDangnhap.setError("Vui lòng nhập tài khoản");
                    return;
                }else{
                    inputTaikhoanDangnhap.setError(null);
                }
                //validate mật khẩu
                if (matkhau.isEmpty()){
                    inputMatkhauDangnhap.setError("Vui lòng nhập mật khẩu");
                    return;
                }else {
                    inputMatkhauDangnhap.setError(null);
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
                            Toast.makeText(LoginActivity.this, "Nhân Viên Nghỉ Làm", Toast.LENGTH_SHORT).show();
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
        inputEdMatKhau.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                inputMatkhauDangnhap.setError(null);
            }
        });
    }

    /*
    * Nhập dữ liệu trước
    * */
//    void insertTest() {
//        NhaHangDAO nhaHangDAO = new NhaHangDAO(this);
//        NhanVienDAO nhanVienDAO = new NhanVienDAO(this);
//
//        //Nhà hàng
//        if (nhaHangDAO.checknhahang("Ratatouille")){
//            return;
//        } if (nhaHangDAO.checknhahang("Golden Ramsey")){
//            return;
//        }
//        //Nhân viên
//        nhaHangDAO.insertNhaHang(new NhaHang(1, "Ratatouille", "Hà Nội", ));
//
//        nhaHangDAO.insertNhaHang(new NhaHang(2, "Golden Ramsey", "TP Hồ Chí Minh", "https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/nhahang2.jpg?alt=media&token=06dd917b-5504-415d-9834-f9c5974eb10c"));
//
//        nhanVienDAO.insertNhanVien(new NhanVien(1, 1, "Nguyễn Huy Hoàng", 1,
//                "2002-01-10", "0933765999", 1, 1, "myadmin", "admin", "https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/anhnhanvien%20(1).jpg?alt=media&token=b6e07e36-e6ec-4ecb-8230-5d71f6cb7d05"));
//        nhanVienDAO.insertNhanVien(new NhanVien(2, 1, "Hồ Ngọc Hà", 2,
//                "1999-01-10", "0933763999", 0, 1, "hongocha", "hongocha", "https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/anhnhanvien%20(1).png?alt=media&token=0b952486-4509-49b3-a9e7-6ae91e1ad2d2"));
//        nhanVienDAO.insertNhanVien(new NhanVien(3, 2, "Trấn Thành", 1,
//                "2000-03-10", "0933765999", 1, 1, "notadmin", "admin", "https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/anhnhanvien%20(2).png?alt=media&token=b22c78f2-78e8-4196-ab16-c200e2e600a9"));
//        nhanVienDAO.insertNhanVien(new NhanVien(4, 1, "Phúc Du", 1,
//                "2000-01-10", "0933765999", 1, 1, "isadmin", "isadmin", "https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/anhnhanvien%20(3).png?alt=media&token=7e62e316-5634-48b0-a402-fafdbe8af77b"));
//        nhanVienDAO.insertNhanVien(new NhanVien(5, 1, "Thùy Minh", 0,
//                "2005-051-10", "0933765399", 0, 0, "Hameno", "Hameno", "https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/anhnhanvien%20(4).png?alt=media&token=9f9e4e14-6813-433c-a1f0-3da30fee1748"));
//    }


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