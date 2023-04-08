package sp23cp18103.nhom2.finedining.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.InterfaceDatMon;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.DatMonDAO;
import sp23cp18103.nhom2.finedining.model.DatMon;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.ThongTinDatMon;
import sp23cp18103.nhom2.finedining.utils.NumberHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Adapter để hiển thị danh sách đặt món trong hóa đơn chi tiết
 * Đặt bàn chỉ cần hiển thị list các vị trí là được nên không cần adapter
 * */
public class SuaDatMonAdapter extends RecyclerView.Adapter<SuaDatMonAdapter.DatMonViewHolder> {
    Context context;
    List<Mon> monList;
    List<ThongTinDatMon> listDatMonMoi;
    List<ThongTinDatMon> listDatMonCu;
    InterfaceDatMon interfaceDatMon;

    int maHD;

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public SuaDatMonAdapter(Context context, List<Mon> monList, List<ThongTinDatMon> listDatMonCu, InterfaceDatMon interfaceDatMon) {
        this.context = context;
        this.monList = monList;
        this.listDatMonCu = listDatMonCu;
        this.interfaceDatMon = interfaceDatMon;
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
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPrefSaveListSua", Context.MODE_PRIVATE);
        String jsonListDatMon = sharedPreferences.getString("listSuaMon", "");
        Type type = new TypeToken<ArrayList<ThongTinDatMon>>(){}.getType();
        listDatMonMoi = new Gson().fromJson(jsonListDatMon, type);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Mon mon = monList.get(position);
        holder.tvTenMon.setText(mon.getTenMon());
        holder.tvGia.setText(NumberHelper.getNumberWithDecimal(mon.getGia()) + " vnd");

        if (listDatMonCu != null) {
            for (int i = 0; i < listDatMonCu.size(); i++){
                DatMon datMon = listDatMonCu.get(i);
                if (listDatMonCu.get(i).getTenMon().equalsIgnoreCase(mon.getTenMon())){
                    holder.edSoLuongMon.setText("" + datMon.getSoLuong());
                    interfaceDatMon.getMaMon(mon.getMaMon(), String.valueOf(datMon.getSoLuong()));
                }
            }
        }

        if (listDatMonMoi != null){
            for (ThongTinDatMon datMon : listDatMonMoi) {
                if (datMon.getMaHD() == maHD) {
                    if (datMon.getMaHD() == maHD && datMon.getTenMon().equalsIgnoreCase(mon.getTenMon())) {
                        holder.edSoLuongMon.setText("" + datMon.getSoLuong());
                        interfaceDatMon.getMaMon(mon.getMaMon(), String.valueOf(datMon.getSoLuong()));
                    }
                } else if (maHD != sharedPreferences.getInt("maHD", 0)) {
                    editor.remove("listSuaMon");
                    editor.apply();
                }
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
                String text = holder.edSoLuongMon.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    if (interfaceDatMon != null) {
                        interfaceDatMon.getMaMon(mon.getMaMon(), text);
                    }
                } else {
                    holder.edSoLuongMon.removeTextChangedListener(this);
                    holder.edSoLuongMon.setText(""); // set dữ liệu ở đây
                    holder.edSoLuongMon.addTextChangedListener(this);
                }
            }



        });
    }

    @Override
    public int getItemCount() {
        return monList.size();
    }

    class DatMonViewHolder extends RecyclerView.ViewHolder{
        LinearLayout lnChonMon;
        EditText edSoLuongMon;
        TextView tvTenMon,tvGia;
        public DatMonViewHolder(@NonNull View itemView) {
            super(itemView);
            edSoLuongMon = itemView.findViewById(R.id.ed_SoLuong_MonDat);
            lnChonMon = itemView.findViewById(R.id.linearChonMon);
            tvTenMon = itemView.findViewById(R.id.tvTenMon_Cardview_datMon);
            tvGia = itemView.findViewById(R.id.tvGia_Cardview_datMon);
        }

    }

}
