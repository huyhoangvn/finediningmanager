package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.HoaDonDAO;
import sp23cp18103.nhom2.finedining.database.ThongTinHoaDonDAO;
import sp23cp18103.nhom2.finedining.model.HoaDon;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
* Thống kê tổng tiền hóa đơn
* */
public class ThongKeDoanhThuFragment extends Fragment {
    TextView tvTongDoanhThu;
    TextInputLayout inputLytNgayBatDau,inputLytNgayKetThuc,inputLytNam;
    TextInputEditText edNgayBatDau,edNgayKetThuc,edNam;
    AppCompatButton btnThongKeDoanhThu,btnThongKeDoanhThuTheoNam;
    Context context ;
    ThongTinHoaDonDAO thongTinHoaDonDAO;
    HoaDon hd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context =getContext();
        anhXa(view);
        khoiTao();
        chonNgayBatDau();
        chonNgayKetThuc();
        thongKeTongDoanhThu();
    }

    private void khoiTao() {
         thongTinHoaDonDAO = new ThongTinHoaDonDAO(context);
    }

    private void thongKeTongDoanhThu() {
        btnThongKeDoanhThu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String tuNgay = DateHelper.getDateSql(edNgayBatDau.getText().toString());
                String denNgay = DateHelper.getDateSql(edNgayKetThuc.getText().toString());
                tvTongDoanhThu.setText(""+thongTinHoaDonDAO.getDoanhThu(PreferencesHelper.getId(context),tuNgay,denNgay));
            }
        });
    }

    private void chonNgayKetThuc() {
        inputLytNgayKetThuc.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showDatePickerVietnam(context,edNgayKetThuc);
            }
        });
    }

    private void chonNgayBatDau() {
        inputLytNgayBatDau.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showDatePickerVietnam(context,edNgayBatDau);
            }
        });

    }

    public void anhXa(View view){
        tvTongDoanhThu = view.findViewById(R.id.tvTongDoanhThu);
        inputLytNgayBatDau = view.findViewById(R.id.input_lyt_thongKeDoanhThu_ngayBatDau);
        inputLytNgayKetThuc = view.findViewById(R.id.input_lyt_thongKeDoanhThu_ngayKetThuc);
        edNgayBatDau = view.findViewById(R.id.input_thongKeDoanhThu_ngayBatDau);
        edNgayKetThuc = view.findViewById(R.id.input_thongKeDoanhThu_ngayKetThuc);
        inputLytNam = view.findViewById(R.id.input_lyt_nam_thongKeDoanhThu);
        edNam = view.findViewById(R.id.input_nam_thongKeDoanhThu);
        btnThongKeDoanhThu = view.findViewById(R.id.btnThongKeTongDoanhThu);
        btnThongKeDoanhThuTheoNam = view.findViewById(R.id.btnThongKeDoanhThuNam);

    }
}