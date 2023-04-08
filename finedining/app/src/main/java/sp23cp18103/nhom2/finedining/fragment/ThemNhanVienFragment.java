package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.GalleryHelper;
import sp23cp18103.nhom2.finedining.utils.ImageHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
* Người dùng có vai trò quản lý có thể thêm tài khoản
* Có thể dùng để sửa nhân viên nếu bỏ tài khoản và mật khẩu
* */
public class ThemNhanVienFragment extends Fragment {
    private Context context;
    //Utils
    private FragmentManager fmNhanVien;
    private GalleryHelper galleryHelper;
    //Database
    private NhanVienDAO nhanVienDAO;
    //Controller
    private TextView tvTieuDe;
    private ImageButton imgbtnThemAnh;
    private TextInputLayout inputTaiKhoan;
    private TextInputLayout inputMatKhau;
    private TextInputLayout inputTenNV;
    private TextInputLayout inputSdt;
    private TextInputLayout inputNgaySinh;
    private EditText edTaiKhoan;
    private EditText edMatKhau;
    private EditText edTenNV;
    private EditText edSdt;
    private EditText edNgaySinh;
    private RadioButton rdoNam;
    private RadioButton rdoNu;
    private RadioButton rdoKhac;
    private LinearLayout lytTrangThai;
    private CheckBox chkTrangThai;
    private Button btnLuu;
    private Button btnHuy;
    //variables
    private final int maNV;//Lưu trữ mã nhân viên đang sửa
//    private String avatarUrl;//Lưu trữ hình ảnh avatar hiện tại đang hiển thị

    public ThemNhanVienFragment(int maNV) {
        this.maNV = maNV;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_them_nhan_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();

        anhXa(view);
        khoiTaoDAO();
        khoiTaoUtils();

        khoiTaoListener();
        //Hiển thị tùy theo người dùng muốn thêm hoặc sửa nhân viên
        //Cho sửa nhân viên nếu mã nhân viên hợp lệ được truyền vào (maNV != 0)
        if(maNV == 0){
            hienThiThemNhanVien();
        } else {
            hienThiSuaNhanVien();
        }
    }

    /*
    * Ánh xạ các view
    * */
    private void anhXa(View view) {
        tvTieuDe = view.findViewById(R.id.tv_fThemNhanVien_title);
        imgbtnThemAnh = view.findViewById(R.id.imgbtn_fragThemNhanVien_themAnh);
        inputTaiKhoan = view.findViewById(R.id.input_fThemNhanVien_taiKhoan);
        inputMatKhau = view.findViewById(R.id.input_fThemNhanVien_matKhau);
        inputTenNV = view.findViewById(R.id.input_fThemNhanVien_tenNV);
        inputSdt = view.findViewById(R.id.input_fThemNhanVien_sdt);
        inputNgaySinh = view.findViewById(R.id.input_fThemNhanVien_ngaySinh);
        edTaiKhoan = view.findViewById(R.id.ed_fThemNhanVien_taiKhoan);
        edMatKhau = view.findViewById(R.id.ed_fThemNhanVien_matKhau);
        edTenNV = view.findViewById(R.id.ed_fThemNhanVien_tenNV);
        edSdt = view.findViewById(R.id.ed_fThemNhanVien_sdt);
        edNgaySinh = view.findViewById(R.id.ed_fThemNhanVien_ngaySinh);
        rdoNam = view.findViewById(R.id.rdo_fThemNhanVien_nam);
        rdoNu = view.findViewById(R.id.rdo_fThemNhanVien_nu);
        rdoKhac = view.findViewById(R.id.rdo_fThemNhanVien_khac);
        lytTrangThai = view.findViewById(R.id.lyt_fThemNhanVien_trangThai);
        chkTrangThai = view.findViewById(R.id.chk_fThemNhanVien_trangThai);
        btnLuu = view.findViewById(R.id.btn_fThemNhanVien_luu);
        btnHuy = view.findViewById(R.id.btn_fThemNhanVien_huy);
    }

    /*
    * Khởi tạo lớp DAO
    * */
    private void khoiTaoDAO() {
        nhanVienDAO = new NhanVienDAO(context);
    }

    /*
    * Khởi tạo các lớp utils
    * */
    private void khoiTaoUtils() {
        fmNhanVien = getParentFragmentManager();
        galleryHelper = new GalleryHelper(context);
    }

    /*
    * Khởi tạo listener cho các view
    * */
    private void khoiTaoListener() {
        //Nút quay về
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fmNhanVien.popBackStack();
                if(nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(context)) == 1){
                    FloatingActionButton ftbtnThemNhanVien = getActivity().findViewById(R.id.ftbtn_fNhanVien_them);
                    ftbtnThemNhanVien.show();
                }
            }
        });
        //Nút sửa ngày sinh
        inputNgaySinh.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showDatePickerVietnam(context, edNgaySinh);
            }
        });
        //Nút sửa hình ảnh
        imgbtnThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryHelper.getImageFromGallery(imgbtnThemAnh);
            }
        });
        //Clear Error khi focus
        edTaiKhoan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    inputTaiKhoan.setError(null);
            }
        });
        edMatKhau.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    inputMatKhau.setError(null);
            }
        });
        edNgaySinh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    inputNgaySinh.setError(null);
            }
        });
        edSdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    inputSdt.setError(null);
            }
        });
        edTenNV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    inputTenNV.setError(null);
            }
        });
    }

    /*
    * Hiển thị layout dành riêng cho thêm nhân viên
    * */
    private void hienThiThemNhanVien() {
        tvTieuDe.setText("Thêm nhân viên mới");
        //Ẩn trạng thái vì thêm mặc định là nhân viên phục vụ
        lytTrangThai.setVisibility(View.GONE);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();

                if(!validateForm()){
                    return;
                }

                themNhanVien();
            }
        });
    }

    /*
     * Hiển thị layout dành riêng cho sửa thông tin nhân viên
     * */
    private void hienThiSuaNhanVien() {
        tvTieuDe.setText("Sửa thông tin nhân viên");
        //Ẩn tài khoản mật khẩu
        inputTaiKhoan.setVisibility(View.GONE);
        inputMatKhau.setVisibility(View.GONE);
        if(nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(context))!=1){
            lytTrangThai.setVisibility(View.GONE);
        }

        fillThongTinNhanVien();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();

                if(!validateForm()){
                    return;
                }

                suaNhanVien();
            }
        });
    }

    /*
    * Xóa lỗi khi ấn nút lưu
    * */
    private void clearError() {
        inputTaiKhoan.setError(null);
        inputMatKhau.setError(null);
        inputTenNV.setError(null);
        inputSdt.setError(null);
        inputNgaySinh.setError(null);
        inputTaiKhoan.clearFocus();
        inputMatKhau.clearFocus();
        inputTenNV.clearFocus();
        inputSdt.clearFocus();
        inputNgaySinh.clearFocus();
    }

    /*
    * Kiểm tra tính hợp lệ của các trường thông tin nhập vào
    * */
    private boolean validateForm() {
        //Cho thêm nhân viên
        if(maNV == 0){
            if(edTaiKhoan.getText().toString().trim().equals("")){
                inputTaiKhoan.setError("Chưa nhập tài khoản");
                return false;
            }
            if(edMatKhau.getText().toString().trim().equals("")){
                inputMatKhau.setError("Chưa nhập mật khẩu");
                return false;
            }
        }
        //Cho cả thêm và sửa nhân viên
        if(edTenNV.getText().toString().trim().equals("")){
            inputTenNV.setError("Chưa nhập tên");
            return false;
        }
        if(edSdt.getText().toString().trim().equals("")){
            inputSdt.setError("Chưa nhập số điện thoại");
            return false;
        }
        if(edSdt.getText().toString().trim().length() != 10){
            inputSdt.setError("Số điện thoại không hợp lệ");
            return false;
        }
        if(edNgaySinh.getText().toString().trim().equals("")){
            inputNgaySinh.setError("Chưa nhập ngày sinh");
            return false;
        }
        /*
        * Kiểm tra xem ngày có đúng định dạng tiếng việt hay không (VD: 20-01-2022)
        * */
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(edNgaySinh.getText().toString().trim());
            assert date != null;
            if (!edNgaySinh.getText().toString().trim().equals(sdf.format(date))) {
                inputNgaySinh.setError("Định dạng ngày sai");
                return false;
            }
        } catch (ParseException ex) {
            inputNgaySinh.setError("Định dạng ngày sai");
            return false;
        }
        return true;
    }

    /*
    * Thêm nhân viên
    * */
    private void themNhanVien() {
        //Kiểm tra xem tài khoản đã tồn tại chưa
        NhanVien nhanVien = getThongTinTuForm();
        if(nhanVienDAO.checkTaikhoan(nhanVien.getTaiKhoan())){
            inputTaiKhoan.setError("Tài khoản đã tồn tại");
            return;
        }
        //Thêm nhân viên nếu tài khoản hợp lệ
        if(nhanVienDAO.insertNhanVien(nhanVien) != -1){
            Toast.makeText(context, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Thêm nhân viên thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * Sửa nhân viên
    * */
    private void suaNhanVien() {
        if(nhanVienDAO.updateNhanVien(getThongTinTuForm()) > 0){
            Toast.makeText(context, "Sửa nhân viên thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sửa nhân viên thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * Lấy thông tin nhân viên hợp lệ từ form
    * */
    private NhanVien getThongTinTuForm() {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(maNV);
        nhanVien.setTaiKhoan(edTaiKhoan.getText().toString().trim());
        nhanVien.setMatKhau(edMatKhau.getText().toString().trim());
        nhanVien.setMaNH(nhanVienDAO.getMaNH(PreferencesHelper.getId(context)));//Lấy mã nhà hàng nhân viên đang thêm, sửa
        nhanVien.setTenNV(edTenNV.getText().toString().trim());
        nhanVien.setSdt(edSdt.getText().toString().trim());
        nhanVien.setNgaySinh(DateHelper.getDateSql(edNgaySinh.getText().toString().trim()));//Chuyển sang định dạng SQL
        nhanVien.setTrangThai((chkTrangThai.isChecked())?1:0);
        nhanVien.setHinh(nhanVienDAO.getHinh(maNV));
        String currentImageUrl = galleryHelper.getCurrentImageUrl();
        if(currentImageUrl != null){
            nhanVien.setHinh(currentImageUrl);
        }

        if(rdoNam.isChecked()){
            nhanVien.setGioiTinh(1);
        }
        if(rdoNu.isChecked()){
            nhanVien.setGioiTinh(2);
        }
        else if (rdoKhac.isChecked()){
            nhanVien.setGioiTinh(0);
        }
        if(maNV == 0){//Thêm nhân viên
            nhanVien.setPhanQuyen(0);//Nhân viên phục vụ
        } else {
            nhanVien.setPhanQuyen(nhanVienDAO.getPhanQuyen(maNV));//Trường hợp sửa nhân viên
        }
        return nhanVien;
    }

    /*
    * Hiển thị thông tin nhân viên đang cần sửa lên trên form
    * */
    private void fillThongTinNhanVien() {
        NhanVien nhanVien = nhanVienDAO.getNhanVien(maNV);
        ImageHelper.loadAvatar(context, imgbtnThemAnh, nhanVien.getHinh());
        edTenNV.setText(nhanVien.getTenNV());
        edNgaySinh.setText(DateHelper.getDateVietnam(nhanVien.getNgaySinh()));//Chuyển sang định dạng tiếng việt
        edSdt.setText(nhanVien.getSdt());
        if(nhanVien.getGioiTinh() == 1){
            rdoNam.setChecked(true);
        } else if (nhanVien.getGioiTinh() == 2) {
            rdoNu.setChecked(true);
        } else {
            rdoKhac.setChecked(true);
        }
        chkTrangThai.setChecked(nhanVien.getTrangThai() == 1);
    }
}