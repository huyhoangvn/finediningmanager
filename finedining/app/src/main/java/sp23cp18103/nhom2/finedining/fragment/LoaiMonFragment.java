package sp23cp18103.nhom2.finedining.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.LoaiMonAdapter;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.model.LoaiMon;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
* Hiển thị danh sách loại món và thêm sửa
* */
public class LoaiMonFragment extends Fragment {
    RecyclerView rcvLoaiMon;
    TextInputEditText edTenLoaiMon,edTimKiemLoaiMon;
    TextView tvTieuDeLoaiMon;
    CheckBox chkDialogTrangThaiLoaiMon;
    Button btnDialogLuuLoaiMon, btnDialogHuyLoaiMon;
    FloatingActionButton fabLoaiMon;
    LoaiMonDAO dao;
    Context context;
    List<LoaiMon> listMon, listTimKiem;
    LoaiMonAdapter adapter;
    TextInputLayout inputTimKiemLoaiMon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loai_mon, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvLoaiMon = view.findViewById(R.id.rcvLoaiMon);
        fabLoaiMon = view.findViewById(R.id.fbtnAddLoaiMon);
        edTimKiemLoaiMon = view.findViewById(R.id.edTimKiemLoaiMon);
        inputTimKiemLoaiMon = view.findViewById(R.id.inputTimKiemLoaiMon);
        dao = new LoaiMonDAO(getContext());
        capNhat();
        //Thêm loại món
        fabLoaiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater=((Activity)getContext()).getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_loai_mon,null);
                builder.setView(view);
                edTenLoaiMon = view.findViewById(R.id.edTenLoaiMon);
                chkDialogTrangThaiLoaiMon = view.findViewById(R.id.chkDialogTrangThaiLoaiMon);
                btnDialogHuyLoaiMon = view.findViewById(R.id.btnDialogHuyLoaiMon);
                btnDialogLuuLoaiMon = view.findViewById(R.id.btnDialogLuuLoaiMon);
                tvTieuDeLoaiMon = view.findViewById(R.id.tvTieuDeLoaiMon);
                tvTieuDeLoaiMon.setText("Thêm loại món ");
                Dialog dialog= builder.create();
                btnDialogLuuLoaiMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoaiMon lm = new LoaiMon();
                        int maNV = PreferencesHelper.getId(getContext());
                        lm.setMaNV(maNV);
                        lm.setTenLoai(edTenLoaiMon.getText().toString().trim());
                        if(chkDialogTrangThaiLoaiMon.isChecked()){
                            lm.setTrangThai(1);
                        }else{
                            lm.setTrangThai(0);
                        }
                        if(edTenLoaiMon.getText().toString().trim().isEmpty()){
                            edTenLoaiMon.setError("Không được để trống");
                            return;
                        }else{
                            if(dao.insertLoaiMon(lm)>0){
                                Toast.makeText(getActivity(), "Thêm thành công ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else{
                                Toast.makeText(getActivity(), "Thêm thất bại ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        capNhat();
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
        //Sự kiện tìm kiếm loại món
        edTimKiemLoaiMon.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int maNV = PreferencesHelper.getId(getContext());

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    String timKiem = edTimKiemLoaiMon.getText().toString().trim();
                    if(timKiem.isEmpty()){
                        capNhat();
                        return false;
                    }else{
                        listTimKiem = dao.timKiem(maNV, timKiem);
                        adapter = new LoaiMonAdapter(getActivity(), listTimKiem);
                        rcvLoaiMon.setAdapter(adapter);
                    }
                    return true;
                }
                return false;
            }
        });
        inputTimKiemLoaiMon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maNV = PreferencesHelper.getId(getContext());
                String timKiem = edTimKiemLoaiMon.getText().toString().trim();
                if(timKiem.isEmpty()){
                    capNhat();
                    return ;
                }else{
                    listTimKiem = dao.timKiem(maNV, timKiem);
                    adapter = new LoaiMonAdapter(getContext(), listTimKiem);
                    rcvLoaiMon.setAdapter(adapter);
                }
            }
        });
    }

    //hàm cập nhật recycleview
    void capNhat(){
        listMon = dao.getAllLoaiMon();
        adapter = new LoaiMonAdapter(getContext(), listMon);
        rcvLoaiMon.setAdapter(adapter);
    }


}