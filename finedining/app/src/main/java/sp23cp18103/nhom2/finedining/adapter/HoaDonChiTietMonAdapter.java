package sp23cp18103.nhom2.finedining.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.ThongTinChiTietDatMonDAO;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.ThongTinChiTietDatMon;

public class HoaDonChiTietMonAdapter extends RecyclerView.Adapter<HoaDonChiTietMonAdapter.HoaDonChiTietViewHolder> {

    Context context;
    List<ThongTinChiTietDatMon> thongTinChiTietDatMonList;
    ThongTinChiTietDatMonDAO dao;

    public HoaDonChiTietMonAdapter(Context context, List<ThongTinChiTietDatMon> thongTinChiTietDatMonList) {
        this.context = context;
        this.thongTinChiTietDatMonList = thongTinChiTietDatMonList;
        dao = new ThongTinChiTietDatMonDAO(context);
        Log.d("zzzz", "HoaDonChiTietMonAdapter: "+ this.thongTinChiTietDatMonList);

    }

    @NonNull
    @Override
    public HoaDonChiTietMonAdapter.HoaDonChiTietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_hoadonchitiet_mon, parent,false);
        HoaDonChiTietMonAdapter.HoaDonChiTietViewHolder viewHolder = new HoaDonChiTietMonAdapter.HoaDonChiTietViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonChiTietViewHolder holder, int position) {
        final int vitri = position;

        ThongTinChiTietDatMon ttct = thongTinChiTietDatMonList.get(position);
        holder.tvTenMon.setText(""+ttct.getTenMon());
        holder.tvGia.setText(""+ttct.getGia()+" VND");
        holder.tvSoLuong.setText(""+ttct.getSoLuong());
        holder.tvThanhGia.setText(""+ttct.getThanhTien());


    }

    @Override
    public int getItemCount() {
        return thongTinChiTietDatMonList.size();
    }

    public class HoaDonChiTietViewHolder extends RecyclerView.ViewHolder{
            TextView tvTenMon,tvGia,tvSoLuong,tvThanhGia;
        public HoaDonChiTietViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenMon = itemView.findViewById(R.id.tvTenMon_Cardview_HoaDonChiTiet_mon);
            tvGia = itemView.findViewById(R.id.tvGiaTien_Cardview_HoaDonChiTiet_mon);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong_Cardview_HoaDonChiTietmon);
            tvThanhGia = itemView.findViewById(R.id.tvThanhGia_Cardview_HoaDonChiTietmon);

        }
    }
}
