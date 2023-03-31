package sp23cp18103.nhom2.finedining.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.InterfaceDatMon;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.DatMonDAO;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.ThongTinDatMon;

/*
 * Adapter để hiển thị danh sách đặt món trong hóa đơn chi tiết
 * Đặt bàn chỉ cần hiển thị list các vị trí là được nên không cần adapter
 * */
public class SuaDatMonAdapter extends RecyclerView.Adapter<SuaDatMonAdapter.DatMonViewHolder> {
    Context context;
    List<Mon> monList;
    List<ThongTinDatMon> listThongTinMon;
    InterfaceDatMon interfaceDatMon;

    ThongTinDatMon thongTinDatMon;


    int maHD;

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public SuaDatMonAdapter(Context context, List<Mon> monList, InterfaceDatMon interfaceDatMon) {
        this.context = context;
        this.monList = monList;
        this.interfaceDatMon = interfaceDatMon;
    }

    public void setListThongTinMon(List<ThongTinDatMon> listThongTinMon) {
        this.listThongTinMon = listThongTinMon;
    }


    public SuaDatMonAdapter(List<ThongTinDatMon> listThongTinMon) {
        this.listThongTinMon = listThongTinMon;
    }

    public  List<ThongTinDatMon> getListThongTinMon() {
        return listThongTinMon;
    }

    @NonNull
    @Override
    public DatMonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_chonmon,parent, false);
        SuaDatMonAdapter.DatMonViewHolder viewHolder = new SuaDatMonAdapter.DatMonViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DatMonViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DatMonDAO datMonDAO = new DatMonDAO(context);
        List<ThongTinDatMon> list =  datMonDAO.getDatMonTheoHoaDon(maHD);
        Mon mon = monList.get(position);
        holder.tvTen.setText("" + mon.getTenMon());
        holder.tvGia.setText("" + mon.getGia());
        list.toString();
            for (int i = 0; i < list.size(); i++){
                if (list.get(i).getTenMon().equalsIgnoreCase(mon.getTenMon())){
                    holder.edSoLuongMon.setText("" + list.get(i).getSoLuong());
                }
        }




        holder.edSoLuongMon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Trước khi thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Đang thay đổi văn bản
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Sau khi thay đổi văn bản
//                for (int i = 0; i < list.size(); i++){
//                    if (list.get(i).getTenMon().equalsIgnoreCase(mon.getTenMon())){
//                        holder.edSoLuongMon.setText("" + list.get(i).getSoLuong());
//                    }
//                }
                String text = holder.edSoLuongMon.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    if (interfaceDatMon != null) {
                        interfaceDatMon.getMaMon(mon.getMaMon(), text);
                    }
                }
                else {
//                     Xóa văn bản đã thay đổi trước đó
                    holder.edSoLuongMon.removeTextChangedListener(this);
                    holder.edSoLuongMon.setText("");
                    holder.edSoLuongMon.addTextChangedListener(this);
                }
            }
        });

       listThongTinMon = getListThongTinMon();
        if (listThongTinMon != null){
             listThongTinMon.toString();
        }
    }

    @Override
    public int getItemCount() {
        return monList.size();
    }

    class DatMonViewHolder extends RecyclerView.ViewHolder{
        TextView tvTen,tvGia;
        LinearLayout lnChonMon;
        EditText edSoLuongMon;

        public DatMonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenMon_Cardview_datMon);
            tvGia = itemView.findViewById(R.id.tvGia_Cardview_datMon);
            edSoLuongMon = itemView.findViewById(R.id.ed_SoLuong_MonDat);
            lnChonMon = itemView.findViewById(R.id.linearChonMon);
        }
    }
}