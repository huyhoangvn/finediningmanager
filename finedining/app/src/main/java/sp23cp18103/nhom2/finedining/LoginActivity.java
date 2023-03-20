package sp23cp18103.nhom2.finedining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import sp23cp18103.nhom2.finedining.fragment.ThemNhanVienFragment;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.DatMon;
import sp23cp18103.nhom2.finedining.model.HoaDon;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.model.NhaHang;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

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

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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


    //Nhập dữ liệu mẫu để thực hành
    void insertTest(){
        NhaHangDAO nhaHangDAO = new NhaHangDAO(this);
        NhanVienDAO nhanVienDAO = new NhanVienDAO(this);
        LoaiBanDAO loaiBanDAO = new LoaiBanDAO(this);
        LoaiMonDAO loaiMonDAO = new LoaiMonDAO(this);
        BanDAO banDAO = new BanDAO(this);
        MonDAO monDAO = new MonDAO(this);
        KhachDAO khachDAO = new KhachDAO(this);
        HoaDonDAO hoaDonDAO = new HoaDonDAO(this);
        DatBanDAO datBanDAO = new DatBanDAO(this);
        DatMonDAO datMonDAO = new DatMonDAO(this);
        //Nhà hàng
        nhaHangDAO.insertNhaHang(new NhaHang(1, "Fine Dining", "Hà Nội", null));
        nhaHangDAO.insertNhaHang(new NhaHang(2, "Nha Nam", "TP Hồ Chí Minh", null));
        //Nhân viên
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
        //Loại bàn
        loaiBanDAO.insertloaiban(new LoaiBan());
    }

    private void anhXa() {
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
        input_taikhoan_dangnhap = findViewById(R.id.input_taikhoan_dangnhap);
        input_matkhau_dangnhap = findViewById(R.id.input_matkhau_dangphap);
    }
}