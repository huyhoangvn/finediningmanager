package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
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
    CheckBox chkDialogTrangThaiLoaiMon, chkFragmentLoaiMon;
    Button btnDialogLuuLoaiMon, btnDialogHuyLoaiMon;
    FloatingActionButton fabLoaiMon;
    LoaiMonDAO dao;
    Context context;
    List<LoaiMon> listLM;
    LoaiMonAdapter adapter;
    TextInputLayout inputTimKiemLoaiMon, inputDialogTenLoaiMon;

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
        chkFragmentLoaiMon = view.findViewById(R.id.chkFragmentLoaiMon);
        dao = new LoaiMonDAO(getContext());
        getPhanQuyen();
        timKiemLM();
        capNhat();
        //sự kiện lọc trang thái
        chkFragmentLoaiMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listLM.clear();
                listLM.addAll(dao.trangThaiLoaiMon(PreferencesHelper.getId(getContext()),
                        (chkFragmentLoaiMon.isChecked())?0:1,
                        edTimKiemLoaiMon.getText().toString().trim()));
                adapter.notifyDataSetChanged();
            }
        });
        //sự kiện thêm loại món
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
                inputDialogTenLoaiMon = view.findViewById(R.id.inputDialogTenLoaiMon);
                tvTieuDeLoaiMon.setText("Thêm loại món ");
                chkDialogTrangThaiLoaiMon.setVisibility(View.GONE);
                Dialog dialog= builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btnDialogLuuLoaiMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoaiMon lm = new LoaiMon();
                        int maNV = PreferencesHelper.getId(getContext());
                        lm.setMaNV(maNV);
                        lm.setTenLoai(edTenLoaiMon.getText().toString().trim());
                        lm.setTrangThai(1);
                        if(ValidateLM()>0){
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



    }

    private void getPhanQuyen() {
        NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
        int maNV = PreferencesHelper.getId(getContext());
        if (nhanVienDAO.getPhanQuyen(maNV)==0){
            fabLoaiMon.setVisibility(View.GONE);
        }
    }

    //hàm tìm kiếm
    public void timKiemLM(){
        edTimKiemLoaiMon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hiemThiDanhSachLM();
            }
        });
        edTimKiemLoaiMon.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH){
                    hiemThiDanhSachLM();
                    return true;
                }
                return false;
            }
        });
        inputTimKiemLoaiMon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiemThiDanhSachLM();
            }
        });
    }
    //hàm kiểm tra dữ liệu
    public int ValidateLM(){
        int check = 1;
        String tenLM = edTenLoaiMon.getText().toString();
        if(tenLM.isEmpty()){
            inputDialogTenLoaiMon.setError("Không được để trống");
            check = -1;
        }
        return check;
    }
    //hàm cập nhật recycleview cho hàm tìm kiếm
    public void hiemThiDanhSachLM(){
        int maNV = PreferencesHelper.getId(getContext());
        int trangThai = (chkFragmentLoaiMon.isChecked())?0:1;
        String timKiem = edTimKiemLoaiMon.getText().toString().trim();
        if(timKiem.isEmpty()){
            listLM = dao.trangThaiLoaiMon(maNV, trangThai,"");
            adapter = new LoaiMonAdapter(getContext(), listLM);
            rcvLoaiMon.setAdapter(adapter);
            return ;
        }else{
            listLM = dao.trangThaiLoaiMon(maNV, trangThai,timKiem);
            adapter = new LoaiMonAdapter(getContext(), listLM);
            rcvLoaiMon.setAdapter(adapter);
        }
    }

    //hàm cập nhật recycleview
    void capNhat(){
        int maNV = PreferencesHelper.getId(getContext());
        int trangThai = (chkFragmentLoaiMon.isChecked())?0:1;
        listLM = dao.trangThaiLoaiMon(maNV, trangThai ,"");
        adapter = new LoaiMonAdapter(getContext(), listLM);
        rcvLoaiMon.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        hiemThiDanhSachLM();
        super.onResume();
    }
}