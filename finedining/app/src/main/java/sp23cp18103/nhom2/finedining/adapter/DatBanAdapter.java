package sp23cp18103.nhom2.finedining.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.InterfaceDatBan;
import sp23cp18103.nhom2.finedining.Interface.InterfaceDatMon;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.BanDAO;
import sp23cp18103.nhom2.finedining.database.DatBanDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.ThongTinDatBan;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

public class DatBanAdapter extends RecyclerView.Adapter<DatBanAdapter.DatBanViewHolder>{
    private Context context;
    private ArrayList<Ban> banList;
    private ArrayList<ThongTinDatBan> datBanHienTaiList;
    private InterfaceDatBan interfaceDatBan;
    public int maHD;
    private BanDAO banDAO;
    private DatBanDAO datBanDAO;
    private int trangThai;//Trạng thái hóa đơn

    public DatBanAdapter(Context context, ArrayList<Ban> banList, ArrayList<ThongTinDatBan> datBanHienTaiList,
                         int maHD, int trangThai, InterfaceDatBan interfaceDatBan) {
        this.context = context;
        this.banList = banList;
        this.datBanHienTaiList = datBanHienTaiList;
        this.maHD= maHD;
        this.interfaceDatBan = interfaceDatBan;
        this.trangThai = trangThai;
        //Khoi tao
        khoiTaoDAO();
    }

    private void khoiTaoDAO() {
        this.banDAO = new BanDAO(context);
        this.datBanDAO = new DatBanDAO(context);
    }

    @NonNull
    @Override
    public DatBanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_datban,parent,false);
        DatBanAdapter.DatBanViewHolder viewHolder = new DatBanAdapter.DatBanViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DatBanViewHolder holder, int position) {
        Ban ban = banList.get(position);
        int trangThaiDay = banDAO.getKiemTraConTrong(PreferencesHelper.getId(context), ban.getMaBan());
        int banThuocHoaDon = datBanDAO.getKiemTraBanThuocHoaDon(ban.getMaBan(), maHD);
        holder.tvViTri.setText(ban.getViTri());

        //Nếu hóa đơn chờ thanh toán thì hiện trống đầy
        if(trangThai == 2){
            if(banThuocHoaDon == 1){
                holder.tvTrangThai.setText("Đã Đặt");
            } else {
                holder.tvTrangThai.setText((trangThaiDay==1)?"Đầy":"Trống");
            }
        } else {
            holder.tvTrangThai.setVisibility(View.GONE);
        }

        //Hiện màu cho những bàn đã đặt
        if(datBanHienTaiList.contains(new ThongTinDatBan(ban.getMaBan(), maHD, 1, ""))){
            holder.cardDatBan.setCardBackgroundColor(MaterialColors.getColor(holder.itemView, com.google.android.material.R.attr.colorPrimary));
        }

        //Cho phép chọn nếu bàn thuộc hóa đơn hoặc bàn trống hoặc trong trạng thái đặt
        if(trangThaiDay != 1 || banThuocHoaDon == 1 || trangThai == 1){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceDatBan.getMaBan(ban.getMaBan(), holder.cardDatBan);
                }
            });
        } else {
            holder.cardDatBan.setAlpha(0.5f);
        }
    }

    @Override
    public int getItemCount() {
        return banList.size();
    }

    class DatBanViewHolder extends RecyclerView.ViewHolder{
        TextView tvViTri,tvTrangThai;
        CardView cardDatBan;
        public DatBanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTrangThai  = itemView.findViewById(R.id.tv_trangthai_DatBan);
            tvViTri = itemView.findViewById(R.id.tv_vitri_DatBan);
            cardDatBan = itemView.findViewById(R.id.card_DatBan);

        }
    }
}
