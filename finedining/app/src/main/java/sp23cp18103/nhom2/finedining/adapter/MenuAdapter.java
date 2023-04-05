package sp23cp18103.nhom2.finedining.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.utils.ImageHelper;
import sp23cp18103.nhom2.finedining.utils.NumberHelper;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    List<Mon> listMon;
    Context context;

    public MenuAdapter(List<Mon> listMon, Context context) {
        this.listMon = listMon;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.carview_menu,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mon mon = listMon.get(position);
        holder.tvTenMon.setText(mon.getTenMon());
        holder.tvGia.setText(NumberHelper.getNumberWithDecimal(mon.getGia()) + " VND");
        ImageHelper.loadAvatar(context,holder.imgAnhMon,mon.getHinh());
    }
    @Override
    public int getItemCount() {
        return listMon.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnhMon;
        TextView tvTenMon,tvGia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhMon = itemView.findViewById(R.id.img_anhMon);
            tvTenMon = itemView.findViewById(R.id.tv_tenMon);
            tvGia = itemView.findViewById(R.id.tv_giaMon);
        }

    }
}
