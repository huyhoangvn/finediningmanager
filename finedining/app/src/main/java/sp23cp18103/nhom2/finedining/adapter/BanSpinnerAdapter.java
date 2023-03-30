package sp23cp18103.nhom2.finedining.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.model.LoaiBan;

public class BanSpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<LoaiBan> list;

    public BanSpinnerAdapter(Context context, ArrayList<LoaiBan> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_spinner_loaiban, parent, false);
            viewHolder.tvMaBanSP = convertView.findViewById(R.id.tvMaBanSP);
            viewHolder.tvTenBanSP = convertView.findViewById(R.id.tvTenBanSP);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiBan loaiBan = list.get(position);

        viewHolder.tvMaBanSP.setText(String.valueOf(loaiBan.getMaLB()));
        viewHolder.tvTenBanSP.setText(loaiBan.getTenLoai());
        return convertView;
    }
    public final class ViewHolder{
        TextView tvMaBanSP, tvTenBanSP;

    }

}
