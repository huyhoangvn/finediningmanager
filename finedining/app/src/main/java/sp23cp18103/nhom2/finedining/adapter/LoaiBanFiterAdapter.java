package sp23cp18103.nhom2.finedining.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.ITLoaiBanFilter;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.model.LoaiBan;

public class LoaiBanFiterAdapter extends RecyclerView.Adapter<LoaiBanFiterAdapter.filterViewHolder> {
    Context context;
    List<String> list;
    ITLoaiBanFilter itLoaiBanFilter;
    LoaiBan loaiBan;
    public LoaiBanFiterAdapter(Context context, List<String> list, ITLoaiBanFilter itLoaiBanFilter) {
        this.context = context;
        this.list = list;
        this.itLoaiBanFilter = itLoaiBanFilter;
    }

    @NonNull
    @Override
    public filterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_filter_loaiban,parent,false);
        return new filterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull filterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvFilterLoaiBan.setText(list.get(position));

        holder.tvFilterLoaiBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    itLoaiBanFilter.loaiBan(list.get(position),holder);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class filterViewHolder extends RecyclerView.ViewHolder {
       public TextView tvFilterLoaiBan;
        public filterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFilterLoaiBan=itemView.findViewById(R.id.tv_filterloaiban);
        }
    }
}
