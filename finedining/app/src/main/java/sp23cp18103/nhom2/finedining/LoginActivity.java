package sp23cp18103.nhom2.finedining;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


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
import sp23cp18103.nhom2.finedining.model.DatMon;
import sp23cp18103.nhom2.finedining.model.HoaDon;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.model.NhaHang;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.model.LoaiMon;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.NhaHang;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.Custom.CustomProgressDialog;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Đăng nhập
 * Và chuyển qua màn hình chính nếu đã đăng nhập rồi
 * */
public class LoginActivity extends AppCompatActivity{
    Button btnDangnhap;
    TextInputLayout inputTaikhoanDangnhap,inputMatkhauDangnhap;
    CheckBox chkRemeber;
    NhanVienDAO nhanVienDAO;

    CustomProgressDialog dialog ;
    private ProgressDialog prgWait;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        insertTest();
        login();
        saveTaiKhoanMatKhau();
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
                //validate tài mật khẩu
                if (matkhau.isEmpty()){
                    inputMatkhauDangnhap.setError("Vui lòng nhập mật khẩu");
                    return;
                }else {
                    inputMatkhauDangnhap.setError(null);
                }

                //Check tài khoản mật khẩu
                if (nhanVienDAO.checkDangnhap(taikhoan,matkhau)){
                    int maNV = nhanVienDAO.getIdNhanVienByTaiKhoan(taikhoan,matkhau);
                    PreferencesHelper.saveIdSharedPref(LoginActivity.this,maNV);
                    // gọi PreferencesHelperđể lưu
                    PreferencesHelper.saveSharedPref(LoginActivity.this,taikhoan,matkhau,chkRemeber.isChecked());
                    // fix delay
                    loading();
                    //chuyển activity
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    // animation chuyển
                    overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
                }else {
                    Toast.makeText(LoginActivity.this, "Thông tin tài khoản mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //Nhập dữ liệu mẫu để thực hành
    void insertTest() {
        NhaHangDAO nhaHangDAO = new NhaHangDAO(this);
        NhanVienDAO nhanVienDAO = new NhanVienDAO(this);
//        LoaiBanDAO loaiBanDAO = new LoaiBanDAO(this);
//        LoaiMonDAO loaiMonDAO = new LoaiMonDAO(this);
//        BanDAO banDAO = new BanDAO(this);
//        MonDAO monDAO = new MonDAO(this);
//        KhachDAO khachDAO = new KhachDAO(this);
//        HoaDonDAO hoaDonDAO = new HoaDonDAO(this);
//        DatBanDAO datBanDAO = new DatBanDAO(this);
//        DatMonDAO datMonDAO = new DatMonDAO(this);
        //Nhà hàng
        if (nhaHangDAO.checknhahang("Fine Dining")){
            return;
        } if (nhaHangDAO.checknhahang("Nha Nam")){
            return;
        }
        nhaHangDAO.insertNhaHang(new NhaHang(1, "Fine Dining", "Hà Nội", null));
        nhaHangDAO.insertNhaHang(new NhaHang(2, "Nha Nam", "TP Hồ Chí Minh", null));
        //Nhân viên
        if (nhanVienDAO.checkTaikhoan("myadmin")){
            return;
        }if (nhanVienDAO.checkTaikhoan("hongocha")){
            return;
        }if (nhanVienDAO.checkTaikhoan("notadmin")){
            return;
        }if (nhanVienDAO.checkTaikhoan("isadmin")){
            return;
        }if (nhanVienDAO.checkTaikhoan("Hameno")){
            return;
        }
        nhanVienDAO.insertNhanVien(new NhanVien(1, 1, "Nguyễn Huy Hoàng", 1,
                "2002-01-10", "0933765999", 1, 1, "myadmin", "admin", "https://imgur.com/jxfDB4O"));
        nhanVienDAO.insertNhanVien(new NhanVien(2, 1, "Hồ Ngọc Hà", 2,
                "1999-01-10", "0933763999", 0, 1, "hongocha", "hongocha", null));
        nhanVienDAO.insertNhanVien(new NhanVien(3, 2, "Lưu Hữu Phước", 1,
                "2000-03-10", "0933765999", 1, 1, "notadmin", "admin", null));
        nhanVienDAO.insertNhanVien(new NhanVien(4, 1, "Nguyễn Huy Hồng", 1,
                "2000-01-10", "0933765999", 1, 1, "isadmin", "isadmin", null));
        nhanVienDAO.insertNhanVien(new NhanVien(5, 1, "Thùy Minh", 0,
                "2005-051-10", "0933765399", 0, 0, "Hameno", "Hameno", null));

    }

    void loading () {
        // progress dialog custom
        dialog = new CustomProgressDialog(this);
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
    }

}