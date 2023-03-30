package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.IEditListenerHoaDon;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.HoaDonAdapter;
import sp23cp18103.nhom2.finedining.database.ThongTinHoaDonDAO;
import sp23cp18103.nhom2.finedining.model.ThongTinHoaDon;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
* Hiển thị danh sách hóa đơn
* */
public class HoaDonFragment extends Fragment {
    ThongTinHoaDonDAO thongTinHoaDonDAO;
    private FragmentManager fmHoaDon;
    RecyclerView rcv_HoaDon;
    List<ThongTinHoaDon> thongTinHoaDonList;
    HoaDonAdapter hoaDonAdapter;
    TextInputEditText edTimKiem,input_ngay,input_gio;
    TextInputLayout input_lyt_ngay,input_lyt_gio;
    CheckBox chkFragmentHoaDon;
    TextInputLayout inputTimKiemHoaDon;
    TextView tvDaThanhToan,tvChoThanhToan,tvDangDuocDat,tvDaHuy;
    FloatingActionButton fbtnThemHoaDon;
    int trangThaiHienTai=2;

    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        thongTinHoaDonDAO = new ThongTinHoaDonDAO(getContext());
        context = getContext();
        timKiemHoaDon();
        dialogChonNgay();
        diaLogChonGio();
        hienThiDanhSachHoaDon();
        khoiTaoFragmentManager();
        trangThaiThanhToan();
        handleOnBackPressed();

    }

    private void diaLogChonGio() {
        input_lyt_gio.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showTimePicker(context,input_gio);
            }
        });
    }

    private void dialogChonNgay() {
        input_lyt_ngay.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showDatePickerVietnam(context,input_ngay);
            }
        });

    }

    private void anhXa(View view) {
        rcv_HoaDon = view.findViewById(R.id.rcv_hoaDon);
        inputTimKiemHoaDon = view.findViewById(R.id.inputTimKiemHoaDon);
        edTimKiem = view.findViewById(R.id.input_01_hoaDon_timHoaDon);
        tvChoThanhToan = view.findViewById(R.id.tvChoThanhToan_hoaDon);
        tvDaThanhToan = view.findViewById(R.id.tvDaThanhToan_hoaDon);
        tvDangDuocDat = view.findViewById(R.id.tvDangDatTruoc_hoaDon);
        tvDaHuy = view.findViewById(R.id.tvHuy_hoaDon);
        fbtnThemHoaDon = view.getRootView().findViewById(R.id.fbtn_them_hoaDon_collection);
        input_ngay = view.findViewById(R.id.input_01_hoaDon_ChonNgay);
        input_lyt_ngay = view.findViewById(R.id.inputChonNgay_hoaDon);
        input_gio = view.findViewById(R.id.input_01_hoaDon_ChonGio);
        input_lyt_gio = view.findViewById(R.id.inputChonGio_hoaDon);
    }

    private void handleOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    fmHoaDon.popBackStack();
                    fbtnThemHoaDon.show();
                    hienThiDanhSachHoaDon();
                    }

            }
        });
    }

    public void trangThaiThanhToan(){
        tvDaThanhToan.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                trangThaiHienTai = 3;
                thongTinHoaDonList.clear();
                thongTinHoaDonList.addAll( thongTinHoaDonDAO.getTrangThai(PreferencesHelper.getId(context),trangThaiHienTai));
                hoaDonAdapter.notifyDataSetChanged();
            }
        });
        tvChoThanhToan.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                trangThaiHienTai=2;
                thongTinHoaDonList.clear();
                thongTinHoaDonList.addAll( thongTinHoaDonDAO.getTrangThai(PreferencesHelper.getId(context),trangThaiHienTai));
                hoaDonAdapter.notifyDataSetChanged();
            }
        });
        tvDangDuocDat.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                trangThaiHienTai=1;
                thongTinHoaDonList.clear();
                thongTinHoaDonList.addAll( thongTinHoaDonDAO.getTrangThai(PreferencesHelper.getId(context),trangThaiHienTai));
                hoaDonAdapter.notifyDataSetChanged();
            }

        });
        tvDaHuy.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                trangThaiHienTai=0;
                thongTinHoaDonList.clear();
                thongTinHoaDonList.addAll( thongTinHoaDonDAO.getTrangThai(PreferencesHelper.getId(context),trangThaiHienTai));
                hoaDonAdapter.notifyDataSetChanged();
            }
        });
    }


    public void timKiemHoaDon(){
        input_gio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hienThiHoaDon();

            }
        });
        input_ngay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hienThiHoaDon();

            }
        });
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                hienThiHoaDon();
            }
        });
        edTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH){
                    hienThiHoaDon();
                    return true;
                }
                return false;
            }
        });
        inputTimKiemHoaDon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiHoaDon();
            }
        });
    }
    public void hienThiHoaDon(){
        int maNV = PreferencesHelper.getId(getContext());
        String timKiem = edTimKiem.getText().toString().trim();
        String ngay = DateHelper.getDateSql(input_ngay.getText().toString().trim());
        String gio = input_gio.getText().toString().trim();
        thongTinHoaDonList = thongTinHoaDonDAO.getTrangThaiHoaDon(maNV, timKiem, trangThaiHienTai,ngay,gio);
        hoaDonAdapter = new HoaDonAdapter(getActivity(), thongTinHoaDonList,null);
        rcv_HoaDon.setAdapter(hoaDonAdapter);

    }
    private void khoiTaoFragmentManager() {
        fmHoaDon = getParentFragmentManager();
    }

    public void hienThiDanhSachHoaDon(){
        thongTinHoaDonList =  thongTinHoaDonDAO.getThongTinHoaDon(PreferencesHelper.getId(context));
        hoaDonAdapter = new HoaDonAdapter(getContext(), thongTinHoaDonList, new IEditListenerHoaDon() {
            @Override
            public void showEditFragment(int maHD) {
                fmHoaDon.beginTransaction()
                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                        .add(R.id.frame_collection_hoadon, new SuaHoaDonFragment(maHD))
                        .addToBackStack(null)
                        .commit();
                FloatingActionButton ftbtnThemHoaDon = getActivity().findViewById(R.id.fbtn_them_hoaDon_collection);
                ftbtnThemHoaDon.hide();
            }
        });
        rcv_HoaDon.setAdapter(hoaDonAdapter);
    }
}