package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.RadioButton;
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
import java.util.Date;
import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.InterfaceDatBan;
import sp23cp18103.nhom2.finedining.Interface.InterfaceDatMon;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.DatBanAdapter;
import sp23cp18103.nhom2.finedining.adapter.DatMonAdapter;
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
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Thêm hóa đơn mới (có thể dùng để sửa hóa đơn hoặc tạo một SuaHoaDonFragment)
 * */
public class ThemHoaDonFragment extends Fragment {

    TextInputEditText input_tenKH,input_soLuongKhach,input_thoiGianDat,input_gioDat;
    RadioButton rdoChuaThanhToan,rdoDangDuocDat;
    TextInputLayout  input_mon,input_ban,input_lyt_thoiGianDat,input_lyt_gioDat,input_lyt_tenKH,input_lyt_soLuongKhach;
    TextView tvTieuDe;
    AppCompatButton btnLuu, btnHuy;
    private FragmentManager fragmentManager;
    ThongTinHoaDonDAO thongTinHoaDonDAO;
    MonDAO monDAO;
    HoaDon hoaDon;
    ArrayList<Ban> banList;
    ArrayList<ThongTinDatMon> listDatMon = new ArrayList<>();
    ArrayList<ThongTinDatBan> listDatbanCu = new ArrayList<>();
    ArrayList<ThongTinDatBan> listDatbanMoi = new ArrayList<>();
    int maHoaDonSapThem;
    int maKHSapThem;
    KhachDAO khachDAO;
    HoaDonDAO hoaDonDAO;
    DatMonDAO datMonDAO;
    DatBanDAO datBanDAO;

    BanDAO banDAO;
    DatBan datBan;
    DatMon datMon;
    ThongTinDatMon thongTindatMon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_them_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        clearListChon();
        khoiTao();
        khoiTaoDefault();
        khoiTaoListenerTimKiemThoiGian();
        khoiTaoListenerTrangThai();
        khoiTaoListenerDatBan();
        khoiTaoListenerDatMon();
        khoiTaoListenerThemHoaDon();

    }



    private void anhXa(View view) {
        input_tenKH = view.findViewById(R.id.input_tenKhachHang_them_FragmentThemHoaDon);
        input_soLuongKhach = view.findViewById(R.id.input_soLuongKhach_them_FragmentThemHoaDon);
        input_ban = view.findViewById(R.id.input_ban_them_FragmentThemHoaDon);
        input_mon = view.findViewById(R.id.input_mon_them_FragmentThemHoaDon);
        btnLuu = view.findViewById(R.id.btnThem_FragmentThemHoaDon);
        btnHuy = view.findViewById(R.id.btnHuy_FragmentThemHoaDon);
        tvTieuDe = view.findViewById(R.id.tv_tieuDe_FragmentThemHoaDon);
        rdoChuaThanhToan = view.findViewById(R.id.rdoChoThanhToan_ThemHoaDon);
        rdoDangDuocDat = view.findViewById(R.id.rdoDangDuocDat_ThemHoaDon);
        input_thoiGianDat = view.findViewById(R.id.input_thoiGianDat_them_FragmentThemHoaDon);
        input_lyt_thoiGianDat = view.findViewById(R.id.input_lyt_thoiGianDat_them_FragmentThemHoaDon);
        input_lyt_gioDat = view.findViewById(R.id.input_lyt_gioDat_them_FragmentThemHoaDon);
        input_gioDat = view.findViewById(R.id.input_gioDat_them_FragmentThemHoaDon);
        input_lyt_tenKH = view.findViewById(R.id.input_tenKhachHang_lyt_them_FragmentThemHoaDon);
        input_lyt_soLuongKhach = view.findViewById(R.id.input_soLuongKhach_lyt_them_FragmentThemHoaDon);
        tvTieuDe.setText("Thêm Hóa Đơn");
    }

    private void khoiTao() {
        fragmentManager = getParentFragmentManager();
        thongTindatMon = new ThongTinDatMon();
        hoaDonDAO = new HoaDonDAO(getContext());
        maHoaDonSapThem = hoaDonDAO.getMaHoaDonTiepTheo();
        thongTindatMon.setMaHD(maHoaDonSapThem);
        thongTinHoaDonDAO = new ThongTinHoaDonDAO(getContext());
        monDAO = new MonDAO(getContext());
        datMonDAO = new DatMonDAO(getContext());
        banDAO = new BanDAO(getContext());
        datBan = new ThongTinDatBan();
        datBanDAO = new DatBanDAO(getContext());
        datMon = new DatMon();
        datBan = new DatBan();
        hoaDon = new HoaDon();
    }


    /*
    * Khởi tạo giá trị mặc định cho thời gian
    * */
    private void khoiTaoDefault() {
        String thoiGianHientai = DateHelper.getDateTimeVietnamNow();
        String ngayXuat = thoiGianHientai.substring(0,10);
        input_thoiGianDat.setText(ngayXuat);
        String gioXuat = thoiGianHientai.substring(10,16);
        input_gioDat.setText(gioXuat);
    }

    private void khoiTaoListenerTimKiemThoiGian() {
        input_thoiGianDat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                input_lyt_thoiGianDat.setError(null);
            }
        });
        input_gioDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_lyt_gioDat.setError(null);
            }
        });
        input_lyt_gioDat.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showTimePicker(getContext(),input_gioDat);
            }
        });
        input_lyt_thoiGianDat.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateHelper.showDatePickerVietnam(getContext(),input_thoiGianDat);
            }
        });
    }

    private void khoiTaoListenerTrangThai() {
        rdoChuaThanhToan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                input_ban.getEditText().setText("");
                listDatbanCu.clear();
                khoiTaoDefault();
            }
        });
        rdoDangDuocDat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                input_ban.getEditText().setText("");
                listDatbanCu.clear();
            }
        });
    }

    private void khoiTaoListenerThemHoaDon() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearError();

                if (!validate()){
                    return;
                }


                //Khach
                khachDAO = new KhachDAO(getContext());
                maKHSapThem = khachDAO.getMaKhanhHangTiepTheo();
                KhachHang kh = new KhachHang();
                kh.setTenKH(input_tenKH.getText().toString().trim());
                khachDAO.insert(kh);
                //Hoa don
                HoaDon hoaDon = new HoaDon();
                //get ma khach tiep theo;
                hoaDon.setMaKH(maKHSapThem);
                hoaDon.setSoLuongKhach(Integer.parseInt(input_soLuongKhach.getText().toString().trim()));
                hoaDon.setMaNV(PreferencesHelper.getId(getContext()));

                if (rdoChuaThanhToan.isChecked()){
                    hoaDon.setTrangThai(2);
                }
                if(rdoDangDuocDat.isChecked()){
                    hoaDon.setTrangThai(1);
                }

                hoaDon.setThoiGianXuat(DateHelper.getDateTimeSQLNow());
                hoaDon.setThoiGianDat(DateHelper.getDateSql(input_thoiGianDat.getText().toString().trim())+" "+input_gioDat.getText().toString().trim());
                hoaDonDAO.insertHoaDon(hoaDon);

                themDatBan();
                themDatMon();

                Toast.makeText(getContext(), "luu thanh cong", Toast.LENGTH_SHORT).show();
                input_tenKH.setText("");
                input_soLuongKhach.setText("");
                input_thoiGianDat.setText("");
                input_gioDat.setText("");
                input_ban.getEditText().setText("");
                input_mon.getEditText().setText("");

                Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
            }

            private void clearError() {
                input_lyt_tenKH.setError(null);
                input_lyt_soLuongKhach.setError(null);
                input_lyt_thoiGianDat.setError(null);
                input_lyt_gioDat.setError(null);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                clearListChon();
                FloatingActionButton ftbtnThem = getActivity().findViewById(R.id.fbtn_them_hoaDon_collection);
                ftbtnThem.show();
            }
        });
    }

    void goiDiaLogChonMon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_dat_mon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView rcv_chonMon = view.findViewById(R.id.rcv_dialog_chonMon_FragmentThemHoaDon);
        AppCompatButton btnLuuChonMon = view.findViewById(R.id.btnLuu_dialog_chonMon_FragmentThemHoaDon);
        List<Mon> listMon = monDAO.timKiem(PreferencesHelper.getId(getContext()), "");
        DatMonAdapter adapter = new DatMonAdapter(getContext(), listMon, new InterfaceDatMon() {
            @Override
            public int getMaMon(int maMon, String soluong) {
//                        int maHoaDonSapThem = hoaDonDAO.getMaHoaDonTiepTheo();
//                        int soLuong = Integer.parseInt(soluong);
//                        if (soLuong != 0) {
//                            String tenMon = monDAO.getTenMon(maMon);
//                            // kiểm tra xem món đã có trong danh sách hay chưa
//                            boolean isAlreadyInList = false;
//                            for (ThongTinDatMon thongTinDatMon : listDatMon) {
//                                if (thongTinDatMon.getMaMon() == maMon) {
//                                    // nếu món đã có trong danh sách thì chỉ cập nhật số lượng
//                                    thongTinDatMon.setSoLuong(soLuong);
//                                    isAlreadyInList = true;
//                                    break;
//                                }
//                            }
//                            // nếu món chưa có trong danh sách thì thêm mới
//                            if (!isAlreadyInList) {
//                                thongTindatMon = new ThongTinDatMon();
//                                thongTindatMon.setMaMon(maMon);
//                                thongTindatMon.setTenMon(tenMon);
//                                thongTindatMon.setSoLuong(soLuong);
//                                thongTindatMon.setMaHD(maHoaDonSapThem);
//                                listDatMon.add(thongTindatMon);
//                            }
                int maHoaDonSapThem = hoaDonDAO.getMaHoaDonTiepTheo();
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
                        thongTindatMon.setMaHD(maHoaDonSapThem);
                        thongTindatMon.setTrangThai(1);
                        listDatMon.add(thongTindatMon);
                    }
                }

                return 0;
            }
        });
        btnLuuChonMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveListChon(listDatMon);
                input_mon.getEditText().setText(listDatMon.toString()
                        .replace("[", "")
                        .replace("]", ""));
                dialog.dismiss();
            }
        });
        rcv_chonMon.setAdapter(adapter);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    private void themDatBan() {
        for(int i = 0; i < listDatbanCu.size(); i++){
            datBanDAO.insertDatBan(listDatbanCu.get(i));
        }
    }
    private void themDatMon(){
        for (int i = 0; i < listDatMon.size(); i++) {
            datMon = new DatMon();
            datMon.setMaMon(listDatMon.get(i).getMaMon());
            datMon.setSoLuong(listDatMon.get(i).getSoLuong());
            datMon.setMaHD(listDatMon.get(i).getMaHD());
            datMon.setTrangThai(1);
            if (datMonDAO.insertDatMon(datMon) > 0) {
                Toast.makeText(getContext(), "Thêm món thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Thêm món Không thành công", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        listDatMon.clear();
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
            Date date = sdf.parse(input_thoiGianDat.getText().toString().trim());
            assert date != null;
            if (!input_thoiGianDat.getText().toString().trim().equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            input_lyt_thoiGianDat.setError("Định dạng ngày sai");
            return false;
        }
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = sdf.parse(input_gioDat.getText().toString().trim());
            assert date != null;
            if (!input_gioDat.getText().toString().trim().equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            input_lyt_gioDat.setError("Định dạng ngày sai");
            return false;
        }
        return true;
    }


    private void khoiTaoListenerDatMon() {
        input_mon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goiDiaLogChonMon();
            }
        });
    }

    /*
    * Khởi tạo nút đặt bàn
    * */
    private void khoiTaoListenerDatBan() {
        input_ban.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdoChuaThanhToan.isChecked()){
                    showDialogDatBan(2);
                }
                if(rdoDangDuocDat.isChecked()){
                    showDialogDatBan(1);
                }
            }
        });
    }

    /*
    * Hiển thị dialog đặt bàn tùy theo trạng thái chờ thanh toán hoặc đang đặt
    * */
    private void showDialogDatBan(int trangThai) {
        //Khởi tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater=((Activity)getActivity()).getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_dat_ban,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //Ánh xạ
        RecyclerView rcv_ban = view.findViewById(R.id.rcv_dialog_chonBan_FragmentThemHoaDon);
        TextView tvBanDaChon = view.findViewById(R.id.tvBanDaChon_dialog_chonBan_FragmentThemHoaDon);
        AppCompatButton btnLuuChonBan = view.findViewById(R.id.btnLuu_dialog_chonBan_FragmentThemHoaDon);
        //Khởi tạo biến
        listDatbanMoi.clear();
        listDatbanMoi.addAll(listDatbanCu);
        tvBanDaChon.setText(listDatbanMoi.toString()
                .replace("[", "")
                .replace("]", ""));
        maHoaDonSapThem = hoaDonDAO.getMaHoaDonTiepTheo();

        //Khởi tạo adapter
        banList = (ArrayList<Ban>) banDAO.getDanhSachBan(PreferencesHelper.getId(getContext()));
        DatBanAdapter adapter = new DatBanAdapter(getContext(), banList, listDatbanCu, maHoaDonSapThem, trangThai, new InterfaceDatBan() {
            @Override
            public int getMaBan(int maBan, CardView cardView) {
                ThongTinDatBan thongTinDatBan = new ThongTinDatBan();
                thongTinDatBan.setMaBan(maBan);
                thongTinDatBan.setMaHD(maHoaDonSapThem);
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
                return 1;
            }
        });
        rcv_ban.setAdapter(adapter);
        //Thêm danh sách bàn đã chọn vào hóa đơn hiện tại
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
        dialog.show();

    }


    void saveListChon(List<ThongTinDatMon> listDatMon){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPrefSaveListDat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonListDatMon = new Gson().toJson(listDatMon);
        editor.putString("listDatMon", jsonListDatMon);
        editor.apply();
    }
    void clearListChon(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPrefSaveListDat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("listDatMon");
        editor.apply();
    }

}