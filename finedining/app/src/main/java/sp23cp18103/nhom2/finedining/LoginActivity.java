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
    CheckBox chkRemeber;
    NhanVienDAO nhanVienDAO;

    CustomProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        insertTest();
//        login();
        nhanVienDAO = new NhanVienDAO(this);
        if (nhanVienDAO.checkDangnhap("myadmin","admin")){
            int maNV = nhanVienDAO.getIdNhanVienByTaiKhoan("myadmin","admin");
            int trangthai = nhanVienDAO.getTrangThaiNV(maNV);
            if (trangthai == 0){
                Toast.makeText(LoginActivity.this, "Nhân Viên Nghỉ Làm", Toast.LENGTH_SHORT).show();
                return;
            }
            PreferencesHelper.saveIdSharedPref(LoginActivity.this,maNV);
            // gọi PreferencesHelperđể lưu
            PreferencesHelper.saveSharedPref(LoginActivity.this,"myadmin","admin",chkRemeber.isChecked());
            // fix delay
            loading();
            //chuyển activity
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            // animation chuyển
            overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
        }
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


    //Nhập dữ liệu mẫu để thực hành
    void insertTest() {
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
        if (nhaHangDAO.checknhahang("Fine Dining")){
            return;
        } if (nhaHangDAO.checknhahang("Nha Nam")){
            return;
        }
        nhaHangDAO.insertNhaHang(new NhaHang(1, "Fine Dining", "Hà Nội", "https://chupanhmonan.com/wp-content/uploads/2019/03/ma%CC%82%CC%83u-thie%CC%82%CC%81t-ke%CC%82%CC%81-nha%CC%80-ha%CC%80ng-%C4%91e%CC%A3p.jpg"));

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
                "2002-01-10", "0933765999", 1, 1, "myadmin", "admin", "https://mir-s3-cdn-cf.behance.net/project_modules/disp/10f13510774061.560eadfde5b61.png"));
        nhanVienDAO.insertNhanVien(new NhanVien(2, 1, "Hồ Ngọc Hà", 2,
                "1999-01-10", "0933763999", 0, 1, "hongocha", "hongocha", "https://mir-s3-cdn-cf.behance.net/project_modules/disp/b654a410774061.560eadfd1e2cc.png"));
        nhanVienDAO.insertNhanVien(new NhanVien(3, 2, "Lưu Hữu Phước", 1,
                "2000-03-10", "0933765999", 1, 1, "notadmin", "admin", "https://i.pinimg.com/originals/23/ef/28/23ef28b4176f3c583203eec85f3411a1.png"));
        nhanVienDAO.insertNhanVien(new NhanVien(4, 1, "Nguyễn Huy Hồng", 1,
                "2000-01-10", "0933765999", 1, 1, "isadmin", "isadmin", "https://mir-s3-cdn-cf.behance.net/project_modules/disp/200f2910774061.560eac1cd606c.png"));
        nhanVienDAO.insertNhanVien(new NhanVien(5, 1, "Thùy Minh", 0,
                "2005-051-10", "0933765399", 0, 0, "Hameno", "Hameno", "https://i.pinimg.com/originals/91/de/1e/91de1e48020787761c1906c9fcde86cd.jpg"));
        khachDAO.insert(new KhachHang(1,"vũ",1,"0666","taikhoan","matkhau",null));

        khachDAO.insert(new KhachHang(2,"vũ ngọc",2,"06666","taikhoan1","matkhau1",null));
        hoaDonDAO.insertHoaDon(new HoaDon(1,1,1,4, DateHelper.getDateTimeSQLNow(), DateHelper.getDateTimeSQLNow(),1));

        hoaDonDAO.insertHoaDon(new HoaDon(2,2,1,5, DateHelper.getDateTimeSQLNow(), DateHelper.getDateTimeSQLNow(),1));
        hoaDonDAO.insertHoaDon(new HoaDon(3,2,1,5, DateHelper.getDateTimeSQLNow(), DateHelper.getDateTimeSQLNow(),2));

        loaiMonDAO.insertLoaiMon(new LoaiMon(1,"thịt",1,1));

        monDAO.insertMon(new Mon(1,1,"thịt chó",500,1,"https://i.pinimg.com/originals/23/ef/28/23ef28b4176f3c583203eec85f3411a1.png"));

        monDAO.insertMon(new Mon(2,1,"thịt gà",700,1,null));

        monDAO.insertMon(new Mon(2,1,"thịt Em",700,1,null));

        monDAO.insertMon(new Mon(3,1,"thịt gì đó",200,1,null));



        banDAO.insertban(new Ban(1,1,"A1",1));
        banDAO.insertban(new Ban(2,1,"A2",1));
        banDAO.insertban(new Ban(3,1,"A3",1));
        banDAO.insertban(new Ban(4,1,"A4",1));


        loaiBanDAO.insertloaiban(new LoaiBan(1,"VIP",1,1));

        datBanDAO.insertDatBan(new DatBan(1,1,1));
        datBanDAO.insertDatBan(new DatBan(2,3,1));

        datBanDAO.insertDatBan(new DatBan(5,3,1));

        datMonDAO.insertDatMon(new DatMon(1,1,3, 1));



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