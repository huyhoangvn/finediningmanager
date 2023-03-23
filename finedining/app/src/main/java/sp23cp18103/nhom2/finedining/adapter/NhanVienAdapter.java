package sp23cp18103.nhom2.finedining.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.utils.DateHelper;
import sp23cp18103.nhom2.finedining.utils.ImageHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
* Adapter để hiển thị danh sách nhân viên trong NhanVienFragment
* */
public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.MyViewHolder>{
    private Context context;
    private NhanVienAdapter adpNhanVien;
    private ArrayList<NhanVien> listNhanVien;
    private IEditListener iEditListener;
    //Database
    private NhanVienDAO nhanVienDAO;

    public NhanVienAdapter(Context context, ArrayList<NhanVien> listNhanVien, IEditListener iEditListener) {
        this.context = context;
        this.listNhanVien = listNhanVien;
        this.iEditListener = iEditListener;

        khoiTaoDAO();
    }

    /*
    * Khởi tạo lớp DAO
    * */
    private void khoiTaoDAO() {
        nhanVienDAO = new NhanVienDAO(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cardview_nhan_vien, parent, false);
        return new NhanVienAdapter.MyViewHolder(itemView);
    }

    /*
    * Hiển thị thông tin lên cardview của nhân viên
    * Đổi trạng thái xanh cho "Làm" và đỏ cho "nghỉ"
    * Nhân viên quản lý sẽ được sửa tất cả nhân viên
    * Nhân viên thường chỉ được sửa bản thân
    * */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NhanVien nhanVien = listNhanVien.get(position);
        holder.tvTenNV.setText(nhanVien.getTenNV());
        holder.tvSdt.setText(nhanVien.getSdt());
        holder.tvNgaySinh.setText(DateHelper.getDateVietnam(nhanVien.getNgaySinh()));
        holder.tvGioiTinh.setText(nhanVien.getTenGioiTinh());
        holder.tvTrangThai.setText(nhanVien.getTenTrangThai());
        if(nhanVien.getTrangThai() == 1){
            holder.tvTrangThai.setTextColor(Color.GREEN);
        } else {
            holder.tvTrangThai.setTextColor(Color.RED);
        }
        ImageHelper.loadAvatar(context, holder.imgHinh, nhanVien.getHinh());
        /*
        * Hiển thị fragment để sửa thông tin nhân viên
        * */
        if(nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(context)) != 1){
            if(PreferencesHelper.getId(context) != nhanVien.getMaNV()){
                holder.imgbtnSua.setVisibility(View.GONE);
            }
        } else {
            if(nhanVienDAO.getPhanQuyen(nhanVien.getMaNV()) == 1
                && PreferencesHelper.getId(context) != nhanVien.getMaNV()){
                holder.imgbtnSua.setVisibility(View.GONE);
            }
        }
        holder.imgbtnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iEditListener.showEditFragment(nhanVien.getMaNV());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNhanVien.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinh;
        TextView tvTenNV, tvNgaySinh, tvSdt, tvGioiTinh, tvTrangThai;
        ImageButton imgbtnSua;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinh = itemView.findViewById(R.id.img_cvNhanVien_hinh);
            tvTenNV = itemView.findViewById(R.id.tv_cvNhanVien_tenNV);
            tvSdt = itemView.findViewById(R.id.tv_cvNhanVien_sdt);
            tvNgaySinh = itemView.findViewById(R.id.tv_cvNhanVien_ngaySinh);
            tvGioiTinh = itemView.findViewById(R.id.tv_cvNhanVien_gioiTinh);
            tvTrangThai = itemView.findViewById(R.id.tv_cvNhanVien_trangThai);
            imgbtnSua = itemView.findViewById(R.id.imgbtn_cvNhanVien_sua);
        }
    }

    /*
     * Sửa thông tin công khai của nhân viên
     * */
    private void showFragmentSua() {

    }

}
