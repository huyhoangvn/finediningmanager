package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
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
import sp23cp18103.nhom2.finedining.database.ThongTinChiTietDatMonDAO;
import sp23cp18103.nhom2.finedining.database.ThongTinHoaDonDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.DatBan;
import sp23cp18103.nhom2.finedining.model.DatMon;
import sp23cp18103.nhom2.finedining.model.HoaDon;
import sp23cp18103.nhom2.finedining.model.KhachHang;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.ThongTinChiTietDatMon;
import sp23cp18103.nhom2.finedining.model.ThongTinDatBan;
import sp23cp18103.nhom2.finedining.model.ThongTinDatMon;
import sp23cp18103.nhom2.finedining.model.ThongTinHoaDon;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Thêm hóa đơn mới (có thể dùng để sửa hóa đơn hoặc tạo một SuaHoaDonFragment)
 * */
public class ThemHoaDonFragment extends Fragment {
    TextInputEditText input_tenKH,input_soLuongKhach;
    TextInputLayout  input_mon,input_ban;
    TextView tvTieuDe;
    AppCompatButton btnLuu,btnHuy;
    private FragmentManager fragmentManager;
    ThongTinHoaDonDAO thongTinHoaDonDAO;

    MonDAO monDAO;
    List<Mon> monList2;

    List<Ban> banList;
    ArrayList<ThongTinDatMon> listDatMon = new ArrayList<>();
    ArrayList<ThongTinDatBan> listDatban = new ArrayList<>();
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
    ThongTinDatBan thongTindatBan ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_them_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        input_tenKH = view.findViewById(R.id.input_tenKhachHang_them_FragmentThemHoaDon);
        input_soLuongKhach = view.findViewById(R.id.input_soLuongKhach_them_FragmentThemHoaDon);
        input_ban = view.findViewById(R.id.input_ban_them_FragmentThemHoaDon);
        input_mon = view.findViewById(R.id.input_mon_them_FragmentThemHoaDon);
        btnLuu = view.findViewById(R.id.btnThem_FragmentThemHoaDon);
        btnHuy = view.findViewById(R.id.btnHuy_FragmentThemHoaDon);
        tvTieuDe = view.findViewById(R.id.tv_tieuDe_FragmentThemHoaDon);
        fragmentManager = getParentFragmentManager();

        thongTindatMon = new ThongTinDatMon();

        hoaDonDAO = new HoaDonDAO(getContext());
        maHoaDonSapThem = hoaDonDAO.getMaHoaDonTiepTheo();
        thongTindatMon.setMaHD(maHoaDonSapThem);
        tvTieuDe.setText("Thêm Hóa Đơn");
        thongTinHoaDonDAO = new ThongTinHoaDonDAO(getContext());
        monDAO = new MonDAO(getContext());
        datMonDAO = new DatMonDAO(getContext());
        banDAO = new BanDAO(getContext());
        thongTindatBan = new ThongTinDatBan();

        datBan = new ThongTinDatBan();
        datBanDAO = new DatBanDAO(getContext());

        datMon = new DatMon();
        datBan = new DatBan();

        input_mon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                monList2 = monDAO.timKiem(PreferencesHelper.getId(getContext()),"");
                DatMonAdapter adapter = new DatMonAdapter(getContext(), monList2, new InterfaceDatMon() {
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
        });

        input_ban.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater=((Activity)getActivity()).getLayoutInflater();
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
                        thongTindatBan.setMaHD(maHoaDonSapThem);
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

        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // thêm đặt bàn , đặt mon fixx

                for (int i = 0; i < listDatMon.size(); i++){
                    datMon.setMaMon(listDatMon.get(i).getMaMon());
                    datMon.setSoLuong(listDatMon.get(i).getSoLuong());
                    datMon.setMaHD(listDatMon.get(i).getMaHD());
                    if (datMonDAO.insertDatMon(datMon) > 0){
                        Toast.makeText(getContext(), "Thêm món thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Thêm món Không thành công", Toast.LENGTH_SHORT).show();
                    }
                }


                listDatban.size();
                for (int i = 0; i < listDatban.size(); i++){
                    datBan.setMaBan(listDatban.get(i).getMaBan());
                    datBan.setMaHD(listDatban.get(i).getMaHD());
                    datBan.setThoiGianDat(DateHelper.getDateTimeSQLNow());
                    if (datBanDAO.insertDatBan(datBan) > 0){
                        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "khong thanh cong ", Toast.LENGTH_SHORT).show();
                    }
                }


                //Khach
                khachDAO = new KhachDAO(getContext());
                KhachHang kh = new KhachHang();
                kh.setTenKH(input_tenKH.getText().toString().trim());
                khachDAO.insert(kh);

                listDatMon.size();

                //Hoa don
                HoaDon hoaDon = new HoaDon();
                //get ma khach tiep theo;
                maKHSapThem = khachDAO.getMaKhanhHangTiepTheo();
                hoaDon.setMaKH(maKHSapThem);
                hoaDon.setSoLuongKhach(Integer.parseInt(input_soLuongKhach.getText().toString().trim()));
                hoaDon.setMaNV(PreferencesHelper.getId(getContext()));
                hoaDon.setTrangThai(2);
                hoaDon.setThoiGianXuat(DateHelper.getDateTimeSQLNow());

                hoaDonDAO.insertHoaDon(hoaDon);


                Toast.makeText(getContext(), "Thanh cong", Toast.LENGTH_SHORT).show();

                input_tenKH.setText("");
                input_soLuongKhach.setText("");
                input_mon.getEditText().setText("");
                input_ban.getEditText().setText("");

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnBackPressed();
            }
        });


    }

    private void handleOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    khoiTaoFragmentManager();
                    fragmentManager.popBackStack();
                }
            }
        });
    }
    private void khoiTaoFragmentManager() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_collection_hoadon,new HoaDonFragment())
                .commit();
    }
    public int validate() {
        int check = 1;
        if (input_tenKH.getText().length() == 0) {
            Toast.makeText(getContext(), "Dữ liệu không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (input_soLuongKhach.getText().length()==0){
            Toast.makeText(getContext(), "Dữ liệu không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
        }
        return check;
    }
}