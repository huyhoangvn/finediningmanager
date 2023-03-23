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

import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.InterfaceDatMon;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.model.Mon;

/*
 * Adapter để hiển thị danh sách đặt món trong hóa đơn chi tiết
 * Đặt bàn chỉ cần hiển thị list các vị trí là được nên không cần adapter
 * */
public class DatMonAdapter extends RecyclerView.Adapter<DatMonAdapter.DatMonViewHolder> {
    Context context;

    List<Mon> monList;
    InterfaceDatMon interfaceDatMon;

    public DatMonAdapter(Context context, List<Mon> monList, InterfaceDatMon interfaceDatMon) {
        this.context = context;
        this.monList = monList;
        this.interfaceDatMon = interfaceDatMon;
    }

    @NonNull
    @Override
    public DatMonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_chonmon,null);
        DatMonAdapter.DatMonViewHolder viewHolder = new DatMonAdapter.DatMonViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DatMonViewHolder holder, int position) {

        final int vitri = position;
        Mon mon = monList.get(position);

        holder.tvTen.setText(""+mon.getTenMon());
        holder.tvGia.setText(""+mon.getGia());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.lnChonMon.setBackgroundColor(Color.GREEN);
                interfaceDatMon.getMaMon(mon.getMaMon());
            }
        });

    }

    @Override
    public int getItemCount() {
        return monList.size();
    }

    class DatMonViewHolder extends RecyclerView.ViewHolder{
        TextView tvTen,tvGia;
        LinearLayout lnChonMon;

        public DatMonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenMon_Cardview_datMon);
            tvGia = itemView.findViewById(R.id.tvGia_Cardview_datMon);
            lnChonMon = itemView.findViewById(R.id.linearChonMon);
        }
    }
}
