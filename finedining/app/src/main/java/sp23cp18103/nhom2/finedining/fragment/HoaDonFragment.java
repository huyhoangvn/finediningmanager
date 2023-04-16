package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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
    TextView tvDaThanhToan,tvChoThanhToan,tvDangDuocDat,tvDaHuy,tvTatCa;
    FloatingActionButton fbtnThemHoaDon;
    int trangThaiHienTai = -1;

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
        context = getContext();
        thongTinHoaDonDAO = new ThongTinHoaDonDAO(context);

        anhXa(view);
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
        tvTatCa = view.findViewById(R.id.tvTatCa_hoaDon);

    }

    private void handleOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    fmHoaDon.popBackStack();
                    fbtnThemHoaDon.show();
                }
            }
        });
    }

    public void trangThaiThanhToan(){
        tvTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuyenChon(trangThaiHienTai,-1);
                trangThaiHienTai = -1;
                hienThiTatCa();
            }
        });

        tvDaThanhToan.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                chuyenChon(trangThaiHienTai, 3);
                trangThaiHienTai = 3;
                capNhatHoaDon();

            }
        });
        tvChoThanhToan.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                chuyenChon(trangThaiHienTai, 2);
                trangThaiHienTai=2;
                capNhatHoaDon();
            }
        });
        tvDangDuocDat.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                chuyenChon(trangThaiHienTai, 1);
                trangThaiHienTai=1;
                capNhatHoaDon();
            }

        });
        tvDaHuy.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                chuyenChon(trangThaiHienTai, 0);
                trangThaiHienTai=0;
                capNhatHoaDon();
            }
        });
    }

    /*
    * Thay đổi nền của nút khi chuyển đổi qua lại giữa các lựa chọn trạng thái hóa đơn
    * */
    private void chuyenChon(int trangThaiHienTai, int i) {
        if (trangThaiHienTai==-1){
            tvTatCa.setBackground(AppCompatResources.getDrawable(context, R.drawable.filter_item_normal_background));
        }
        if(trangThaiHienTai == 1){
            tvDangDuocDat.setBackground(AppCompatResources.getDrawable(context, R.drawable.filter_item_normal_background));
        }
        if(trangThaiHienTai == 2){
            tvChoThanhToan.setBackground(AppCompatResources.getDrawable(context, R.drawable.filter_item_normal_background));
        }
        if(trangThaiHienTai == 3){
            tvDaThanhToan.setBackground(AppCompatResources.getDrawable(context, R.drawable.filter_item_normal_background));
        }
        if(trangThaiHienTai == 0){
            tvDaHuy.setBackground(AppCompatResources.getDrawable(context, R.drawable.filter_item_normal_background));
        }
        if (i==-1){
            tvTatCa.setBackground(AppCompatResources.getDrawable(context, R.drawable.filter_item_clicked_background));
        }
        if(i == 1){
            tvDangDuocDat.setBackground(AppCompatResources.getDrawable(context, R.drawable.filter_item_clicked_background));
        }
        if(i == 2){
            tvChoThanhToan.setBackground(AppCompatResources.getDrawable(context, R.drawable.filter_item_clicked_background));
        }
        if(i == 3){
            tvDaThanhToan.setBackground(AppCompatResources.getDrawable(context, R.drawable.filter_item_clicked_background));
        }
        if(i == 0){
            tvDaHuy.setBackground(AppCompatResources.getDrawable(context, R.drawable.filter_item_clicked_background));
        }
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
                if(trangThaiHienTai==-1){
                    hienThiTatCa();
                } else {
                    capNhatHoaDon();
                }
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
                if(trangThaiHienTai==-1){
                    hienThiTatCa();
                } else {
                    capNhatHoaDon();
                }
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
                if(trangThaiHienTai==-1){
                    hienThiTatCa();
                } else {
                    capNhatHoaDon();
                }
            }
        });
        edTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(trangThaiHienTai==-1){
                        hienThiTatCa();
                    } else {
                        capNhatHoaDon();
                    }
                    return true;
                }
                return false;
            }
        });
        inputTimKiemHoaDon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trangThaiHienTai==-1){
                    hienThiTatCa();
                } else {
                    capNhatHoaDon();
                }
            }
        });
        edTimKiem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fbtnThemHoaDon.show();
                    }
                }, 100);
            }
        });
        input_ngay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fbtnThemHoaDon.show();
                    }
                }, 100);
            }
        });
        input_gio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fbtnThemHoaDon.show();
                    }
                }, 100);
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    public void capNhatHoaDon(){
        int maNV = PreferencesHelper.getId(context);
        String timKiem = edTimKiem.getText().toString().trim();
        String ngay = DateHelper.getDateSql(input_ngay.getText().toString().trim());
        String gio = input_gio.getText().toString().trim();
        thongTinHoaDonList.clear();
        thongTinHoaDonList.addAll(thongTinHoaDonDAO.getTrangThaiHoaDon(maNV, timKiem, trangThaiHienTai,ngay,gio));
        hoaDonAdapter.notifyDataSetChanged();
    }
    public void hienThiTatCa(){
        int maNV = PreferencesHelper.getId(context);
        String timKiem = edTimKiem.getText().toString().trim();
        String ngay = DateHelper.getDateSql(input_ngay.getText().toString().trim());
        String gio = input_gio.getText().toString().trim();
        thongTinHoaDonList.clear();
        thongTinHoaDonList.addAll(thongTinHoaDonDAO.getTatCa(maNV, timKiem,ngay,gio));
        hoaDonAdapter.notifyDataSetChanged();
    }
    private void khoiTaoFragmentManager() {
        fmHoaDon = getParentFragmentManager();
        fmHoaDon.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                chuyenChon(trangThaiHienTai, -1);
                trangThaiHienTai = -1;
                hienThiTatCa();
            }
        });
    }

    public void hienThiDanhSachHoaDon(){
        trangThaiHienTai = -1;
        chuyenChon(trangThaiHienTai, -1);
        int maNV = PreferencesHelper.getId(context);
        String timKiem = edTimKiem.getText().toString().trim();
        String ngay = DateHelper.getDateSql(input_ngay.getText().toString().trim());
        String gio = input_gio.getText().toString().trim();
        thongTinHoaDonList =  thongTinHoaDonDAO.getTatCa(maNV, timKiem,ngay,gio);
        hoaDonAdapter = new HoaDonAdapter(context, thongTinHoaDonList, new IEditListenerHoaDon() {
            @Override
            public void showEditFragment(int maHD) {
                fmHoaDon.beginTransaction()
                        .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
                        .add(R.id.frame_collection_hoadon, new SuaHoaDonFragment(maHD))
                        .addToBackStack(null)
                        .commit();
                FloatingActionButton ftbtnThemHoaDon = getActivity().findViewById(R.id.fbtn_them_hoaDon_collection);
                ftbtnThemHoaDon.hide();
            }
        });
        rcv_HoaDon.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0)
                {
                    fbtnThemHoaDon.hide();
                }
                if (dy <= 0)
                {
                    fbtnThemHoaDon.show();
                }
            }

//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
//            {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE)
//                {
//                    fbtnThemHoaDon.show();
//                }
//
//                super.onScrollStateChanged(recyclerView, newState);
//            }
        });
        rcv_HoaDon.setAdapter(hoaDonAdapter);
    }
}