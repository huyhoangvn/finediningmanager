package sp23cp18103.nhom2.finedining.adapter;

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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.LoaiBanDAO;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.fragment.LoaiBanFragment;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.utils.ColorHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Adapter để hiển thị danh sách loại bàn trong LoaiBanFragment
 * */
public class LoaiBanAdapter extends RecyclerView.Adapter<LoaiBanAdapter.LoaiBanViewHolder> {
    Context context;
    List<LoaiBan> mListLoaiBan;
    LoaiBanFragment fragment;
    TextInputEditText edTenLoaiBan;
    TextInputEditText edSoChoNgoi;
    CheckBox chkTrangThaiLoaiBan;
    AppCompatButton btn_ShaveLoaiBan, btn_CancelLoaiBan;
    LoaiBanDAO dao;
    NhanVienDAO nhanVienDAO;
    TextInputLayout input_tenLB;
    public LoaiBanAdapter(List<LoaiBan> mListLoaiBan, Context context) {
        this.mListLoaiBan = mListLoaiBan;
        this.context = context;
    }

    @NonNull
    @Override
    public LoaiBanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_loai_ban, parent, false);
        return new LoaiBanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiBanViewHolder holder, int position) {
        anChucNang(holder);
        LoaiBan loaiBan = mListLoaiBan.get(position);
        dao = new LoaiBanDAO(context);
        int tongSoBan = dao.getTongBan(loaiBan.getMaLB(), PreferencesHelper.getId(context));
        int soBanDay = dao.getSoLuongBan(loaiBan.getMaLB(), PreferencesHelper.getId(context));

        holder.tv_TenLoaiBan.setText(loaiBan.getTenLoai());
        holder.tv_SoBan.setText(String.valueOf(tongSoBan));
        holder.tv_TrangThai_LoaiBan.setText(String.valueOf(loaiBan.getTrangThai()));

        holder.tv_SoBanTrongBan.setText(String.valueOf(tongSoBan - soBanDay));

        if (loaiBan.getTrangThai() == 1) {
            holder.tv_TrangThai_LoaiBan.setText("Dùng");
            holder.tv_TrangThai_LoaiBan.setTextColor(ColorHelper.getPositiveColor(context));
        } else {
            holder.tv_TrangThai_LoaiBan.setText("Không dùng");
            holder.tv_TrangThai_LoaiBan.setTextColor(ColorHelper.getNegativeColor(context));
        }

        holder.img_Sua_LoaiBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_loai_ban, null);
                builder.setView(view);
                TextView tv_tieude_loaiban = view.findViewById(R.id.tvTieuDeLoaiBan);
                tv_tieude_loaiban.setText("Sửa loại bàn");

                edTenLoaiBan = view.findViewById(R.id.edTenLoaiBan);
                chkTrangThaiLoaiBan = view.findViewById(R.id.chkTrangThaiLoaiBan);
                btn_ShaveLoaiBan = view.findViewById(R.id.btn_ShaveLoaiBan);
                btn_CancelLoaiBan = view.findViewById(R.id.btn_CancelLoaiBan);
                input_tenLB = view.findViewById(R.id.input_tenLB);
                edTenLoaiBan.setText(loaiBan.getTenLoai());

                Dialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                if (loaiBan.getTrangThai() == 1) {
                    chkTrangThaiLoaiBan.setChecked(true);
                } else {
                    chkTrangThaiLoaiBan.setChecked(false);
                }
                btn_CancelLoaiBan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }

                });
                btn_ShaveLoaiBan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenLoai = edTenLoaiBan.getText().toString().trim();
                        if (tenLoai.isEmpty()) {
                            input_tenLB.setError("Không được để trống");

                            return;
                        }
                        // thuc hien chuc nang
                        int maNV = PreferencesHelper.getId(context);
                        loaiBan.setTenLoai(tenLoai);
                        if (chkTrangThaiLoaiBan.isChecked()) {
                            loaiBan.setTrangThai(1);
                        } else {
                            if (dao.getlienKetTrangThai(loaiBan.getMaLB(), maNV) > 0) {
                                Toast.makeText(context, "Còn tồn tại bàn", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                loaiBan.setTrangThai(0);
                            }
                        }
//                            loaiBan.setSoChoNgoi(Integer.parseInt(edSoChoNgoi.getText().toString()));
                        if (dao.updateloaiban(loaiBan) > 0) {
                            Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Update không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListLoaiBan.size();
    }

    public class LoaiBanViewHolder extends RecyclerView.ViewHolder {
        TextView tv_TenLoaiBan, tv_TrangThai_LoaiBan, tv_SoBanTrongBan, tv_SoBan;
        ImageButton img_Sua_LoaiBan;

        public LoaiBanViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_TenLoaiBan = itemView.findViewById(R.id.tv_TenLoaiBan);
            tv_SoBan = itemView.findViewById(R.id.tv_SoBan);
            tv_SoBanTrongBan = itemView.findViewById(R.id.tv_SoBanTrongBan);
            tv_TrangThai_LoaiBan = itemView.findViewById(R.id.tv_TrangThai_LoaiBan);
            img_Sua_LoaiBan = itemView.findViewById(R.id.img_Sua_LoaiBan);

        }
    }

    void anChucNang(LoaiBanViewHolder holder){
        nhanVienDAO = new NhanVienDAO(context);
        int phanQuyen = nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(context));
        if(phanQuyen == 0){
            holder.img_Sua_LoaiBan.setVisibility(View.GONE);
        }
    }
}
