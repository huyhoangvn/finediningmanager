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
    ArrayList<ThongTinDatMon> listDatMon ;
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

                for (int i = 0; i < listDatMon.size(); i++){
                    datMon.setMaMon(listDatMon.get(i).getMaMon());
                    datMon.setSoLuong(listDatMon.get(i).getSoLuong());
                    datMon.setMaHD(listDatMon.get(i).getMaHD());
                    datMonDAO.updateDatMon(datMon);
                }

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
       ThongTinDatMon thongTinDatMon = datMonDAO.getMon(maHD);

       String tenmon = thongTinDatMon.getTenMon();
       int soluong = thongTinDatMon.getSoLuong();
       input_mon.getEditText().setText(tenmon +" x "+ soluong);

       datBanDAO = new DatBanDAO(getContext());
       ThongTinDatBan thongTinDatBan = datBanDAO.getBan(maHD);
       String viTri = thongTinDatBan.getViTri();
       input_ban.getEditText().setText(viTri);

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
    void goiDiaLogMon(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater=((Activity)getActivity()).getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_dat_mon,null);
        builder.setView(view);
        Dialog dialog = builder.create();

        RecyclerView rcv_chonMon = view.findViewById(R.id.rcv_dialog_chonMon_FragmentThemHoaDon);
        TextInputEditText inputSoLuongChonMon = view.findViewById(R.id.input_SoLuong_dialog_chonMon_FragmentThemHoaDon);
        TextView tvMonDaChon = view.findViewById(R.id.tvMonDaChon_dialog_chonMon_FragmentThemHoaDon);
        AppCompatButton btnLuuChonMon = view.findViewById(R.id.btnLuu_dialog_chonMon_FragmentThemHoaDon);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        AppCompatButton btnChon = view.findViewById(R.id.btnChon_SoLuongMon);
        monList = monDAO.timKiem(PreferencesHelper.getId(getContext()),"");
        DatMonAdapter adapter = new DatMonAdapter(getContext(), monList, new InterfaceDatMon() {
            @Override
            public int getMaMon(int maMon) {
                String tenMon = monDAO.getTenMon(maMon);
                thongTindatMon.setTenMon(tenMon);
                thongTindatMon.setMaMon(maMon);
                return 0;
            }
        });
        rcv_chonMon.setAdapter(adapter);
        tvMonDaChon.setText("");
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = Integer.parseInt(inputSoLuongChonMon.getText().toString());
                thongTindatMon.setSoLuong(soLuong);
                listDatMon.add(thongTindatMon);
                tvMonDaChon.setText(listDatMon.toString());
            }
        });

        btnLuuChonMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_mon.getEditText().setText(listDatMon.toString());
                dialog.dismiss();
            }

        });
        dialog.show();
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