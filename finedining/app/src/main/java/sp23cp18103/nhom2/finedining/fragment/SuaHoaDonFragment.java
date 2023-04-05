package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.InterfaceDatBan;
import sp23cp18103.nhom2.finedining.Interface.InterfaceDatMon;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.DatBanAdapter;
import sp23cp18103.nhom2.finedining.adapter.SuaDatMonAdapter;
import sp23cp18103.nhom2.finedining.database.BanDAO;
import sp23cp18103.nhom2.finedining.database.DatBanDAO;
import sp23cp18103.nhom2.finedining.database.DatMonDAO;
import sp23cp18103.nhom2.finedining.database.HoaDonDAO;
import sp23cp18103.nhom2.finedining.database.KhachDAO;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.database.ThongTinHoaDonDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.DatBan;
import sp23cp18103.nhom2.finedining.model.DatMon;
import sp23cp18103.nhom2.finedining.model.HoaDon;
import sp23cp18103.nhom2.finedining.model.KhachHang;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.ThongTinDatBan;
import sp23cp18103.nhom2.finedining.model.ThongTinDatMon;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.KeyboardHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;


public class SuaHoaDonFragment extends Fragment {
    Context context;

    TextInputEditText input_tenKH,input_soLuongKhach,input_ngayDat,input_GioDat;
    TextInputLayout input_mon,input_ban,input_lyt_ngayDat,input_lyt_giaDat,input_lyt_tenKH,input_lyt_soLuongKhach;

    RadioGroup groupTrangThai;
    RadioButton rdoDaThanhToan,rdoChuaThanhToan,rdoHuy,rdoDat;

    AppCompatButton btnLuu,btnHuy;
    ThongTinHoaDonDAO thongTinHoaDonDAO;
    MonDAO monDAO;
    List<Ban> banList;
    ArrayList<ThongTinDatMon> listDatMon = new ArrayList<>();
    ArrayList<ThongTinDatBan> listDatbanCu;//Để giữ cái danh sách thêm bàn ở fragment sửa hóa đơn
    ArrayList<ThongTinDatBan> listDatbanMoi;//Để giữ cái danh sách thêm bàn ở dialog sửa chọn bàn

    ArrayList<ThongTinDatMon> listDatMonCu;


    ArrayList<ThongTinDatBan> listDatban;
    KhachDAO khachDAO;
    HoaDonDAO hoaDonDAO;
    DatMonDAO datMonDAO;
    DatBanDAO datBanDAO;
    BanDAO banDAO;
    DatBan datBan;
    DatMon datMon;
    ThongTinDatMon thongTindatMon;
    ThongTinDatBan thongTindatBan ;
    private int maHD;
    KhachHang kh;
    HoaDon hoaDon;
    private FragmentManager fragmentManager;

    public SuaHoaDonFragment(int maHD) {
        this.maHD = maHD;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sua_hoa_don, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        anhXa(view);
        clearListSua();
        khoiTao();
        getTTHoaDon();
        khoiTaoListenerTrangThai();
        evClickChonMon();
        evClickChonBan();
        goiDialogNgayDat();
        goiDiaLogGioDat();
        luuHoaDon();
        khoiTaoListener();

    }

    private void khoiTaoListener() {
        input_ngayDat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    input_lyt_ngayDat.setError(null);
            }
        });
        input_GioDat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                input_lyt_giaDat.setError(null);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                clearListSua();
                FloatingActionButton ftbtnThemNhanVien = getActivity().findViewById(R.id.fbtn_them_hoaDon_collection);
                ftbtnThemNhanVien.show();
            }
        });
    }

    private void luuHoaDon(){
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();
                if (!validate()){
                    return;
                }

                //Trường hợp đổi trạng thái từ đặt trước sang chờ thanh toán
                if(!kiemTraBanTrung()){
                    return;
                }

                kh.setTenKH(input_tenKH.getText().toString().trim());
                kh.setMaKH(hoaDonDAO.getMaKhachHang(maHD));
                khachDAO.updateKhachHang(kh);
                //Hoa don
                hoaDon.setMaHD(maHD);
                hoaDon.setMaKH(hoaDonDAO.getMaKhachHang(maHD));
                hoaDon.setSoLuongKhach(Integer.parseInt(input_soLuongKhach.getText().toString().trim()));
                hoaDon.setMaNV(PreferencesHelper.getId(getContext()));

                if (rdoDaThanhToan.isChecked()){
                    hoaDon.setTrangThai(3);
                }
                if(rdoChuaThanhToan.isChecked()){
                    hoaDon.setTrangThai(2);
                }
                if (rdoDat.isChecked()){
                    hoaDon.setTrangThai(1);
                }
                if (rdoHuy.isChecked()){
                    hoaDon.setTrangThai(0);
                }
                hoaDon.setThoiGianDat(DateHelper.getDateSql(input_ngayDat.getText().toString().trim())+" "+input_GioDat.getText().toString().trim());

                if (hoaDonDAO.updateHoaDon(hoaDon)>0){
                    Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }

                upDateMon();

                //Lưu danh sách đặt bàn vào hóa đơn
                luuDatBanVaoHoaDon();
            }
        });
    }

    /*
     * Kiểm tra các bàn còn trùng với bàn đầy khi chuyển từ đặt bàn sang chờ thanh toán
     * Trả về true nếu không có bàn trùng
     * Trả về false nếu có bàn trùng và đưa ra dialog gợi ý cho người dùng bỏ các bàn bị trùng
     * */
    private boolean kiemTraBanTrung() {
        if(rdoChuaThanhToan.isChecked() && hoaDonDAO.getTrangThai(maHD)==1){
            ArrayList<ThongTinDatBan> danhSachDatTruocBanDay
                    = datBanDAO.layDanhSachDatTruocBanDay(PreferencesHelper.getId(getContext()), maHD);
            if(danhSachDatTruocBanDay.size() > 0 && !Collections.disjoint(listDatbanCu, danhSachDatTruocBanDay)){
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                builder.setTitle("Bàn Đặt Hiện Đang Sử Dụng");
                builder.setMessage(danhSachDatTruocBanDay.toString().replace("[","").replace("]",""));
                builder.setCancelable(false);
                builder.setPositiveButton("Bỏ đặt bàn đầy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listDatbanCu.removeAll(danhSachDatTruocBanDay);
                        input_ban.getEditText().setText(listDatbanCu.toString()
                                .replace("[", "")
                                .replace("]", ""));
                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.create().show();
                return false;
            }
        }
        return true;
    }

    /*
     * Lưu danh sách đặt bàn vào hóa đơn
     * */
    private void luuDatBanVaoHoaDon() {
        //Trường hợp không đổi trạng thái hoặc chỉ đổi từ chờ thanh toán sang đặt trước
        ArrayList<ThongTinDatBan> listLichSuDatBan = (ArrayList<ThongTinDatBan>) datBanDAO.getLichSuDatBan(PreferencesHelper.getId(context), maHD);
        for(int i = 0; i < listDatbanCu.size(); i++){
            ThongTinDatBan temp = listDatbanCu.get(i);
            if(listLichSuDatBan.contains(temp)){
                temp.setTrangThai(1);
                datBanDAO.updateDatBan(temp);
                listLichSuDatBan.remove(temp);
            } else {
                datBanDAO.insertDatBan(temp);
            }
        }
        for(int i = 0; i < listLichSuDatBan.size(); i++){
            ThongTinDatBan temp = listLichSuDatBan.get(i);
            temp.setTrangThai(0);
            datBanDAO.updateDatBan(temp);
        }
    }

    private void clearError() {
        input_lyt_tenKH.setError(null);
        input_lyt_soLuongKhach.setError(null);
        input_lyt_ngayDat.setError(null);
        input_lyt_giaDat.setError(null);
    }

    private void khoiTao() {
        fragmentManager = getParentFragmentManager();
        datBanDAO = new DatBanDAO(getContext());
        monDAO = new MonDAO(getContext());
        banDAO = new BanDAO(getContext());
        khachDAO = new KhachDAO(getContext());
        thongTinHoaDonDAO = new ThongTinHoaDonDAO(getContext());
        kh = new KhachHang();
        hoaDon = new HoaDon();
        datBan = new DatBan();
        datMon = new DatMon();
        thongTindatMon = new ThongTinDatMon();
        thongTindatBan = new ThongTinDatBan();
        listDatbanCu = new ArrayList<>();
        listDatbanMoi = new ArrayList<>();
        listDatMon = new ArrayList<>();

    }
    private void getTTHoaDon() {
       khachDAO = new KhachDAO(getContext());
       String tenKhach = khachDAO.getTenKhach(maHD);
       input_tenKH.setText(tenKhach);

       hoaDonDAO = new HoaDonDAO(getContext());
       int soLuongKhach = hoaDonDAO.getSoLuongKhach(maHD);
       input_soLuongKhach.setText("" + soLuongKhach);

       String thoiGianDat = hoaDonDAO.getNgayDat(maHD);
       String ngayDat = DateHelper.getDateVietnam(thoiGianDat.substring(0,10));
       String gioDat = thoiGianDat.substring(10,16);
       input_ngayDat.setText("" + ngayDat);
       input_GioDat.setText("" + gioDat);


       datMonDAO = new DatMonDAO(getContext());
       List<ThongTinDatMon> listDatmoncu = datMonDAO.getDatMonTheoHoaDon(maHD,PreferencesHelper.getId(context));
       listDatMon.addAll(listDatmoncu);
       listDatmoncu.toString();

       input_mon.getEditText().setText(listDatmoncu.toString()
               .replace("[", "")
               .replace("]", ""));

       //Hiển thị danh sách bàn đã đặt của hóa đơn hiện tại lên edit text chọn bàn
       hienThiBanDaDat();

        int trangThai = hoaDonDAO.getTrangThai(maHD);
        if (trangThai==1){
            rdoDat.setChecked(true);
        }
        if (trangThai==2){
            rdoChuaThanhToan.setChecked(true);
        }
        if (trangThai==3){
            rdoDaThanhToan.setChecked(true);
        }
        if (trangThai==0){
            rdoHuy.setChecked(true);
        }
    }
    private void getTTHoaDonSua() {
        khachDAO = new KhachDAO(getContext());
        String tenKhach = khachDAO.getTenKhach(maHD);
        input_tenKH.setText(tenKhach);

        hoaDonDAO = new HoaDonDAO(getContext());
        int soLuongKhach = hoaDonDAO.getSoLuongKhach(maHD);
        input_soLuongKhach.setText("" + soLuongKhach);

        String thoiGianDat = hoaDonDAO.getNgayDat(maHD);
        String ngayDat = DateHelper.getDateVietnam(thoiGianDat.substring(0, 10));
        String gioDat = thoiGianDat.substring(10, 16);
        input_ngayDat.setText("" + ngayDat);
        input_GioDat.setText("" + gioDat);
    }




    /*
    * Hiển thị danh sách bàn đã đặt của hóa đơn hiện tại lên edit text chọn bàn
    * */
    private void hienThiBanDaDat() {
        listDatbanCu = (ArrayList<ThongTinDatBan>) datBanDAO.getDanhSachBanDaDat(PreferencesHelper.getId(context), maHD);
        input_ban.getEditText().setText(listDatbanCu.toString()
                .replace("[", "")
                .replace("]", ""));
    }

    private void hienThiMonDaDat() {
       listDatMonCu  = (ArrayList<ThongTinDatMon>) datMonDAO.getDatMonTheoHoaDon(maHD,PreferencesHelper.getId(getContext()));
        input_mon.getEditText().setText(listDatMonCu.toString()
                .replace("[", "")
                .replace("]", ""));
    }

    void goiDialogNgayDat(){
        input_lyt_ngayDat.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showDatePickerVietnam(getContext(),input_ngayDat);
            }
        });
    }
    void goiDiaLogGioDat(){
        input_lyt_giaDat.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showTimePicker(getContext(),input_GioDat);
            }
        });
    }
    /*
    * Đặt listener cho chọn bàn
    * */
    private void khoiTaoListenerTrangThai(){
        groupTrangThai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hienThiBanDaDat();
                hienThiMonDaDat();
                getTTHoaDonSua();
                if(rdoDaThanhToan.isChecked() || rdoHuy.isChecked()){
                    input_ban.setClickable(false);
                    input_mon.setClickable(false);
                }
                if(rdoDat.isChecked() || rdoChuaThanhToan.isChecked()){
                    input_ban.setClickable(true);
                    input_mon.setClickable(true);
                }
            }
        });
    }


    private void evClickChonBan() {
        input_ban.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdoDat.isChecked()){
                    goiDiaLogBan(1);
                }
                if(rdoChuaThanhToan.isChecked()){
                    goiDiaLogBan(2);
                }
            }
        });
    }

    void evClickChonMon(){
        input_mon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdoDat.isChecked()){
                    goiDiaLogChonMon();
                }
                if(rdoChuaThanhToan.isChecked()){
                    goiDiaLogChonMon();
                }
            }
        });
    }

    /*
    * Gọi dialog chọn bàn
    * */
    void goiDiaLogBan(int trangThai){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater=(getActivity()).getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_dat_ban,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        //Ánh xạ
        RecyclerView rcv_ban = view.findViewById(R.id.rcv_dialog_chonBan_FragmentThemHoaDon);
        TextView tvBanDaChon = view.findViewById(R.id.tvBanDaChon_dialog_chonBan_FragmentThemHoaDon);
        AppCompatButton btnLuuChonBan = view.findViewById(R.id.btnLuu_dialog_chonBan_FragmentThemHoaDon);
        Button btnHuyDatBan = view.findViewById(R.id.btnhuy_dialog_chonBan_FragmentThemHoaDon);

        //KhoiTao
        listDatbanMoi.clear();
        listDatbanMoi.addAll(listDatbanCu);
        tvBanDaChon.setText(listDatbanMoi.toString()
                .replace("[", "")
                .replace("]", ""));
        banList = banDAO.getDanhSachBan(PreferencesHelper.getId(context));
        DatBanAdapter adapter = new DatBanAdapter(getContext(), (ArrayList<Ban>) banList, listDatbanCu, maHD, trangThai, new InterfaceDatBan() {
            @Override
            public int getMaBan(int maBan, CardView cardView) {
                ThongTinDatBan thongTinDatBan = new ThongTinDatBan();
                thongTinDatBan.setMaBan(maBan);
                thongTinDatBan.setMaHD(maHD);
                if(!listDatbanMoi.contains(thongTinDatBan)){
                    thongTinDatBan.setViTri(banDAO.getViTri(maBan));
                    thongTinDatBan.setTrangThai(1);
                    listDatbanMoi.add(thongTinDatBan);
                    cardView.setCardBackgroundColor(MaterialColors.getColor(view, com.google.android.material.R.attr.colorPrimary));
                } else {
                    listDatbanMoi.remove(thongTinDatBan);
                    cardView.setCardBackgroundColor(MaterialColors.getColor(view, com.google.android.material.R.attr.colorSecondaryContainer));
                }
                tvBanDaChon.setText(listDatbanMoi.toString()
                        .replace("[", "")
                        .replace("]", ""));
                return 0;
            }
        });
        rcv_ban.setAdapter(adapter);

        /*
        * Lưu lại vào danh sách chọn của hóa đơn hiện tại
        * */
        btnLuuChonBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDatbanCu.clear();
                listDatbanCu.addAll(listDatbanMoi);
                input_ban.getEditText().setText(listDatbanCu.toString()
                        .replace("[", "")
                        .replace("]", ""));
                dialog.dismiss();
            }
        });
        btnHuyDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    void goiDiaLogChonMon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_dat_mon, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView rcv_chonMon = view.findViewById(R.id.rcv_dialog_chonMon_FragmentThemHoaDon);
        AppCompatButton btnLuuChonMon = view.findViewById(R.id.btnLuu_dialog_chonMon_FragmentThemHoaDon);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) AppCompatButton btnHuyChonMon = view.findViewById(R.id.btn_huydatmon);
        List<Mon> listMon = monDAO.timKiem(PreferencesHelper.getId(getContext()), "");
        SuaDatMonAdapter adapter = new SuaDatMonAdapter(getContext(), listMon,listDatMon, new InterfaceDatMon() {
            @Override
            public int getMaMon(int maMon, String soluong) {
                int soLuong = Integer.parseInt(soluong);

                // Nếu nhập số lượng là 0, xoá món đó khỏi danh sách.
                if (soLuong == 0) {
                    for (ThongTinDatMon thongTinDatMon : listDatMon) {
                        if (thongTinDatMon.getMaMon() == maMon) {
                            listDatMon.remove(thongTinDatMon);
                            break;
                        }
                    }
                } else {
                    String tenMon = monDAO.getTenMon(maMon);
                    // kiểm tra xem món đã có trong danh sách hay chưa
                    boolean isAlreadyInList = false;
                    for (ThongTinDatMon thongTinDatMon : listDatMon) {
                        if (thongTinDatMon.getMaMon() == maMon) {
                            // nếu món đã có trong danh sách thì chỉ cập nhật số lượng
                            thongTinDatMon.setSoLuong(soLuong);
                            isAlreadyInList = true;
                            break;
                        }
                    }
                    // nếu món chưa có trong danh sách thì thêm mới
                    if (!isAlreadyInList) {
                        thongTindatMon = new ThongTinDatMon();
                        thongTindatMon.setMaMon(maMon);
                        thongTindatMon.setTenMon(tenMon);
                        thongTindatMon.setSoLuong(soLuong);
                        thongTindatMon.setMaHD(maHD);
                        thongTindatMon.setTrangThai(1);
                        listDatMon.add(thongTindatMon);
                    }
                }
                return 0;
            }
        });
        rcv_chonMon.setAdapter(adapter);
        adapter.setMaHD(maHD);
        adapter.getMaHD();

        btnLuuChonMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveListSua(listDatMon);
                input_mon.getEditText().setText(listDatMon.toString().replace("[", "")
                        .replace("]", ""));
                dialog.dismiss();
            }
        });

        btnHuyChonMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                KeyboardHelper.hideSoftKeyboard((Activity) context);
            }
        });

        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    void upDateMon() {
        // Lấy danh sách các món đã được đặt trong lịch sử
        List<ThongTinDatMon> listLichSuDatMon = datMonDAO.getLichSuDatMonTheoHoaDon(maHD, PreferencesHelper.getId(getContext()));
        // Duyệt danh sách các món hiện tại
        for (int i = 0; i < listDatMon.size(); i++) {
            ThongTinDatMon temp = listDatMon.get(i);
            // Nếu món hiện tại đã tồn tại trong lịch sử
            if (listLichSuDatMon.contains(temp)) {
                // Cập nhật số lượng và trạng thái của món
                temp.setTrangThai(1);
                if (datMonDAO.updateDatMonSoluong(temp) > 0) {
                    // Thông báo cập nhật thành công
                    Toast.makeText(getContext(), "Cập nhật món thành công trang thai 1", Toast.LENGTH_SHORT).show();
                    // Xoá món khỏi danh sách lịch sử đặt món
                    listLichSuDatMon.remove(temp);
                } else {
                    // Thông báo cập nhật thất bại
                    Toast.makeText(getContext(), "Cập nhật món thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Nếu món hiện tại không tồn tại trong lịch sử
                if (datMonDAO.insertDatMon(temp) > 0) {
                    // Thông báo thêm mới thành công
                    Toast.makeText(getContext(), "Thêm món mới thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Thông báo thêm mới thất bại
                    Toast.makeText(getContext(), "Thêm món mới thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Các món còn lại trong danh sách lịch sử đặt món sẽ có trạng thái được đặt lại là 0
        for (int i = 0; i < listLichSuDatMon.size(); i++) {
            ThongTinDatMon temp = listLichSuDatMon.get(i);
            temp.setTrangThai(0);
            if (datMonDAO.updateDatMonSoluong(temp) > 0) {
                // Thông báo cập nhật thành công
                Toast.makeText(getContext(), "Cập nhật món thành công trạng thái là 0", Toast.LENGTH_SHORT).show();
            } else {
                // Thông báo cập nhật thất bại
                Toast.makeText(getContext(), "Cập nhật món thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
    void saveListSua(List<ThongTinDatMon> listDatMon){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPrefSaveListSua", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonListDatMon = new Gson().toJson(listDatMon);
        editor.putString("listSuaMon", jsonListDatMon);
        editor.apply();
    }

    void clearListSua(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPrefSaveListSua", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("listSuaMon");
        editor.apply();
    }

    private void anhXa(View view) {
        input_tenKH = view.findViewById(R.id.input_tenKhachHang_sua_FragmentSuaHoaDon);
        input_soLuongKhach = view.findViewById(R.id.input_soLuongKhach_sua_FragmentSuaHoaDon);
        input_ban = view.findViewById(R.id.input_ban_Sua_FragmentSuaHoaDon);
        input_mon = view.findViewById(R.id.input_mon_sua_FragmentSuaHoaDon);
        btnLuu = view.findViewById(R.id.btnSua_FragmentSuaHoaDon);
        btnHuy = view.findViewById(R.id.btnHuy_FragmentSuaHoaDon);
        input_ngayDat =view.findViewById(R.id.input_ngayDat_sua_FragmentSuaHoaDon);
        input_lyt_ngayDat = view.findViewById(R.id.input_lyt_ngayDat_sua_FragmentSuaHoaDon);
        input_GioDat = view.findViewById(R.id.input_gioDat_sua_FragmentSuaHoaDon);
        input_lyt_giaDat = view.findViewById(R.id.input_lyt_gioDat_sua_FragmentSuaHoaDon);
        rdoDaThanhToan = view.findViewById(R.id.rdoDaThanhToan_SuaHoaDon);
        rdoHuy = view.findViewById(R.id.rdoHuy_SuaHoaDon);
        rdoChuaThanhToan = view.findViewById(R.id.rdoChuaThanhToan_SuaHoaDon);
        rdoDat = view.findViewById(R.id.rdoDatBan_SuaHoaDon);
        input_lyt_tenKH = view.findViewById(R.id.input_lyt_tenKhachHang_sua_FragmentSuaHoaDon);
        input_lyt_soLuongKhach = view.findViewById(R.id.input_lyt_soLuongKhach_sua_FragmentSuaHoaDon);
        groupTrangThai = view.findViewById(R.id.groupTrangThai_SuaHoaDon);
    }
    public Boolean validate() {
        //them Hoa Don
        if (input_tenKH.getText().toString().trim().equals("")){
            input_lyt_tenKH.setError("Chưa nhập tên khách hàng");
            return false;
        }
        if (input_soLuongKhach.getText().toString().trim().equals("")){
            input_lyt_soLuongKhach.setError("Chưa nhập Số Lượng");
            return false;
        }
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(input_ngayDat.getText().toString().trim());
            assert date != null;
            if (!input_ngayDat.getText().toString().trim().equals(sdf.format(date))) {
                input_lyt_ngayDat.setError("Định dạng ngày sai");
                return false;
            }
        } catch (ParseException ex) {
            input_lyt_ngayDat.setError("Định dạng ngày sai");
            return false;
        }
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = sdf.parse(input_GioDat.getText().toString().trim());
            assert date != null;
            if (!input_GioDat.getText().toString().trim().equals(sdf.format(date))) {
                input_lyt_giaDat.setError("Định dạng giờ sai");
                return false;
            }
        } catch (ParseException ex) {
            input_lyt_giaDat.setError("Định dạng giờ sai");
            return false;
        }
        return true;
    }
}