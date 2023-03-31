package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
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


    TextInputEditText input_tenKH,input_soLuongKhach;
    TextInputLayout input_mon,input_ban;
    TextView tvTieuDe;
    AppCompatButton btnLuu,btnHuy;
    ThongTinHoaDonDAO thongTinHoaDonDAO;
    MonDAO monDAO;
    List<Mon> monList;
    List<Ban> banList;
    List<HoaDon> HoaDonList;
    ArrayList<ThongTinDatMon> listDatMon = new ArrayList<>();
    ArrayList<ThongTinDatBan> listDatban ;
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
        anhXa(view);
        khoiTao();
        getTTHoaDon();
        evClickChonMon();
        evClickChonBan();
        luuHoaDon();
    }




    private void khoiTao() {
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

//        listDatMon = (ArrayList<ThongTinDatMon>) datMonDAO.getDatMonTheoHoaDon(maHD);


    }
    private void getTTHoaDon() {
       khachDAO = new KhachDAO(getContext());
       String tenKhach = khachDAO.getTenKhach(maHD);
       input_tenKH.setText(tenKhach);

       hoaDonDAO = new HoaDonDAO(getContext());
       int soLuongKhach = hoaDonDAO.getSoLuongKhach(maHD);
       input_soLuongKhach.setText(""+soLuongKhach);

       datMonDAO = new DatMonDAO(getContext());
       List<ThongTinDatMon> list = datMonDAO.getDatMonTheoHoaDon(maHD);
       input_mon.getEditText().setText(list.toString());

       datBanDAO = new DatBanDAO(getContext());
       ThongTinDatBan thongTinDatBan = datBanDAO.getBan(maHD);
       String viTri = thongTinDatBan.getViTri();
       input_ban.getEditText().setText(viTri);

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

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView rcv_ban = view.findViewById(R.id.rcv_dialog_chonBan_FragmentThemHoaDon);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tvBanDaChon = view.findViewById(R.id.tvBanDaChon_dialog_chonBan_FragmentThemHoaDon);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        AppCompatButton btnLuuChonBan = view.findViewById(R.id.btnLuu_dialog_chonBan_FragmentThemHoaDon);


        banList = banDAO.gettimKiem(PreferencesHelper.getId(getContext()),"");
        DatBanAdapter adapter = new DatBanAdapter(getContext(), banList, new InterfaceDatBan() {
            @Override
            public int getMaBan(int maBan) {
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
                input_ban.getEditText().setText(listDatban.toString());
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    void evClickChonMon(){
        input_mon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goiDiaLogChonMon();
            }
        });
    }
    private void luuHoaDon(){
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kh.setTenKH(input_tenKH.getText().toString().trim());
                kh.setMaKH(hoaDonDAO.getMaKhachHang(maHD));
                khachDAO.updateKhachHang(kh);
                //Hoa don
                hoaDon.setMaHD(maHD);
                hoaDon.setMaKH(hoaDonDAO.getMaKhachHang(maHD));
                hoaDon.setSoLuongKhach(Integer.parseInt(input_soLuongKhach.getText().toString().trim()));
                hoaDon.setMaNV(PreferencesHelper.getId(getContext()));
                hoaDon.setTrangThai(1);
                hoaDon.setThoiGianXuat(DateHelper.getDateTimeSQLNow());
                if (hoaDonDAO.updateHoaDon(hoaDon)>0){
                    Toast.makeText(getContext(), "hinh nhu la thanh cong", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "meo", Toast.LENGTH_SHORT).show();
                }




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

                for (int i = 0; i < listDatban.size(); i++){
                    datBan.setMaBan(listDatban.get(i).getMaBan());
                    datBan.setMaHD(listDatban.get(i).getMaHD());
                    datBan.setThoiGianDat(DateHelper.getDateTimeSQLNow());
                    if (datBanDAO.updateDatBan(datBan) > 0){
                        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "khong thanh cong ", Toast.LENGTH_SHORT).show();
                    }
                }

//                Toast.makeText(getContext(), "Thanh cong", Toast.LENGTH_SHORT).show();



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
        input_mon = view.findViewById(R.id.input_mon_Sua_FragmentSuaHoaDon);
        btnLuu = view.findViewById(R.id.btnSua_FragmentSuaHoaDon);
        btnHuy = view.findViewById(R.id.btnHuy_FragmentSuaHoaDon);
        fragmentManager = getParentFragmentManager();
    }
}