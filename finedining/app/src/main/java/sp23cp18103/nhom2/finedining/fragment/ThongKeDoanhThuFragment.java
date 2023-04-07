package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.HoaDonDAO;
import sp23cp18103.nhom2.finedining.database.ThongTinHoaDonDAO;
import sp23cp18103.nhom2.finedining.model.HoaDon;
import sp23cp18103.nhom2.finedining.model.ThongTinThongKeDoanhThu;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Thống kê tổng tiền hóa đơn
 * */
public class ThongKeDoanhThuFragment extends Fragment {
    TextView tvTongDoanhThu;
    TextInputLayout inputLytNgayBatDau, inputLytNgayKetThuc, inputLytNam;
    TextInputEditText edNgayBatDau, edNgayKetThuc, edNam;
    AppCompatButton btnThongKeDoanhThu, btnThongKeDoanhThuTheoNam;
    Context context;
    ThongTinHoaDonDAO thongTinHoaDonDAO;
    HoaDon hd;
    BarChart barChart;
    ArrayList<ThongTinThongKeDoanhThu> thongTinThongKeDoanhThuArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        anhXa(view);
        khoiTao();
        thongKe();
        getChart();
        chonNgayBatDau();
        chonNgayKetThuc();
        thongKeTongDoanhThu();
        thongKeDoanhThuTheoNam();

    }

    private void thongKeDoanhThuTheoNam() {
        btnThongKeDoanhThuTheoNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validate()){
                    return;
                }
                getChart();

            }

            private boolean validate() {
                if (edNam.getText().toString().trim().equals("")){
                    Toast.makeText(context, "Hãy nhập năm \n Ví dụ: 2023", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void khoiTao() {
        thongTinHoaDonDAO = new ThongTinHoaDonDAO(context);
        edNam.setText(DateHelper.getYearSQLNow());
        edNgayBatDau.setText(""+thongTinHoaDonDAO.getNgayNhoNhat(PreferencesHelper.getId(context)));
        edNgayKetThuc.setText(""+thongTinHoaDonDAO.getNgayLonNhat(PreferencesHelper.getId(context)));
    }

    @SuppressLint("SetTextI18n")
    private void thongKeTongDoanhThu() {
        btnThongKeDoanhThu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                thongKe();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void thongKe(){
        String tuNgay = DateHelper.getDateSql(edNgayBatDau.getText().toString());
        String denNgay = DateHelper.getDateSql(edNgayKetThuc.getText().toString());
        tvTongDoanhThu.setText("" + thongTinHoaDonDAO.getDoanhThu(PreferencesHelper.getId(context), tuNgay, denNgay));
    }

    private void chonNgayKetThuc() {
        inputLytNgayKetThuc.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showDatePickerVietnam(context, edNgayKetThuc);
            }
        });
    }

    private void chonNgayBatDau() {
        inputLytNgayBatDau.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showDatePickerVietnam(context, edNgayBatDau);
            }
        });

    }

    public void anhXa(View view) {
        tvTongDoanhThu = view.findViewById(R.id.tvTongDoanhThu);
        inputLytNgayBatDau = view.findViewById(R.id.input_lyt_thongKeDoanhThu_ngayBatDau);
        inputLytNgayKetThuc = view.findViewById(R.id.input_lyt_thongKeDoanhThu_ngayKetThuc);
        edNgayBatDau = view.findViewById(R.id.input_thongKeDoanhThu_ngayBatDau);
        edNgayKetThuc = view.findViewById(R.id.input_thongKeDoanhThu_ngayKetThuc);
        inputLytNam = view.findViewById(R.id.input_lyt_nam_thongKeDoanhThu);
        edNam = view.findViewById(R.id.input_nam_thongKeDoanhThu);
        btnThongKeDoanhThu = view.findViewById(R.id.btnThongKeTongDoanhThu);
        btnThongKeDoanhThuTheoNam = view.findViewById(R.id.btnThongKeDoanhThuNam);
        barChart = view.findViewById(R.id.barChart);
    }

    @SuppressLint("DefaultLocale")
    private ArrayList<BarEntry> getMonthlyRevenue(String year) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        // Lấy danh sách các đối tượng ThongTinThongKeDoanhThu cho năm được chọn
        List<ThongTinThongKeDoanhThu> list = thongTinHoaDonDAO.getDoanhThuTheoNam(PreferencesHelper.getId(context), year);
        // duyệt qua 12 tháng
        for (int i = 1; i <= 12; i++) {
            //  tạo giá trị doanh thu của tháng đó
            float doanhthu = 0;
            // duyệt qua danh sách các đối tượng ThongTinThongKeDoanhThu
            for (ThongTinThongKeDoanhThu tt : list) {
                // Kiểm tra nếu như tháng của đối tượng hiện tại bằng với tháng đang so sánh
                if (tt.getMonth().equals(String.format("%02d", i))) {
                    // Nếu có, lưu giá trị doanh thu vào biến doanhthu
                    doanhthu = tt.getDoanhThu();
                    // Dừng
                    break;
                }
            }
            // add BarEntry mới vào danh sách với số tháng là  x và doanh thu  y
            barEntries.add(new BarEntry(i, doanhthu));
        }
        return barEntries;
    }

    public void getChart(){
        String nam = edNam.getText().toString().trim();
        BarDataSet barDataSet1 = new BarDataSet(getMonthlyRevenue(nam), "Doanh Thu");
        barDataSet1.setColor(Color.RED);

        BarData data = new BarData(barDataSet1);
        barChart.setData(data);

        String[] moth = new String[]{"","T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12", ""};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(moth));
        xAxis.setCenterAxisLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(0.25F);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(8);
        barChart.invalidate();

    }

}