package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.KhachDAO;
import sp23cp18103.nhom2.finedining.database.ThongTinHoaDonDAO;
import sp23cp18103.nhom2.finedining.model.HoaDon;
import sp23cp18103.nhom2.finedining.model.KhachHang;
import sp23cp18103.nhom2.finedining.model.ThongTinThongKeDoanhThu;
import sp23cp18103.nhom2.finedining.model.ThongTinThongKeKhachHang;
import sp23cp18103.nhom2.finedining.utils.ColorHelper;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.KeyboardHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
* Thống kê số lượng khách
* */
public class ThongKeKhachFragment extends Fragment {
    TextView tvTongKhachHang;
    TextInputLayout input_thongKeKhach_ngayBatDau, input_thongKeKhach_ngayKetThuc, input_lyt_nam_ThongKeKhach;
    EditText edNgayBatDau, edNgayKetThuc, input_nam_ThongKeKhach;
    Button btnThongKeKhach1, btnThongKeKhach2;
    Context context;
    KhachDAO khachDAO;
    BarChart barChart;
    List<KhachDAO> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke_khach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        anhXa(view);
        khoiTao();
        barChart();
        thongKe();
        chonNgayBatDau();
        chonNgayKetThuc();
        thongKeSoLuong();
        thongKeSoLuongTheoNam();
    }
    private void thongKeSoLuongTheoNam() {
        btnThongKeKhach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()){
                    return;
                }
                barChart();
                KeyboardHelper.hideSoftKeyboard((Activity) context);
            }
            private boolean validate() {
                if (input_nam_ThongKeKhach.getText().toString().trim().equals("")){
                    Toast.makeText(context, "Hãy nhập năm \n Ví dụ: 2023", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });
    }

    private void khoiTao() {
        khachDAO = new KhachDAO(context);
        input_nam_ThongKeKhach.setText(DateHelper.getYearSQLNow());
        edNgayKetThuc.setText(khachDAO.getNgayNhoNhat(PreferencesHelper.getId(context)));
        edNgayBatDau.setText(khachDAO.getNgayLonNhat(PreferencesHelper.getId(context)));

    }

    private void chonNgayBatDau() {
        input_thongKeKhach_ngayBatDau.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showDatePickerVietnam(context, edNgayBatDau);
            }
        });

    }
    private void chonNgayKetThuc() {
        input_thongKeKhach_ngayKetThuc.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showDatePickerVietnam(context, edNgayKetThuc);
            }
        });
    }
    public void anhXa(View view) {
        input_thongKeKhach_ngayBatDau = view.findViewById(R.id.input_thongKeKhach_ngayBatDau);
        input_thongKeKhach_ngayKetThuc = view.findViewById(R.id.input_thongKeKhach_ngayKetThuc);
        edNgayBatDau = view.findViewById(R.id.edNgayBatDau);
        edNgayKetThuc = view.findViewById(R.id.edNgayKetThuc);
        tvTongKhachHang = view.findViewById(R.id.tvTongKhachHang);
        input_lyt_nam_ThongKeKhach = view.findViewById(R.id.input_lyt_nam_ThongKeKhach);
        input_nam_ThongKeKhach = view.findViewById(R.id.input_nam_ThongKeKhach);
        btnThongKeKhach1 = view.findViewById(R.id.btnThongKeKhach1);
        btnThongKeKhach2 = view.findViewById(R.id.btnThongKeKhach2);
        barChart = view.findViewById(R.id.barChart_thongke_khach);
    }
    private void thongKeSoLuong() {
        btnThongKeKhach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               thongKe();
            }
        });
    }

    private ArrayList<BarEntry> getMonthlyRevenue(String nam) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        // Lấy danh sách các đối tượng ThongTinThongKeKhachHang cho năm được chọn
        List<ThongTinThongKeKhachHang> list = khachDAO.getsoLuongTheoNam(PreferencesHelper.getId(context),nam);

        // duyệt qua 12 tháng
        for (int i = 1; i <= 12; i++) {
            //  tạo giá trị doanh thu của tháng đó
            float soluong = 0;
            for (ThongTinThongKeKhachHang tt : list) {
                // Kiểm tra nếu như tháng của đối tượng hiện tại bằng với tháng đang so sánh
                if (tt.getMonth().equals(String.format("%02d", i))) {
                    soluong = tt.getSoLuong();
                    tt.setSoLuong(tt.getSoLuong());

                    // Dừng
                    break;
                }

            }
            // add BarEntry mới vào danh sách với số tháng là  x và dso luong  y
            barEntries.add(new BarEntry(i, soluong));

        }
        return barEntries;
    }
    public void barChart(){
        String nam = input_nam_ThongKeKhach.getText().toString().trim();
        BarDataSet barDataSet1 = new BarDataSet(getMonthlyRevenue(nam), "Month");
        barDataSet1.setColor(ColorHelper.getPositiveColor(context));
        BarData barData=new BarData(barDataSet1);
        barChart.setData(barData);
        barDataSet1.setValueTextSize(16F);
        barChart.getDescription().setEnabled(true);
        String[] moth = new String[]{"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", ""};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(moth));
        xAxis.setCenterAxisLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(0.25F);
        xAxis.setTextSize(14F);
        xAxis.setGranularityEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(8);
        barChart.invalidate();
    }
    void thongKe(){
        String tungay = DateHelper.getDateSql(edNgayBatDau.getText().toString().trim());
        String denngay = DateHelper.getDateSql(edNgayKetThuc.getText().toString().trim());
        tvTongKhachHang.setText(""+khachDAO.getSoLuongKhachHang(PreferencesHelper.getId(context),tungay,denngay));

    }
}