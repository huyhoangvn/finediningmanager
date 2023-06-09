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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.BanDAO;
import sp23cp18103.nhom2.finedining.database.DatBanDAO;
import sp23cp18103.nhom2.finedining.database.LoaiBanDAO;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.utils.ColorHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;
import sp23cp18103.nhom2.finedining.utils.ValueHelper;

/*
 * Adapter để hiển thị danh sách bàn trong BanFragment
 * */
public class BanAdapter extends RecyclerView.Adapter<BanAdapter.BanViewHolder> {
    Context context;
    List<Ban> list;
    BanDAO banDAO;
    EditText edViTriBan;
    CheckBox chkTrangThaiBan;
    AppCompatButton btnShaveBan, btnCancelBan;
    Spinner spnrBan;
    LoaiBanDAO loaiBanDAO;
    LoaiBan loaiBan;
    BanSpinnerAdapter banSpinnerAdapter;
    DatBanDAO datBanDAO;
    NhanVienDAO nhanVienDAO;
    TextInputLayout input_viTriBan;
    public BanAdapter(Context context, List<Ban> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_ban, parent, false);
        return new BanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BanViewHolder holder, int position) {
        anChucNang(holder);
        int manv = PreferencesHelper.getId(context);
        Ban ban = list.get(position);
        datBanDAO = new DatBanDAO(context);
        LoaiBanDAO loaiBanDAO = new LoaiBanDAO(context);
        loaiBan = loaiBanDAO.getID(ban.getMaLB());
        banDAO = new BanDAO(context);
        holder.tvViTri.setText(ban.getViTri());
        holder.tvLoaiBan.setText("" + loaiBan.getTenLoai());
        holder.tvTrangThaiDung.setText(ban.getTenTrangThai());
        if (ban.getTrangThai() == 1) {
            holder.tvTrangThaiDung.setTextColor(ColorHelper.getPositiveColor(context));
        } else {
            holder.tvTrangThaiDung.setTextColor(ColorHelper.getNegativeColor(context));
        }
//        holder.tvTrangThaiBan.setText((banDAO.getKiemTraConTrong(position)==1) ?"Trống":"Đầy");

        if (banDAO.getKiemTraConTrong(manv, ban.getMaBan()) == 1) {
            holder.tvTrangThaiBan.setText("Đầy");
            holder.tvTrangThaiBan.setTextColor(ColorHelper.getNegativeColor(context));
        } else {
            holder.tvTrangThaiBan.setText("Trống");
            holder.tvTrangThaiBan.setTextColor(ColorHelper.getNeutralColor(context));
        }

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goidialog(ban);
            }
        });

    }

    void goidialog(Ban ban) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ban, null);
        builder.setView(view);
        TextView tvTieuDeBan = view.findViewById(R.id.tvTieuDeBan);
        tvTieuDeBan.setText("Sửa bàn");

        banDAO = new BanDAO(context);

        edViTriBan = view.findViewById(R.id.edViTriBan);
        spnrBan = view.findViewById(R.id.spnrBan);
        chkTrangThaiBan = view.findViewById(R.id.chkTrangThaiBan);
        btnShaveBan = view.findViewById(R.id.btnShaveBan);
        btnCancelBan = view.findViewById(R.id.btnCancelBan);
        input_viTriBan = view.findViewById(R.id.input_ViTriBan);
        edViTriBan.setText(ban.getViTri());

        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (ban.getTrangThai() == 1) {
            chkTrangThaiBan.setChecked(true);
        } else {
            chkTrangThaiBan.setChecked(false);
        }
        evSpiner(ban);

        edViTriBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_viTriBan.setError(null);
            }
        });
        btnCancelBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });

        btnShaveBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_viTriBan.setError(null);
                String viTri = edViTriBan.getText().toString().trim();
                if (viTri.isEmpty()) {
                    input_viTriBan.setError("Không được để trống");
                    return;
                }
                if (viTri.length() > ValueHelper.MAX_INPUT_NAME) {
                    input_viTriBan.setError("Nhập tối đa " + ValueHelper.MAX_INPUT_NAME + " kí tự");
                    return;
                }
                // thuc hien chuc nang
                int manv = PreferencesHelper.getId(context);
                LoaiBan loaiBan = (LoaiBan) spnrBan.getSelectedItem();
                ban.setMaLB(loaiBan.getMaLB());
                ban.setViTri(viTri);

                if (chkTrangThaiBan.isChecked()) {
                    ban.setTrangThai(1);
                } else {
                    ban.setTrangThai(0);
                }
                ban.setViTri((edViTriBan.getText().toString().trim()));
                if (banDAO.updateban(ban) > 0) {
                    Toast.makeText(context, "Sửa bàn thành công", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Sửa bàn không thành công", Toast.LENGTH_SHORT).show();
                }

                if (ban.getTrangThai()==0){
                    datBanDAO.getHuyTrangThaiDatBan(ban.getMaBan(),manv);
                }
            }

        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BanViewHolder extends RecyclerView.ViewHolder {
        TextView tvViTri, tvLoaiBan, tvTrangThaiBan, tvTrangThaiDung;
        ImageButton imgSua;

        public BanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvViTri = itemView.findViewById(R.id.tvViTri);
            tvLoaiBan = itemView.findViewById(R.id.tvLoaiBan);
            tvTrangThaiBan = itemView.findViewById(R.id.tvTrangThai);
            imgSua = itemView.findViewById(R.id.imgSua);
            tvTrangThaiDung = itemView.findViewById(R.id.tvTrangThaiDung);
        }
    }

    void evSpiner(Ban ban) {
        loaiBanDAO = new LoaiBanDAO(context);
        List<LoaiBan> list2;
        list2 = loaiBanDAO.getTimKiem(PreferencesHelper.getId(context), "", 1);
        banSpinnerAdapter = new BanSpinnerAdapter(context, (ArrayList<LoaiBan>) list2);
        spnrBan.setAdapter(banSpinnerAdapter);
        for (int i = 0; i < list2.size(); i++) {
            LoaiBan lb = list2.get(i);
            if (lb.getMaLB() == ban.getMaLB()) {
                spnrBan.setSelection(i);
                spnrBan.setSelected(true);
            }
        }
    }
    void anChucNang(BanViewHolder holder){
        nhanVienDAO = new NhanVienDAO(context);
        int phanQuyen = nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(context));
        if(phanQuyen == 0){
            holder.imgSua.setVisibility(View.GONE);
        }
    }
}
