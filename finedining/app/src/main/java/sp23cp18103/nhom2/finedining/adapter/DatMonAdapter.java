package sp23cp18103.nhom2.finedining.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
    private boolean isSelected = false;
    private SparseBooleanArray selectedItems;

    public DatMonAdapter(Context context, List<Mon> monList, InterfaceDatMon interfaceDatMon) {
        this.context = context;
        this.monList = monList;
        this.interfaceDatMon = interfaceDatMon;

        selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public DatMonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_chonmon, parent, false);
        DatMonAdapter.DatMonViewHolder viewHolder = new DatMonAdapter.DatMonViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DatMonViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final int vitri = position;
        Mon mon = monList.get(position);

        holder.tvTen.setText(""+mon.getTenMon());

        holder.itemView.setSelected(selectedItems.get(position, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItems.get(position, false)) {
                    selectedItems.delete(position);
                    holder.itemView.setSelected(false);
                    holder.lnChonMon.setBackgroundColor(Color.WHITE);

                } else {
                    selectedItems.put(position, true);
                    holder.itemView.setSelected(true);
                    interfaceDatMon.getMaMon(mon.getMaMon());
                    holder.lnChonMon.setBackgroundColor(Color.GREEN);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return monList.size();
    }

    class DatMonViewHolder extends RecyclerView.ViewHolder{
        TextView tvTen;
        LinearLayout lnChonMon;

        public DatMonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenMon_Cardview_datMon);
            lnChonMon = itemView.findViewById(R.id.linearChonMon);
        }
    }
}
