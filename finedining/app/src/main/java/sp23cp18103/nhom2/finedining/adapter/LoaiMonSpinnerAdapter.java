package sp23cp18103.nhom2.finedining.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.model.LoaiMon;

public class LoaiMonSpinnerAdapter extends ArrayAdapter<LoaiMon> {
    Context context;
    ArrayList<LoaiMon> list;
    TextView tvSpinnerMaLoaiMon, tvSpinnerTenLoaiMon;
    public LoaiMonSpinnerAdapter(@NonNull Context context, ArrayList<LoaiMon> list) {
        super(context,0 , list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spinner_loaimon, parent, false);
        }
        LoaiMon lm = list.get(position);
        if(lm!=null){
            tvSpinnerMaLoaiMon = v.findViewById(R.id.tvSpinnerMaLoaiMon);
            tvSpinnerMaLoaiMon.setText(String.valueOf(position + 1));
            tvSpinnerTenLoaiMon = v.findViewById(R.id.tvSpinnerTenLoaiMon);
            tvSpinnerTenLoaiMon.setText(lm.getTenLoai());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spinner_loaimon, parent, false);
        }
        LoaiMon lm = list.get(position);
        if(lm!=null){
            tvSpinnerMaLoaiMon = v.findViewById(R.id.tvSpinnerMaLoaiMon);
            tvSpinnerMaLoaiMon.setText(String.valueOf(position+1));
            tvSpinnerTenLoaiMon = v.findViewById(R.id.tvSpinnerTenLoaiMon);
            tvSpinnerTenLoaiMon.setText(lm.getTenLoai());
        }
        return v;
    }
}
