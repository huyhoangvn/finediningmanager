package sp23cp18103.nhom2.finedining.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.MonBanChayAdapter;
import sp23cp18103.nhom2.finedining.database.ThongTinMonDAO;
import sp23cp18103.nhom2.finedining.model.ThongTinMon;

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
    ThongTinMonDAO tinMonDAO;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
//        evRcvTk();
        evTim();
    }

    private void evTim() {
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhapNgayNam();
            }
        });
    }

    void nhapNgayNam(){
        tinMonDAO = new ThongTinMonDAO(getContext());
        String thang = inputLayoutThang.getEditText().getText().toString();
        String nam = inputLayoutNam.getEditText().getText().toString();
        list = tinMonDAO.gettopmonThangNam(thang,nam);
        adapter = new MonBanChayAdapter(getContext(),list);
        rcvMonHot.setAdapter(adapter);
    }

    private void evRcvTk() {
        tinMonDAO = new ThongTinMonDAO(getContext());
        list = tinMonDAO.gettopmonTheoNam("2023");
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
    }
}