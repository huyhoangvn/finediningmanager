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
import sp23cp18103.nhom2.finedining.database.HoaDonDAO;
import sp23cp18103.nhom2.finedining.fragment.ThemHoaDonFragment;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.ThongTinDatMon;
import sp23cp18103.nhom2.finedining.utils.NumberHelper;

/*
 * Adapter để hiển thị danh sách đặt món trong hóa đơn chi tiết
 * Đặt bàn chỉ cần hiển thị list các vị trí là được nên không cần adapter
 * */
public class DatMonAdapter extends RecyclerView.Adapter<DatMonAdapter.DatMonViewHolder> {
    Context context;
    List<Mon> monList;
    List<ThongTinDatMon> listThongTinMon;
    InterfaceDatMon interfaceDatMon;

    ThongTinDatMon thongTinDatMon;

    HoaDonDAO hoaDonDAO;

    List<ThongTinDatMon> listDatMonMoi;


    int maHD;

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public DatMonAdapter(Context context, List<Mon> monList, InterfaceDatMon interfaceDatMon) {
        this.context = context;
        this.monList = monList;
        this.interfaceDatMon = interfaceDatMon;
    }

    public void setListThongTinMon(List<ThongTinDatMon> listThongTinMon) {
        this.listThongTinMon = listThongTinMon;
    }


    public DatMonAdapter(List<ThongTinDatMon> listThongTinMon) {
        this.listThongTinMon = listThongTinMon;
    }

    public  List<ThongTinDatMon> getListThongTinMon() {
        return listThongTinMon;
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
        Mon mon = monList.get(position);
        holder.tvTenMon.setText(mon.getTenMon());
        holder.tvGia.setText(NumberHelper.getNumberWithDecimal(mon.getGia()) + " VND");
        holder.edSoLuongMon.setText("0");
        hoaDonDAO = new HoaDonDAO(context);
        int maHoaDonSapThem = hoaDonDAO.getMaHoaDonTiepTheo();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPrefSaveListDat", Context.MODE_PRIVATE);
        String jsonListDatMon = sharedPreferences.getString("listDatMon", "");
        Type type = new TypeToken<ArrayList<ThongTinDatMon>>(){}.getType();
        listDatMonMoi = new Gson().fromJson(jsonListDatMon, type);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (listDatMonMoi != null){
            for (ThongTinDatMon datMon : listDatMonMoi) {
                if (datMon.getMaHD() == maHoaDonSapThem) {
                    if (datMon.getMaHD() == maHoaDonSapThem && datMon.getTenMon().equalsIgnoreCase(mon.getTenMon())) {
                        holder.edSoLuongMon.setText("" + datMon.getSoLuong());
                        interfaceDatMon.getMaMon(mon.getMaMon(), String.valueOf(datMon.getSoLuong()));
                    }
                } else if (maHoaDonSapThem != sharedPreferences.getInt("maHD", 0)) {
                    editor.remove("listDatMon");
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
                String text = s.toString();
                if (!TextUtils.isEmpty(text)) {
                    if (interfaceDatMon != null) {
                        interfaceDatMon.getMaMon(mon.getMaMon(), text);
                    }
                }
                else {
                    // Xóa văn bản đã thay đổi trước đó
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
        TextView tvTenMon,tvGia;

        LinearLayout lnChonMon;
        EditText edSoLuongMon;

        public DatMonViewHolder(@NonNull View itemView) {
            super(itemView);
            edSoLuongMon = itemView.findViewById(R.id.ed_SoLuong_MonDat);
            lnChonMon = itemView.findViewById(R.id.linearChonMon);
            tvTenMon = itemView.findViewById(R.id.tvTenMon_Cardview_datMon);
            tvGia = itemView.findViewById(R.id.tvGia_Cardview_datMon);
        }
    }
}
