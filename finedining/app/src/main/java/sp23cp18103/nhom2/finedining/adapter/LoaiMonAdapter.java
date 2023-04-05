package sp23cp18103.nhom2.finedining.adapter;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.fragment.LoaiMonFragment;
import sp23cp18103.nhom2.finedining.model.LoaiMon;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Adapter để hiển thị danh sách loại món trong LoaiMonFragment
 * */
public class LoaiMonAdapter extends RecyclerView.Adapter<LoaiMonAdapter.loaiMonViewHolder>{
    Context context;
    List<LoaiMon> list;
    TextInputEditText edTenLoaiMon;
    Button btnDialogLuuLoaiMon, btnDialogHuyLoaiMon;
    CheckBox chkDialogTrangThaiLoaiMon;
    LoaiMonDAO dao;
    TextInputLayout inputDialogTenLoaiMon;


    public LoaiMonAdapter(Context context, List<LoaiMon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public loaiMonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_loai_mon,parent, false);
        return new loaiMonViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull loaiMonViewHolder holder, int position) {
        LoaiMon lm = list.get(position);
        dao = new LoaiMonDAO(context);
        holder.tvtenLoaiMon.setText(lm.getTenLoai());
        if(lm.getTrangThai()==1){
            holder.tvTrangThai.setText("Còn dùng");
            holder.tvTrangThai.setTextColor(Color.BLUE);
        }else{
            holder.tvTrangThai.setText("Không dùng");
            holder.tvTrangThai.setTextColor(Color.RED);
        }
//        int maLM = lm.getMaLM();
        int maNV = PreferencesHelper.getId(context);
        holder.tvCardviewSoMon.setText( String.valueOf(dao.getSoLuongMon(maNV, lm.getMaLM())));
        holder.imgSuaTenLoaiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater=((Activity)context).getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_loai_mon,null);
                builder.setView(view);
                edTenLoaiMon = view.findViewById(R.id.edTenLoaiMon);
                TextView tvTieuDeLoaiMon = view.findViewById(R.id.tvTieuDeLoaiMon);
                tvTieuDeLoaiMon.setText("Sửa loại món");
                chkDialogTrangThaiLoaiMon = view.findViewById(R.id.chkDialogTrangThaiLoaiMon);
                btnDialogLuuLoaiMon = view.findViewById(R.id.btnDialogLuuLoaiMon);
                btnDialogHuyLoaiMon = view.findViewById(R.id.btnDialogHuyLoaiMon);
                inputDialogTenLoaiMon = view.findViewById(R.id.inputDialogTenLoaiMon);
                edTenLoaiMon.setText(lm.getTenLoai());
                Dialog dialog= builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                int maNV = PreferencesHelper.getId(context);
                if(lm.getTrangThai()==1){
                    chkDialogTrangThaiLoaiMon.setChecked(true);
                }else{
                    chkDialogTrangThaiLoaiMon.setChecked(false);
                }
                btnDialogLuuLoaiMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenLoai = edTenLoaiMon.getText().toString().trim();
                        lm.setTenLoai(tenLoai);
                        if(chkDialogTrangThaiLoaiMon.isChecked()){
                            Log.d("TAG", "onClick: " + "Checked");
                            lm.setTrangThai(1);
                        }else{
                            Log.d("TAG", "onClick: " + dao.getLienKetTrangThai(lm.getMaLM(),maNV));
                            if(dao.getLienKetTrangThai(lm.getMaLM(),maNV)>0){
                                Log.d("TAG", "onClick: " + "Un Checked 1");
                                Toast.makeText(context, "Khong cho sua", Toast.LENGTH_SHORT).show();
                                return;
                            }else{
                                Log.d("TAG", "onClick: " + "Un Checked 0");
                                lm.setTrangThai(0);
                            }
                        }
                        if(tenLoai.isEmpty()){
                            inputDialogTenLoaiMon.setError("Không được để trống");
                            return;
                        }else{
                            if(dao.updateLoaiMon(lm)>0){
                                Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }else{
                                Toast.makeText(context, "Update không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
                btnDialogHuyLoaiMon.setOnClickListener(new View.OnClickListener() {
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





    class loaiMonViewHolder extends RecyclerView.ViewHolder {
        TextView tvtenLoaiMon, tvTrangThai, tvCardviewSoMon;
        ImageView imgSuaTenLoaiMon;

        public loaiMonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtenLoaiMon = itemView.findViewById(R.id.tvCarviewTenLoaiMon);
            imgSuaTenLoaiMon = itemView.findViewById(R.id.imgCardviewSuaLoaiMon);
            tvTrangThai = itemView.findViewById(R.id.tvCardviewTrangThaiLM);
            tvCardviewSoMon = itemView.findViewById(R.id.tvCardviewSoMon);
        }
    }


}
