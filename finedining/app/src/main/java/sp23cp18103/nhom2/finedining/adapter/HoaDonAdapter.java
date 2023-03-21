package sp23cp18103.nhom2.finedining.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.database.ThongTinChiTietDatMonDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.ThongTinChiTietDatMon;
import sp23cp18103.nhom2.finedining.model.ThongTinHoaDon;

/*
 * Adapter để hiển thị danh sách hóa đơn trong HoaDonFragment
 * */
public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder>{
    Context context;
    List<ThongTinHoaDon> ThongTinHoaDonList;

    List<ThongTinChiTietDatMon> thongTinChiTietDatMonList;


    public HoaDonAdapter(Context context, List<ThongTinHoaDon> ThongTinHoaDonList) {
        this.context = context;
        this.ThongTinHoaDonList = ThongTinHoaDonList;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_hoa_don,null);
        HoaDonAdapter.HoaDonViewHolder viewHolder = new HoaDonAdapter.HoaDonViewHolder(view);

        return viewHolder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {

        final int vitri = position;

        ThongTinHoaDon tthd = ThongTinHoaDonList.get(position);

        tthd.getMaHD();
        holder.tv_tenKhach.setText(""+tthd.getTenKhachHang());
        holder.tvSoLuongKhach.setText(""+tthd.getSoLuongKhachHang());
        holder.tvThoiGianXuat.setText(""+tthd.getThoiGianXuat());

        holder.tvTrangThai.setText((tthd.getTrangThai()==1) ?"Đã Thanh Toán":"Chưa Thanh Toán");


        holder.imgBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context,androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_hoadon_chitiet);

                TextView tv_SoluongKhach = dialog.findViewById(R.id.tv_soLuongKhach_dialog_hoaDon_chiTiet);
                TextView tvThoiGianXuat = dialog.findViewById(R.id.tv_thoiGianXuat_dialog_hoaDon_chiTiet);
                RecyclerView rcv_mon = dialog.findViewById(R.id.rcv_mon_dialog_hoaDon_chiTiet);
                TextView tv_ban = dialog.findViewById(R.id.tv_danhSachBan_dialog_hoaDon_chiTiet);
                TextView tv_tongTien = dialog.findViewById(R.id.tv_tongTien_dialog_hoaDon_chiTiet);
                TextView tvTrangThai = dialog.findViewById(R.id.tv_trangThai_dialog_hoaDon_chiTiet);

                tv_SoluongKhach.setText(""+tthd.getSoLuongKhachHang());
                tvThoiGianXuat.setText(""+tthd.getThoiGianXuat());
                tvTrangThai.setText((tthd.getTrangThai()==1) ?"Đã Thanh Toán":"Chưa Thanh Toán");

                ThongTinChiTietDatMonDAO thongTinChiTietDatMonDAO = new ThongTinChiTietDatMonDAO(context);
                thongTinChiTietDatMonList = thongTinChiTietDatMonDAO.getThongTinHoaDonChiTietDatMon(tthd.getMaHD());
                HoaDonChiTietMonAdapter hoaDonChiTietAdapter = new HoaDonChiTietMonAdapter(context,thongTinChiTietDatMonList);
                rcv_mon.setAdapter(hoaDonChiTietAdapter);

                tv_ban.setText(""+thongTinChiTietDatMonDAO.getBan(tthd.getMaHD()));

                tv_tongTien.setText(""+thongTinChiTietDatMonDAO.getTongSoTien(tthd.getMaHD()));

                dialog.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return ThongTinHoaDonList.size();
    }

    class HoaDonViewHolder extends RecyclerView.ViewHolder{
        ImageButton imgEdit;
        AppCompatButton imgBill;

        TextView tv_tenKhach,tvSoLuongKhach,tvThoiGianXuat,tvTrangThai;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBill = itemView.findViewById(R.id.imgBill);
            imgEdit = itemView.findViewById(R.id.imgBtn_edit_CardView_HoaDon);
            tv_tenKhach = itemView.findViewById(R.id.tv_tenKhach_CardView_HoaDon);
            tvSoLuongKhach = itemView.findViewById(R.id.tv_soLuongKhach_CardView_HoaDon);
            tvThoiGianXuat = itemView.findViewById(R.id.tv_thoiGianXuat_CardView_HoaDon);
            tvTrangThai = itemView.findViewById(R.id.tv_trangThai_CardView_HoaDon);

        }
    }
}
