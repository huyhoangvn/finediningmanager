package sp23cp18103.nhom2.finedining.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.model.LoaiMon;

public class LoaiMonFilterAdapter extends RecyclerView.Adapter<LoaiMonFilterAdapter.filterViewHolder> {
    Context context;
    List<String> list;
    LoaiMonDAO dao ;

    public LoaiMonFilterAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public filterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filter_loaimon,parent, false);
        return new filterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull filterViewHolder holder, int position) {
        holder.tvFilterLoaiMon.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class filterViewHolder extends RecyclerView.ViewHolder {
        TextView tvFilterLoaiMon;

        public filterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFilterLoaiMon = itemView.findViewById(R.id.tvFilterLoaiMon);
        }
    }
}
