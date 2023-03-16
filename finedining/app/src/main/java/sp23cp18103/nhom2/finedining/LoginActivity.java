package sp23cp18103.nhom2.finedining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import sp23cp18103.nhom2.finedining.database.DBHelper;
import sp23cp18103.nhom2.finedining.database.DatBanDAO;
import sp23cp18103.nhom2.finedining.database.NhaHangDAO;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.NhaHang;
import sp23cp18103.nhom2.finedining.model.NhanVien;

/*
 * Đăng nhập
 * Và chuyển qua màn hình chính nếu đã đăng nhập rồi
 * */
public class LoginActivity extends AppCompatActivity {
    Button btn_dangnhap;
    TextInputLayout input_taikhoan_dangnhap,input_matkhau_dangnhap;

    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
//        insertTest();
        login();

    }
    private void login() {
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
//                nhanVienDAO = new NhanVienDAO(LoginActivity.this);
//                String taikhoan = input_taikhoan_dangnhap.getEditText().getText().toString();
//                String matkhau = input_matkhau_dangnhap.getEditText().getText().toString();
//                if (nhanVienDAO.checkDangnhap(taikhoan,matkhau)){
//                    test đăng nhập mở insert thêm inten vô đây
//                }else {
//                    Toast.makeText(LoginActivity.this, "Thông tin tài khoản mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }


    //test đăng nhập gọi lên main
    void insertTest(){
        NhaHangDAO nhaHangDAO = new NhaHangDAO(this);
        NhaHang nhaHang = new NhaHang();
        nhaHang.setTenNH("Nhà Hàng a");
        nhaHang.setDiaChi("hanoi");
        nhaHang.setHinh("null");

        if (nhaHangDAO.checknhahang("Nhà Hàng a")){
            Toast.makeText(this, "Đã có nhà hàng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nhaHangDAO.insertNhaHang(nhaHang) > 0){
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "k Thành công", Toast.LENGTH_SHORT).show();
        }


        nhanVienDAO = new NhanVienDAO(this);
        NhanVien nv = new NhanVien();
        nv.setMaNH(1);
        nv.setTenNV("admin");
        nv.setGioiTinh(1);
        nv.setNgaySinh("2000");
        nv.setSdt("03633891");
        nv.setPhanQuyen(1);
        nv.setTrangThai(1);
        nv.setTaiKhoan("admin");
        nv.setMatKhau("admin");


        if (nhanVienDAO.checkTaikhoan("admin")){
            Toast.makeText(this, "Đã có Tài Khoản admin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nhanVienDAO.insertNhanVien(nv) > 0){
            Toast.makeText(this, "Nhân viên Thành công", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "k Thành công", Toast.LENGTH_SHORT).show();
        }

    }

    private void anhXa() {
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
        input_taikhoan_dangnhap = findViewById(R.id.input_taikhoan_dangnhap);
        input_matkhau_dangnhap = findViewById(R.id.input_matkhau_dangphap);
    }
}