package sp23cp18103.nhom2.finedining.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.LoaiMon;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Adapter để hiển thị danh sách món trong MonFragment
 * */
public class MonAdapter extends RecyclerView.Adapter<MonAdapter.MonViewHolder>{
    List<Mon> list, list2;
    LoaiMonSpinnerAdapter loaiMonSpinnerAdapter;
    Context context;
    MonDAO dao;
    LoaiMonDAO loaiMonDAO;
    ArrayList<LoaiMon> listLoaiMon;
    int maLoaiMon, positionLM;

    public MonAdapter( Context context, List<Mon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_mon, null);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
        hideFloatingButton(holder);
        Mon m =list.get(position);
        dao = new MonDAO(context);
        holder.tvCardviewTenMon.setText(m.getTenMon());
        loaiMonDAO = new LoaiMonDAO(context);
        LoaiMon lm = loaiMonDAO.getId(String.valueOf(m.getMaLM()));
        holder.tvCardviewTenLoaiMon.setText(lm.getTenLoai());
        holder.tvCardviewGiaMon.setText(m.getGia()+"vnđ");
        if(m.getTrangThai()==1){
            holder.tvCardviewTrangThaiMon.setText("Còn dùng");
            holder.tvCardviewTrangThaiMon.setTextColor(Color.BLUE);
        }else{
            holder.tvCardviewTrangThaiMon.setText("Không dùng");
            holder.tvCardviewTrangThaiMon.setTextColor(Color.RED);
        }

        holder.imgcardviewSuaMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater=((Activity)context).getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_mon,null);
                builder.setView(view);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView tv_tieude_mon = view.findViewById(R.id.tvTieuDeMon);
                TextInputEditText edDialogTenMon = view.findViewById(R.id.edDialogTenMon);
                TextInputEditText edDialogGia = view.findViewById(R.id.edDialogGia);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                Spinner spnrialogLoaiMon = view.findViewById(R.id.spnrDialogLoaiMon);
                CheckBox chkTrangThaiMon = view.findViewById(R.id.chkTrangThaiMon);
                Button btnDialogLuuMon = view.findViewById(R.id.btnDialogLuuMon);
                Button btnDialogHuyMon = view.findViewById(R.id.btnDialogHuyMon);
                ImageButton imgDialogMon = view.findViewById(R.id.imgDialogMon);
                tv_tieude_mon.setText("Sửa món");
                edDialogTenMon.setText(m.getTenMon());
                edDialogGia.setText(String.valueOf(m.getGia()));
                imgDialogMon.setImageResource(R.drawable.default_avatar);
                Dialog dialog = builder.create();
                if(m.getTrangThai()==1){
                    chkTrangThaiMon.setChecked(true);
                }else{
                    chkTrangThaiMon.setChecked(false);
                }
                listLoaiMon = (ArrayList<LoaiMon>) loaiMonDAO.getAllLoaiMon();
                loaiMonSpinnerAdapter = new LoaiMonSpinnerAdapter(builder.getContext(), listLoaiMon);
                spnrialogLoaiMon.setAdapter(loaiMonSpinnerAdapter);
                for(int i = 0; i<listLoaiMon.size(); i++){
                    if(m.getMaMon() == (listLoaiMon.get(i).getMaLM())){
                        positionLM = i;
                    }
                }
                spnrialogLoaiMon.setSelection(positionLM);
                spnrialogLoaiMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maLoaiMon = listLoaiMon.get(position).getMaLM();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnDialogLuuMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenMon = edDialogTenMon.getText().toString().trim();
                        String giaMon = edDialogGia.getText().toString().trim();
                        m.setTenMon(tenMon);
                        m.setGia(Integer.parseInt(giaMon));
                        m.setHinh(String.valueOf(R.drawable.default_avatar));
                        m.setMaLM(maLoaiMon);
                        if(chkTrangThaiMon.isChecked()){
                            m.setTrangThai(1);
                        }else{
                            m.setTrangThai(0);
                        }
                        for(int i = 0; i<listLoaiMon.size(); i++){
                            if(m.getMaMon() == (listLoaiMon.get(i).getMaLM())){
                                positionLM = i;
                            }
                        }
                        spnrialogLoaiMon.setSelection(positionLM);
                        if(tenMon.isEmpty() && giaMon.isEmpty()){
                            edDialogTenMon.setError("Không được để trống");
                            edDialogGia.setError("Không được để trống");
                            return;
                        }else{
                            try {
                                Integer.parseInt(edDialogGia.getText().toString());
                            }catch (Exception e){
                                edDialogGia.setError("Giá không hợp lệ");
                                return;
                            }
                            if(dao.updateMon(m)>0){
                                Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                btnDialogHuyMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MonViewHolder extends RecyclerView.ViewHolder {
        TextView tvCardviewTenMon,tvCardviewTenLoaiMon,tvCardviewGiaMon,tvCardviewTrangThaiMon;
        ImageView imgCardviewMon, imgcardviewSuaMon;
        public MonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardviewTenMon = itemView.findViewById(R.id.tvCardviewTenMon);
            tvCardviewTenLoaiMon = itemView.findViewById(R.id.tvCardviewTenLoaiMon);
            tvCardviewGiaMon = itemView.findViewById(R.id.tvCardviewGiaMon);
            tvCardviewTrangThaiMon = itemView.findViewById(R.id.tvCardviewTrangThaiMon);
            imgCardviewMon = itemView.findViewById(R.id.imgCardviewMon);
            imgcardviewSuaMon = itemView.findViewById(R.id.imgcardviewSuaMon);

        }
    }

    void hideFloatingButton(MonViewHolder holder){
        NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
        int maNV = PreferencesHelper.getId(context);
        int chuVu = nhanVienDAO.getPhanQuyen(maNV);
        if (chuVu == 1){
            holder.imgcardviewSuaMon.setVisibility(View.VISIBLE);
        }else {
            holder.imgcardviewSuaMon.setVisibility(View.GONE);
        }
    }
}
