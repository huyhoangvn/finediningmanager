package sp23cp18103.nhom2.finedining.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.LoaiMon;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.utils.ColorHelper;
import sp23cp18103.nhom2.finedining.utils.GalleryHelper;
import sp23cp18103.nhom2.finedining.utils.ImageHelper;
import sp23cp18103.nhom2.finedining.utils.NumberHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;
import sp23cp18103.nhom2.finedining.utils.ValueHelper;

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
    TextInputEditText edDialogTenMon, edDialogGia;
    GalleryHelper galleryHelper;
    NhanVienDAO nhanVienDAO;
    TextInputLayout inputDialogTenMon, inputDialogGia;

    public MonAdapter( Context context, List<Mon> list) {
        this.context = context;
        this.list = list;
        galleryHelper = new GalleryHelper(context);
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_mon, parent, false);
        return new MonViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, @SuppressLint("RecyclerView") int position) {
        hideFloatingButton(holder);
        Mon m =list.get(position);
        nhanVienDAO = new NhanVienDAO(context);
        dao = new MonDAO(context);
        holder.tvCardviewTenMon.setText(m.getTenMon());
        loaiMonDAO = new LoaiMonDAO(context);
        int maNV = PreferencesHelper.getId(context);
        if(nhanVienDAO.getPhanQuyen(maNV)==0){
            holder.imgcardviewSuaMon.setVisibility(View.GONE);
        }
        LoaiMon lm = loaiMonDAO.getId(String.valueOf(m.getMaLM()));
        holder.tvCardviewTenLoaiMon.setText(lm.getTenLoai());
        holder.tvCardviewGiaMon.setText(NumberHelper.getNumberWithDecimal(m.getGia()) + "");
        if(m.getTrangThai()==1){
            holder.tvCardviewTrangThaiMon.setText("Còn dùng");
            holder.tvCardviewTrangThaiMon.setTextColor(ColorHelper.getPositiveColor(context));
        }else{
            holder.tvCardviewTrangThaiMon.setText("Không dùng");
            holder.tvCardviewTrangThaiMon.setTextColor(ColorHelper.getNegativeColor(context));
        }
        ImageHelper.loadAvatar(context, holder.imgCardviewMon, m.getHinh());
        holder.imgcardviewSuaMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int phanQuyen = nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(context));
                if(phanQuyen == 1){
                    holder.imgcardviewSuaMon.setVisibility(View.VISIBLE);
                }else{
                    holder.imgcardviewSuaMon.setVisibility(View.INVISIBLE);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater=((Activity)context).getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_mon,null);
                builder.setView(view);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView tv_tieude_mon = view.findViewById(R.id.tvTieuDeMon);
                edDialogTenMon = view.findViewById(R.id.edDialogTenMon);
                edDialogGia = view.findViewById(R.id.edDialogGia);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                Spinner spnrialogLoaiMon = view.findViewById(R.id.spnrDialogLoaiMon);
                CheckBox chkTrangThaiMon = view.findViewById(R.id.chkTrangThaiMon);
                Button btnDialogLuuMon = view.findViewById(R.id.btnDialogLuuMon);
                Button btnDialogHuyMon = view.findViewById(R.id.btnDialogHuyMon);
                ImageButton imgDialogMon = view.findViewById(R.id.imgDialogMon);
                inputDialogGia = view.findViewById(R.id.inputDialogGia);
                inputDialogTenMon = view.findViewById(R.id.inputDialogTenMon);
                tv_tieude_mon.setText("Sửa món");
                Dialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                edDialogTenMon.setText(m.getTenMon());
                edDialogGia.setText(String.valueOf(m.getGia()));
                imgDialogMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            galleryHelper.getImageFromGallery(imgDialogMon);
                    }
                });
                ImageHelper.loadAvatar(context,imgDialogMon,m.getHinh());

                if(m.getTrangThai()==1){
                    chkTrangThaiMon.setChecked(true);
                }else{
                    chkTrangThaiMon.setChecked(false);
                }
                int maNV = PreferencesHelper.getId(context);
                listLoaiMon = (ArrayList<LoaiMon>) loaiMonDAO.trangThaiLoaiMon(maNV, 1, "");
                loaiMonSpinnerAdapter = new LoaiMonSpinnerAdapter(builder.getContext(), listLoaiMon);
                spnrialogLoaiMon.setAdapter(loaiMonSpinnerAdapter);
                for(int i = 0; i<listLoaiMon.size(); i++){
                    if(m.getMaMon() == (listLoaiMon.get(i).getMaLM())){
                        positionLM = i;
                    }
                }
                spnrialogLoaiMon.setSelection(loaiMonSpinnerAdapter.getPosition(new LoaiMon(m.getMaLM(),"", -1, -1)));

                spnrialogLoaiMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maLoaiMon = listLoaiMon.get(position).getMaLM();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                edDialogTenMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputDialogTenMon.setError(null);
                    }
                });
                edDialogGia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputDialogGia.setError(null);
                    }
                });

                btnDialogLuuMon.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        inputDialogGia.setError(null);
                        inputDialogTenMon.setError(null);
                       if(ValidateMon()>0){
                           Mon monThem = new Mon();
                           String tenMon = edDialogTenMon.getText().toString().trim();
                           String giaMon = edDialogGia.getText().toString().trim();
                           monThem.setMaMon(m.getMaMon());
                           monThem.setTenMon(tenMon);
                           if(!giaMon.isEmpty()){
                               monThem.setGia(Integer.parseInt(giaMon));
                           }
                           if(galleryHelper.getCurrentImageUrl() == null){
                               monThem.setHinh(m.getHinh());
                           }else{
                               monThem.setHinh(galleryHelper.getCurrentImageUrl());
                           }
                           monThem.setMaLM(maLoaiMon);
                           if(chkTrangThaiMon.isChecked()){
                               if(dao.getTuDongChuyenTrangThai(m.getMaMon(), maNV)>0){
                                   monThem.setTrangThai(1);
                               }else{
                                   monThem.setTrangThai(1);
                               }
                           }else{
                               int maNV = PreferencesHelper.getId(context);
                               if(dao.getTrangThaiDatMon(m.getMaMon(), maNV)>0){
                                   monThem.setTrangThai(0);
                               }else{
                                   monThem.setTrangThai(0);
                               }
                           }
                           for(int i = 0; i<listLoaiMon.size(); i++){
                               if(m.getMaMon() == (listLoaiMon.get(i).getMaLM())){
                                   positionLM = i;
                               }
                           }
                           spnrialogLoaiMon.setSelection(positionLM);
                           if(dao.updateMon(monThem)>0){
                                Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                               list.set(position, monThem);
                               notifyDataSetChanged();
                               galleryHelper.clearCurrentImageUrl();
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

    public int ValidateMon(){
        String tenMon = edDialogTenMon.getText().toString().trim();
        String giaMon = edDialogGia.getText().toString().trim();
        if(tenMon.isEmpty()){
            inputDialogTenMon.setError("Không được để trống");
            return 0;
        }
        if(giaMon.isEmpty()){
            inputDialogGia.setError("Không được để trống");
            return 0;
        }
        if(tenMon.length() > ValueHelper.MAX_INPUT_NAME){
            inputDialogTenMon.setError("Nhập tối đa " + ValueHelper.MAX_INPUT_NAME + " kí tự");
            return 0;
        }
        if(Long.parseLong(giaMon) > ValueHelper.MAX_INPUT_NUMBER){
            inputDialogGia.setError("Nhập giá tối đa " + ValueHelper.MAX_INPUT_NUMBER);
            return 0;
        }
        return 1;
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
