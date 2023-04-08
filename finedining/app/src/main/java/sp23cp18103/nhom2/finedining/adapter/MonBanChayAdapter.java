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
import sp23cp18103.nhom2.finedining.model.ThongTinMon;
import sp23cp18103.nhom2.finedining.utils.ImageHelper;
import sp23cp18103.nhom2.finedining.utils.NumberHelper;

/*
 * Adapter để hiển thị danh sách món bán chạy trong ThongKeMonFragment
 * */
public class MonBanChayAdapter extends RecyclerView.Adapter<MonBanChayAdapter.ViewHolder>{
    Context context;
    List<ThongTinMon> list;

    public MonBanChayAdapter(Context context, List<ThongTinMon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.cardview_thongke_mon, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThongTinMon tinMon = list.get(position);
        int itemIndex = holder.getAdapterPosition();
        holder.tvTenMon.setText(tinMon.getTenMonThongKe());
        holder.tvSoLuong.setText("" + tinMon.getSoLuongMon());
        holder.tvDoanhThu.setText("" + NumberHelper.getNumberWithDecimal(tinMon.getDoanhThuMon()));
        holder.tvSoTT.setText("" + (itemIndex + 1));
        ImageHelper.loadAvatar(context,holder.imgAnhMon,tinMon.getHinhMon());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenMon,tvSoLuong,tvDoanhThu,tvSoTT;
        ImageView imgAnhMon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenMon = itemView.findViewById(R.id.tv_name_monhot);
            tvSoLuong = itemView.findViewById(R.id.tv_soluong_monhot);
            tvDoanhThu = itemView.findViewById(R.id.tv_doanhthu_monhot);
            tvSoTT = itemView.findViewById(R.id.tv_xephang_monhot);
            imgAnhMon = itemView.findViewById(R.id.img_monhot);
        }
    }
}
