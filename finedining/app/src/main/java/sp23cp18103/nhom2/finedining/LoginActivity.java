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

import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.database.NhaHangDAO;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
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
//        insertTest();

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
                    // gọi PreferencesHelperđể lưu
                    PreferencesHelper.saveSharedPref(LoginActivity.this,maNV,taikhoan,matkhau,chkRemeber.isChecked());
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


    void loading(){
        // progress dialog custom
        dialog =  new CustomProgressDialog(this);
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    // tắt progress dialog
    protected void onResume() {
        super.onResume();
        if (dialog != null){
            dialog.dismiss();
        }
    }

    // lưu lại toài khoản mật khẩu khi ghi nhớ
    void saveTaiKhoanMatKhau(){
        inputTaikhoanDangnhap.getEditText().setText(PreferencesHelper.getTaiKhoan(this));
        inputMatkhauDangnhap.getEditText().setText(PreferencesHelper.getMatKhau(this));
        chkRemeber.setChecked(PreferencesHelper.getGhiNho(this));
    }

    //test đăng nhập gọi lên main
    void insertTest(){
        NhaHangDAO nhaHangDAO = new NhaHangDAO(this);
        if (nhaHangDAO.checknhahang("Nhà Hàng a")){
            return;
        }
        nhaHangDAO.insertNhaHang(new NhaHang("Nhà Hàng a","hanoi","null"));


        nhanVienDAO = new NhanVienDAO(this);
        if (nhanVienDAO.checkTaikhoan("admin")){
            return;
        }
        nhanVienDAO.insertNhanVien(new NhanVien(1,"admin",1,"2000","03633891",1,1,"admin","admin"));



        LoaiMonDAO loaiMonDAO = new LoaiMonDAO(this);
        if (loaiMonDAO.checkloaimon("Rang")){
            return;
        }
        loaiMonDAO.insertLoaiMon(new LoaiMon("Rang",1,1));


        MonDAO monDAO = new MonDAO(this);
        if (monDAO.checkmon("Gà Rang")){
            return;
        } if (monDAO.checkmon("Hamberger")){
            return;
        } if (monDAO.checkmon("Gà Rang Muối")){
            return;
        } if (monDAO.checkmon("Ếch Rang")){
            return;
        } if (monDAO.checkmon("Ếch Rang Muối")){
            return;
        }
        monDAO.insertMon(new Mon(1,"Gà Rang",1,"null"));
        monDAO.insertMon(new Mon(1,"Hamberger",1,"null"));
        monDAO.insertMon(new Mon(1,"Gà Rang Muối",1,"null"));
        monDAO.insertMon(new Mon(1,"Ếch Rang",1,"null"));
        monDAO.insertMon(new Mon(1,"Ếch Rang Muối",1,"null"));

    }

    private void anhXa() {
        btnDangnhap = findViewById(R.id.btn_dangnhap);
        inputTaikhoanDangnhap = findViewById(R.id.input_taikhoan_dangnhap);
        inputMatkhauDangnhap = findViewById(R.id.input_matkhau_dangphap);
        chkRemeber = findViewById(R.id.chk_Remeber);
    }


}