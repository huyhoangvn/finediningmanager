package sp23cp18103.nhom2.finedining.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.ILoaiMonFilter;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.model.LoaiMon;

public class LoaiMonFilterAdapter extends RecyclerView.Adapter<LoaiMonFilterAdapter.FilterViewHolder>{
    Context context;
    List<String> list;
    LoaiMonDAO dao ;
    ILoaiMonFilter iLoaiMonFilter;

    public LoaiMonFilterAdapter(Context context, List<String> list, ILoaiMonFilter iLoaiMonFilter) {
        this.context = context;
        this.list = list;
        this.iLoaiMonFilter = iLoaiMonFilter;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filter_loaimon,parent, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvFilterLoaiMon.setText(list.get(position));


            holder.tvFilterLoaiMon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        iLoaiMonFilter.locMon(list.get(position), holder);
                }
            });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFilterLoaiMon;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFilterLoaiMon = itemView.findViewById(R.id.tvFilterLoaiMon);
        }
    }
}
