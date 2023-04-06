package sp23cp18103.nhom2.finedining.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.MonBanChayAdapter;
import sp23cp18103.nhom2.finedining.database.ThongTinMonDAO;
import sp23cp18103.nhom2.finedining.model.ThongTinMon;
import sp23cp18103.nhom2.finedining.utils.DateHelper;

/*
 * Hiển thị danh sách món ăn bán chạy
 * */
public class ThongKeMonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke_mon, container, false);
    }
    List<ThongTinMon> list;
    RecyclerView rcvMonHot;
    MonBanChayAdapter adapter;
    TextInputLayout inputLayoutThang,inputLayoutNam;
    AppCompatButton btnTim;
    RadioButton rdoTkDoanhThu,rdoTkSoLuong;
    RadioGroup groupThongKe;
    ThongTinMonDAO tinMonDAO;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        inputLayoutNam.getEditText().setText(DateHelper.getYearSQLNow());
        khoiTaoistenergroupThongKe();
        evTim();
    }
    private void khoiTaoistenergroupThongKe() {
        rdoTkSoLuong.setChecked(true);
        evRcvTkMonSoLuong();
        groupThongKe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rdoTkSoLuong.isChecked()){
                    evRcvTkMonSoLuong();
                }
                if (rdoTkDoanhThu.isChecked()){
                    evRcvTkMonDoanhthu();
                }

            }
        });
    }
    private void evTim() {
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdoTkSoLuong.isChecked()) {
                    thongKeSoLuong(v);
                } else if (rdoTkDoanhThu.isChecked()) {
                    thongKeDoanhThu(v);
                }
            }
        });
    }
void thongKeSoLuong(View view){
    tinMonDAO = new ThongTinMonDAO(getContext());
    String thang = inputLayoutThang.getEditText().getText().toString();
    String nam = inputLayoutNam.getEditText().getText().toString();
    // Kiểm tra giá trị nhập vào của chuỗi 'thang'
    if (!TextUtils.isEmpty(thang)) {
        int thangSo = Integer.parseInt(thang);
        if (thangSo > 12) {
            Toast.makeText(getContext(), "Tháng không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    if (TextUtils.isEmpty(thang) && !TextUtils.isEmpty(nam)){
        list = tinMonDAO.getTop10MonSoLuongCaoNhatTrongNam(nam);
        if (list.size() == 0){
            Toast.makeText(getContext(), "Không thấy bản ghi", Toast.LENGTH_SHORT).show();
            return;
        }else {
            Toast.makeText(getContext(), "Thành Công", Toast.LENGTH_SHORT).show();
            adapter = new MonBanChayAdapter(getContext(),list);
            rcvMonHot.setAdapter(adapter);
        }
    } else if (!TextUtils.isEmpty(thang) && !TextUtils.isEmpty(nam)){
        thang = String.format("%02d", Integer.parseInt(thang));
        list = tinMonDAO.getTop10MonSoLuongCaoNhatTrongThangNam(thang,nam);
        if (list.size() == 0){
            Toast.makeText(getContext(), "Không có bản ghi", Toast.LENGTH_SHORT).show();
            return;
        }else {
            Toast.makeText(getContext(), "Thành Công", Toast.LENGTH_SHORT).show();
            adapter = new MonBanChayAdapter(getContext(),list);
            rcvMonHot.setAdapter(adapter);
        }
    } else if (TextUtils.isEmpty(thang) && TextUtils.isEmpty(nam)){
        Toast.makeText(getContext(), "Vui Lòng Nhập Năm Và Tháng", Toast.LENGTH_SHORT).show();
        return;
    } else if (TextUtils.isEmpty(nam)){
        Toast.makeText(getContext(), "Không Xác Định Được Năm", Toast.LENGTH_SHORT).show();
        return;
    } else {
        Toast.makeText(getContext(), "Lỗi Không Xác Định", Toast.LENGTH_SHORT).show();
        return;
    }
    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
}
    void thongKeDoanhThu(View view) {
        tinMonDAO = new ThongTinMonDAO(getContext());
        String thang = inputLayoutThang.getEditText().getText().toString();
        String nam = inputLayoutNam.getEditText().getText().toString();
        // Kiểm tra giá trị nhập vào của chuỗi 'thang'
        if (!TextUtils.isEmpty(thang)) {
            int thangSo = Integer.parseInt(thang);
            if (thangSo > 12) {
                Toast.makeText(getContext(), "Tháng không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (TextUtils.isEmpty(thang) && !TextUtils.isEmpty(nam)){
            list = tinMonDAO.getTop10MonDoanhThuCaoNhatTrongNam(nam);
            if (list.size() == 0){
                Toast.makeText(getContext(), "Không thấy bản ghi", Toast.LENGTH_SHORT).show();
                return;
            }else {
                Toast.makeText(getContext(), "Thành Công", Toast.LENGTH_SHORT).show();
                adapter = new MonBanChayAdapter(getContext(),list);
                rcvMonHot.setAdapter(adapter);
            }
        } else if (!TextUtils.isEmpty(thang) && !TextUtils.isEmpty(nam)){
            thang = String.format("%02d", Integer.parseInt(thang));
            list = tinMonDAO.getTop10MonDoanhThuCaoNhatTrongThangNam(thang,nam);
            if (list.size() == 0){
                Toast.makeText(getContext(), "Không có bản ghi", Toast.LENGTH_SHORT).show();
                return;
            }else {
                Toast.makeText(getContext(), "Thành Công", Toast.LENGTH_SHORT).show();
                adapter = new MonBanChayAdapter(getContext(),list);
                rcvMonHot.setAdapter(adapter);
            }
        } else if (TextUtils.isEmpty(thang) && TextUtils.isEmpty(nam)){
            Toast.makeText(getContext(), "Vui Lòng Nhập Năm Và Tháng", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(nam)){
            Toast.makeText(getContext(), "Không Xác Định Được Năm", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(getContext(), "Lỗi Không Xác Định", Toast.LENGTH_SHORT).show();
            return;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void evRcvTkMonSoLuong() {
        tinMonDAO = new ThongTinMonDAO(getContext());
        list = tinMonDAO.getTop10MonSoLuongCaoNhatYearNow(DateHelper.getYearSQLNow());
        adapter = new MonBanChayAdapter(getContext(),list);
        rcvMonHot.setAdapter(adapter);
    }

    private void evRcvTkMonDoanhthu() {
        list.clear();
        tinMonDAO = new ThongTinMonDAO(getContext());
        list = tinMonDAO.getTop10MonDoanhThuCaoNhatYearNow(DateHelper.getYearSQLNow());
        adapter = new MonBanChayAdapter(getContext(),list);
        rcvMonHot.setAdapter(adapter);
    }

    private void anhXa(View view) {
        inputLayoutNam = view.findViewById(R.id.input_thongkeMon_Nam);
        inputLayoutThang = view.findViewById(R.id.input_thongkeMon_thang);
        rcvMonHot = view.findViewById(R.id.rcv_monhot);
        btnTim = view.findViewById(R.id.btn_tim_TkMon);
        rdoTkDoanhThu = view.findViewById(R.id.rdo_thongke_mon_DoanhThu);
        rdoTkSoLuong = view.findViewById(R.id.rdo_thongke_mon_SoLuong);
        groupThongKe = view.findViewById(R.id.group_Thogke);
    }
}