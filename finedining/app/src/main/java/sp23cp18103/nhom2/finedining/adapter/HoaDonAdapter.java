package sp23cp18103.nhom2.finedining.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.IEditListenerHoaDon;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.database.ThongTinChiTietDatMonDAO;
import sp23cp18103.nhom2.finedining.model.ThongTinChiTietDatMon;
import sp23cp18103.nhom2.finedining.model.ThongTinHoaDon;
import sp23cp18103.nhom2.finedining.utils.ColorHelper;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.NumberHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Adapter để hiển thị danh sách hóa đơn trong HoaDonFragment
 * */
public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder>{
    Context context;
    List<ThongTinHoaDon> ThongTinHoaDonList;

    List<ThongTinChiTietDatMon> thongTinChiTietDatMonList;
    private IEditListenerHoaDon iEditListener;

    private NhanVienDAO nhanVienDAO;


    public HoaDonAdapter(Context context, List<ThongTinHoaDon> ThongTinHoaDonList,IEditListenerHoaDon iEditListener) {
        this.context = context;
        this.ThongTinHoaDonList = ThongTinHoaDonList;
        this.iEditListener = iEditListener;
        nhanVienDAO = new NhanVienDAO(context);
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_hoa_don,parent,false);
        HoaDonAdapter.HoaDonViewHolder viewHolder = new HoaDonAdapter.HoaDonViewHolder(view);

        return viewHolder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final int vitri = position;

        ThongTinHoaDon tthd = ThongTinHoaDonList.get(position);
        String ngayHienTai = tthd.getThoiGianDat();
        String ngay =ngayHienTai.substring(0,10);
        holder.tv_tenKhach.setText(""+tthd.getTenKhachHang());
        holder.tvThoiGianXuat.setText(DateHelper.getDateTimeVietnam(tthd.getThoiGianDat()));
        if (tthd.getTrangThai()==1){
            holder.tvTrangThai.setText("Đang đặt");
            holder.tvTrangThai.setTextColor(ColorHelper.getNeutralColor(context));
        }else if (tthd.getTrangThai()==2){
            holder.tvTrangThai.setText("Chờ thanh toán");
            holder.tvTrangThai.setTextColor(ColorHelper.getWaitingColor(context));
        }else if (tthd.getTrangThai()==3){
            holder.tvTrangThai.setText("Đã thanh toán");
            holder.tvTrangThai.setTextColor(ColorHelper.getPositiveColor(context));
        }else if (tthd.getTrangThai()==0){
            holder.tvTrangThai.setText("Hủy");
            holder.tvTrangThai.setTextColor(ColorHelper.getNegativeColor(context));
        }
        if (ngay.equals(DateHelper.getDateSQLNow()) && tthd.getTrangThai() == 1){
            holder.imgThongBao.setVisibility(View.VISIBLE);
        } else {
            holder.imgThongBao.setVisibility(View.GONE);
        }
        //if ((tthd.getTrangThai() == 3 || tthd.getTrangThai() == 0) && nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(context)) != 1){
        if (tthd.getTrangThai() == 3 || tthd.getTrangThai() == 0){
            holder.imgEdit.setVisibility(View.GONE);
        } else {
            holder.imgEdit.setVisibility(View.VISIBLE);
        }


        holder.imgBill.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater=((Activity)context).getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_hoadon_chitiet,null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView tv_tenKhach = view.findViewById(R.id.tv_tenKhach_dialog_hoaDon_chiTiet);
                TextView tv_tenNhanVien = view.findViewById(R.id.tv_tenQuanLyHoaDon_dialog_hoaDon_chiTiet);
                TextView tv_SoluongKhach = view.findViewById(R.id.tv_soLuongKhach_dialog_hoaDon_chiTiet);
                TextView tvThoiGianXuat = view.findViewById(R.id.tv_thoiGianXuat_dialog_hoaDon_chiTiet);
                TextView tvThoiGianDat = view.findViewById(R.id.tv_thoiGianDat_dialog_hoaDon_chiTiet);
                RecyclerView rcv_mon = view.findViewById(R.id.rcv_mon_dialog_hoaDon_chiTiet);
                TextView tv_ban = view.findViewById(R.id.tv_danhSachBan_dialog_hoaDon_chiTiet);
                TextView tv_tongTien = view.findViewById(R.id.tv_tongTien_dialog_hoaDon_chiTiet);
                TextView tvTrangThai = view.findViewById(R.id.tv_trangThai_dialog_hoaDon_chiTiet);

                tv_tenNhanVien.setText(tthd.getTenNhanVien());
                tv_tenKhach.setText(tthd.getTenKhachHang());
                tv_SoluongKhach.setText(""+tthd.getSoLuongKhachHang());
                tvThoiGianXuat.setText(""+DateHelper.getDateTimeVietnam(tthd.getThoiGianXuat()));
                tvThoiGianDat.setText(""+ DateHelper.getDateTimeVietnam(tthd.getThoiGianDat()));

                if (tthd.getTrangThai()==1){
                   tvTrangThai.setText("Đang đặt");
                }else if (tthd.getTrangThai()==2){
                   tvTrangThai.setText("Chờ thanh toán");
                }else if (tthd.getTrangThai()==3){
                    tvTrangThai.setText("Đã thanh toán");
                }else if (tthd.getTrangThai()==0){
                   tvTrangThai.setText("Hủy");
                }

                ThongTinChiTietDatMonDAO thongTinChiTietDatMonDAO = new ThongTinChiTietDatMonDAO(context);
                thongTinChiTietDatMonList = thongTinChiTietDatMonDAO.getThongTinHoaDonChiTietDatMon(tthd.getMaHD());
                HoaDonChiTietMonAdapter hoaDonChiTietAdapter = new HoaDonChiTietMonAdapter(context,thongTinChiTietDatMonList);
                rcv_mon.setAdapter(hoaDonChiTietAdapter);

                tv_ban.setText(""+thongTinChiTietDatMonDAO.getBan(tthd.getMaHD()).toString()
                        .replace("[", "")
                        .replace("]", ""));
                tv_tongTien.setText(""+ NumberHelper.getNumberWithDecimal(thongTinChiTietDatMonDAO.getTongSoTien(tthd.getMaHD())));

                dialog.show();
            }

        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            iEditListener.showEditFragment(tthd.getMaHD());
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
        ImageView imgThongBao;

        TextView tv_tenKhach,tvSoLuongKhach,tvThoiGianXuat,tvTrangThai,tvTenKhachHang;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThongBao = itemView.findViewById(R.id.img_thongbao_CardView_HoaDon);
            imgBill = itemView.findViewById(R.id.imgBill);
            imgEdit = itemView.findViewById(R.id.imgBtn_edit_CardView_HoaDon);
            tv_tenKhach = itemView.findViewById(R.id.tv_tenKhach_CardView_HoaDon);
            tvThoiGianXuat = itemView.findViewById(R.id.tv_thoiGianXuat_CardView_HoaDon);
            tvTrangThai = itemView.findViewById(R.id.tv_trangThai_CardView_HoaDon);


        }
    }
}
