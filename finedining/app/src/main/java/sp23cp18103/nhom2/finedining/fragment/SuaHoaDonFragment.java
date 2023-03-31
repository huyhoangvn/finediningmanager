package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.IEditListenerHoaDon;
import sp23cp18103.nhom2.finedining.Interface.InterfaceDatBan;
import sp23cp18103.nhom2.finedining.Interface.InterfaceDatMon;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.DatBanAdapter;
import sp23cp18103.nhom2.finedining.adapter.DatMonAdapter;
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
import sp23cp18103.nhom2.finedining.model.ThongTinHoaDon;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;


public class SuaHoaDonFragment extends Fragment {
    Context context;

    TextInputEditText input_tenKH,input_soLuongKhach,input_ngayDat,input_GioDat;
    TextInputLayout input_mon,input_ban,input_lyt_ngayDat,input_lyt_giaDat,input_lyt_tenKH,input_lyt_soLuongKhach;

    RadioButton rdoDaThanhToan,rdoChuaThanhToan,rdoHuy,rdoDat;

    AppCompatButton btnLuu,btnHuy;
    ThongTinHoaDonDAO thongTinHoaDonDAO;
    MonDAO monDAO;
    List<Mon> monList;
    List<Ban> banList;
    List<HoaDon> HoaDonList;

    ArrayList<ThongTinDatMon> listDatMon = new ArrayList<>();
    ArrayList<ThongTinDatBan> listDatban;
    ArrayList<ThongTinDatBan> listDatbanMoi;

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
        khoiTao();
        getTTHoaDon();
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
                hoaDon.setThoiGianXuat(DateHelper.getDateTimeSQLNow());
                hoaDon.setThoiGianDat(input_ngayDat.getText().toString().trim()+" "+input_GioDat.getText().toString().trim());

                if (hoaDonDAO.updateHoaDon(hoaDon)>0){
                    Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }

                //QUYET
                for (int i = 0; i < listDatMon.size(); i++) {
                    DatMon datMon = listDatMon.get(i);
                    if (datMonDAO.checkDatMonExist(maHD, datMon.getMaMon())) {
                        // Update món đã tồn tại trong hóa đơn
                        if (datMonDAO.updateDatMon(datMon, maHD) > 0) {
                            Toast.makeText(getContext(), "Cập nhật món thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Cập nhật món thất bại", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        // Thêm món mới vào hóa đơn
                        if (datMonDAO.insertDatMon(datMon) > 0) {
                            Toast.makeText(getContext(), "Thêm món mới thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Thêm món mới thất bại", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }


//                for (int i = 0; i < listDatMon.size(); i++){
//                    datMon = new DatMon();
//                    if (!listDatMon.equals(listDatMon.get(i).getTenMon())){
//                        datMon.setMaMon(listDatMon.get(i).getMaMon());
//                        datMon.setSoLuong(listDatMon.get(i).getSoLuong());
//                        datMon.setMaHD(listDatMon.get(i).getMaHD());
//                        if (datMonDAO.insertDatMon(datMon) > 0) {
//                            Toast.makeText(getContext(), "Thêm món thành công", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getContext(), "Thêm món Không thành công", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }else {
//                        datMon = listDatMon.get(i);
//                        datMon.setMaMon(listDatMon.get(i).getMaMon());
//                        datMon.setSoLuong(listDatMon.get(i).getSoLuong());
//                        if(datMonDAO.updateDatMon(datMon,maHD) > 0){
//                            Toast.makeText(getContext(), "Clmm", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(getContext(), "Ối zooif oi", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                }
            }
            //QUYET

            private void clearError() {
                input_lyt_tenKH.setError(null);
                input_lyt_soLuongKhach.setError(null);
                input_lyt_ngayDat.setError(null);
                input_lyt_giaDat.setError(null);
            }
        });
    }


    private void khoiTao() {
        fragmentManager = getParentFragmentManager();
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
        listDatban = new ArrayList<>();
        listDatMon = new ArrayList<>();

    }
    private void getTTHoaDon() {
       khachDAO = new KhachDAO(getContext());
       String tenKhach = khachDAO.getTenKhach(maHD);
       input_tenKH.setText(tenKhach);

       hoaDonDAO = new HoaDonDAO(getContext());
       int soLuongKhach = hoaDonDAO.getSoLuongKhach(maHD);
       input_soLuongKhach.setText(""+soLuongKhach);

       String thoiGianDat = hoaDonDAO.getNgayDat(maHD);
        Log.d("TAG", ":" + thoiGianDat + ":");
       String ngayDat = thoiGianDat.substring(0,10);
       String gioDat = thoiGianDat.substring(10,16);
       input_ngayDat.setText(""+ngayDat);
       input_GioDat.setText(""+gioDat);


       datMonDAO = new DatMonDAO(getContext());
       List<ThongTinDatMon> list = datMonDAO.getDatMonTheoHoaDon(maHD);
       input_mon.getEditText().setText(list.toString());

       datBanDAO = new DatBanDAO(getContext());
       ThongTinDatBan thongTinDatBan = datBanDAO.getBan(maHD);
       String viTri = thongTinDatBan.getViTri();
       input_ban.getEditText().setText(viTri);

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

    void evClickChonMon(){
        input_mon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              goiDiaLogMon();
            }
        });
    }
    
    void evClickChonBan(){
        input_ban.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goiDiaLogBan();
            }
        });
    }
    void goiDiaLogBan(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater=(getActivity()).getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_dat_ban,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        //Ánh xạ
        RecyclerView rcv_ban = view.findViewById(R.id.rcv_dialog_chonBan_FragmentThemHoaDon);
        TextView tvBanDaChon = view.findViewById(R.id.tvBanDaChon_dialog_chonBan_FragmentThemHoaDon);
        AppCompatButton btnLuuChonBan = view.findViewById(R.id.btnLuu_dialog_chonBan_FragmentThemHoaDon);

        int maHoaDonSapThem = hoaDonDAO.getMaHoaDonTiepTheo();
        banList = banDAO.getDanhSachBan(PreferencesHelper.getId(context));
        DatBanAdapter adapter = new DatBanAdapter(getContext(), (ArrayList<Ban>) banList, listDatban, maHoaDonSapThem, new InterfaceDatBan() {
            @Override
            public int getMaBan(int maBan, CardView cardView) {
                String viTri = banDAO.getViTri(maBan);
                thongTindatBan.setViTri(viTri);
                thongTindatBan.setMaBan(maBan);
                listDatban.add(thongTindatBan);
                tvBanDaChon.setText(listDatban.toString());
                return 0;
            }
        });

        rcv_ban.setAdapter(adapter);

        tvBanDaChon.setText("");
        btnLuuChonBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_ban.getEditText().setText(listDatban.toString()
                        .replace("[", "")
                        .replace("]", ""));
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    void evClickChonMon(){
        input_mon.setEndIconOnClickListener(new View.OnClickListener() {
        RecyclerView rcv_chonMon = view.findViewById(R.id.rcv_dialog_chonMon_FragmentThemHoaDon);
//        TextInputEditText inputSoLuongChonMon = view.findViewById(R.id.input_SoLuong_dialog_chonMon_FragmentThemHoaDon);
//        TextView tvMonDaChon = view.findViewById(R.id.tvMonDaChon_dialog_chonMon_FragmentThemHoaDon);
        AppCompatButton btnLuuChonMon = view.findViewById(R.id.btnLuu_dialog_chonMon_FragmentThemHoaDon);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//        AppCompatButton btnChon = view.findViewById(R.id.btnChon_SoLuongMon);
        monList = monDAO.timKiem(PreferencesHelper.getId(getContext()),"");
        DatMonAdapter adapter = new DatMonAdapter(getContext(), monList, new InterfaceDatMon() {
            @Override
            public void onClick(View v) {
                goiDiaLogChonMon();
            }
        });
    }


    void goiDiaLogChonMon() {
        input_mon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_dat_mon, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                RecyclerView rcv_chonMon = view.findViewById(R.id.rcv_dialog_chonMon_FragmentThemHoaDon);
                AppCompatButton btnLuuChonMon = view.findViewById(R.id.btnLuu_dialog_chonMon_FragmentThemHoaDon);
                List<Mon> listMon = monDAO.timKiem(PreferencesHelper.getId(getContext()), "");
                SuaDatMonAdapter adapter = new SuaDatMonAdapter(getContext(), listMon, new InterfaceDatMon() {
                    @Override
                    public int getMaMon(int maMon, String soluong) {
                        int maHoaDonSapThem = hoaDonDAO.getMaHoaDonTiepTheo();
                        int soLuong = Integer.parseInt(soluong);
                        if (soLuong != 0) {
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
                                thongTindatMon.setMaHD(maHoaDonSapThem);
                                listDatMon.add(thongTindatMon);
                            }
                        }
                        return 0;
                    }
                });
                adapter.setMaHD(maHD);
                adapter.getMaHD();
                adapter.setListThongTinMon(listDatMon);
                btnLuuChonMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        input_mon.getEditText().setText(listDatMon.toString());
                        List<ThongTinDatMon> list = listDatMon;
                        list.toString();
                        dialog.dismiss();
                    }
                });
                rcv_chonMon.setAdapter(adapter);
                dialog.show();
            }

        });

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
                date = null;
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
                date = null;
            }
        } catch (ParseException ex) {
            input_lyt_giaDat.setError("Định dạng ngày sai");
            return false;
        }
        return true;
    }
}