package sp23cp18103.nhom2.finedining.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.BanDAO;
import sp23cp18103.nhom2.finedining.database.LoaiBanDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.LoaiBan;

/*
 * Adapter để hiển thị danh sách bàn trong BanFragment
 * */
public class BanAdapter extends RecyclerView.Adapter<BanAdapter.BanViewHolder> {
    Context context;
    List<Ban> list;
    BanDAO banDAO;
    EditText edViTriBan;
    CheckBox chkTrangThaiBan;
    AppCompatButton btnShaveBan,btnCancelBan;
    Spinner spnrBan;
    List<LoaiBan> listloaiban;
    LoaiBanDAO loaiBanDAO;
    LoaiBan loaiBan;
    BanSpinnerAdapter banSpinnerAdapter;
    int positionLB;
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
        Ban ban = list.get(position);

        LoaiBanDAO loaiBanDAO = new LoaiBanDAO(context);
        loaiBan = loaiBanDAO.getID(ban.getMaLB());
        banDAO=new BanDAO(context);
        holder.tvViTri.setText(ban.getViTri());
        holder.tvLoaiBan.setText(""+loaiBan.getTenLoai());
        holder.tvTrangThaiBan.setText(""+ban.getTrangThai());

        if(ban.getTrangThai()==1){
            holder.tvTrangThaiBan.setText(" Dùng");
            holder.tvTrangThaiBan.setTextColor(Color.BLUE);
        }else{
            holder.tvTrangThaiBan.setText("Không dùng");
            holder.tvTrangThaiBan.setTextColor(Color.RED);
        }

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goidialog(ban);
            }
        });
        
    }

    void goidialog(Ban ban){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ban, null);
        builder.setView(view);

        TextView tvTieuDeBan = view.findViewById(R.id.tvTieuDeBan);
        tvTieuDeBan.setText("Sửa loại loại bàn");

        banDAO = new BanDAO(context);

        edViTriBan = view.findViewById(R.id.edViTriBan);
        spnrBan=view.findViewById(R.id.spnrBan);
        chkTrangThaiBan = view.findViewById(R.id.chkTrangThaiBan);
        btnShaveBan = view.findViewById(R.id.btnShaveBan);
        btnCancelBan = view.findViewById(R.id.btnCancelBan);
        edViTriBan.setText(ban.getViTri());

        Dialog dialog = builder.create();
        if (ban.getTrangThai() == 1) {
            chkTrangThaiBan.setChecked(true);
        } else {
            chkTrangThaiBan.setChecked(false);
        }
        evSpiner(ban);


        btnCancelBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });
        btnShaveBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiBan loaiBan = (LoaiBan) spnrBan.getSelectedItem();
                ban.setMaLB(loaiBan.getMaLB());
                String viTri = edViTriBan.getText().toString().trim();
                ban.setViTri(viTri);



                if (chkTrangThaiBan.isChecked()) {
                    ban.setTrangThai(1);
                } else {
                    ban.setTrangThai(0);
                }
                if (viTri.isEmpty() ) {
                    edViTriBan.setError("Không được để trống");
                    return;
                } else {
                    ban.setViTri((edViTriBan.getText().toString()));
                    if (banDAO.updateban(ban) > 0) {
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Update không thành công", Toast.LENGTH_SHORT).show();
                    }
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
        TextView tvViTri, tvLoaiBan, tvTrangThaiBan;
        ImageButton imgSua;

        public BanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvViTri = itemView.findViewById(R.id.tvViTri);
            tvLoaiBan = itemView.findViewById(R.id.tvLoaiBan);
            tvTrangThaiBan = itemView.findViewById(R.id.tvTrangThai);
            imgSua = itemView.findViewById(R.id.imgSua);
        }
    }
    void evSpiner(Ban ban) {
        loaiBanDAO = new LoaiBanDAO(context);
        List<LoaiBan> list2;
        list2 =  loaiBanDAO.getAllLoaiBan();
        banSpinnerAdapter  = new BanSpinnerAdapter(context, (ArrayList<LoaiBan>) list2);
        spnrBan.setAdapter(banSpinnerAdapter);
        for (int i = 0; i < list2.size(); i++) {
            LoaiBan lb = list2.get(i);
            if (lb.getMaLB() == ban.getMaLB()) {
                spnrBan.setSelection(i);
                spnrBan.setSelected(true);
            }
        }
    }
}
