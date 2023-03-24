package sp23cp18103.nhom2.finedining.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.InterfaceDatBan;
import sp23cp18103.nhom2.finedining.Interface.InterfaceDatMon;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.model.Ban;

public class DatBanAdapter extends RecyclerView.Adapter<DatBanAdapter.DatBanViewHolder> {

    Context context;
    List<Ban> banList;
    InterfaceDatBan interfaceDatBan;

    public DatBanAdapter(Context context, List<Ban> banList, InterfaceDatBan interfaceDatBan) {
        this.context = context;
        this.banList = banList;
        this.interfaceDatBan = interfaceDatBan;
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
        final int viTri = position;

        Ban ban = banList.get(position);
        holder.tvViTri.setText(""+ban.getViTri());
        holder.tvTrangThai.setText((ban.getTrangThai()==1)?"Còn Trống ":"Hết");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linearDatBan.setBackgroundColor(Color.GREEN);
                interfaceDatBan.getMaBan(ban.getMaBan());
            }
        });


    }

    @Override
    public int getItemCount() {
        return banList.size();
    }

    class DatBanViewHolder extends RecyclerView.ViewHolder{
        TextView tvViTri,tvTrangThai;
        LinearLayout linearDatBan;
        public DatBanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTrangThai  = itemView.findViewById(R.id.tv_trangthai_DatBan);
            tvViTri = itemView.findViewById(R.id.tv_vitri_DatBan);
            linearDatBan = itemView.findViewById(R.id.linearDatBan);

        }
    }
}
