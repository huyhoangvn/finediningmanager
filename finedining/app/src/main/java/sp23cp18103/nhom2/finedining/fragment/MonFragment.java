package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.LoaiMonAdapter;
import sp23cp18103.nhom2.finedining.adapter.LoaiMonSpinnerAdapter;
import sp23cp18103.nhom2.finedining.adapter.MonAdapter;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.model.LoaiMon;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
* Hiển thị danh sách món và thêm, sửa
* */
public class MonFragment extends Fragment {
    Context context;
    RecyclerView rcvMon;
    FloatingActionButton fabMon;
    List<Mon> list;
    MonDAO dao;
    MonAdapter adapter;
    TextInputEditText edTimKiemMon, edDialogTenMon, edDialogGia;
    TextInputLayout inputTimKiemMon;
    ArrayList<LoaiMon> listLoaiMon;
    CheckBox chkFragmentMon;
    int maLoaiMon, positionLM;
    LoaiMonSpinnerAdapter loaiMonSpinnerAdapter;
    LoaiMonDAO loaiMonDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvMon = view.findViewById(R.id.rcvMon);
        fabMon = view.findViewById(R.id.fbtnAddMon);
        edTimKiemMon = view.findViewById(R.id.edTimKiemMon);
        inputTimKiemMon = view.findViewById(R.id.inputTimKiemMon);
        chkFragmentMon = view.findViewById(R.id.chkFragmentMon);
        dao = new MonDAO(getContext());
        loaiMonDAO = new LoaiMonDAO(getContext());
        timKiemMon();
        capNhat();
        chkFragmentMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.clear();
                list.addAll(dao.trangThaiLoaiMon(PreferencesHelper.getId(getContext()),
                        (chkFragmentMon.isChecked())?1:0,
                        edTimKiemMon.getText().toString().trim()));
                adapter.notifyDataSetChanged();
            }
        });
        //Sự kiện thêm món
        fabMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater=((Activity)getContext()).getLayoutInflater();
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
                tv_tieude_mon.setText("Thêm món");
                Dialog dialog = builder.create();
                int maNV = PreferencesHelper.getId(getContext());
                int trangThai = (chkTrangThaiMon.isChecked())?1:0;
                listLoaiMon = (ArrayList<LoaiMon>) loaiMonDAO.trangThaiLoaiMon(maNV, trangThai, "");
                loaiMonSpinnerAdapter = new LoaiMonSpinnerAdapter(builder.getContext(), listLoaiMon);
                spnrialogLoaiMon.setAdapter(loaiMonSpinnerAdapter);
                Mon m = new Mon();
                for(int i = 0; i<listLoaiMon.size(); i++){
                    if(m.getMaMon() == (listLoaiMon.get(i).getMaLM())){
                        positionLM = i;
                    }
                }
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
                        String tenMon = edDialogTenMon.getText().toString();
                        String giaMon = edDialogGia.getText().toString();
                        m.setTenMon(tenMon);
                        m.setMaLM(maLoaiMon);
                        if(!giaMon.isEmpty()){
                            m.setGia(Integer.parseInt(giaMon));
                        }
                        m.setHinh(String.valueOf(R.drawable.default_avatar));
                        if(chkTrangThaiMon.isChecked()){
                            m.setTrangThai(1);
                        }else{
                            m.setTrangThai(0);
                        }
                        if(ValidateMon()>0){
                            if(dao.insertMon(m)>0){
                                Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                        capNhat();
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
    //hàm tìm kiếm món
    public void timKiemMon(){
        edTimKiemMon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                hienThiDanhSachMon();
            }
        });
        edTimKiemMon.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH){
                    hienThiDanhSachMon();
                    return true;
                }
                return false;
            }
        });
        inputTimKiemMon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiDanhSachMon();
            }
        });
    }
    //hàm kiểm tra dữ liệu
    public int ValidateMon(){
        int check = 1;
        String tenMon = edDialogTenMon.getText().toString();
        String giaMon = edDialogGia.getText().toString();
        if(tenMon.isEmpty()){
            edDialogTenMon.setError("Không được để trống");
            check = -1;
        }else if(giaMon.isEmpty()){
            edDialogGia.setError("Không được để trống");
            check = -1;
        }else{
            try {
                Integer.parseInt(edDialogGia.getText().toString());
            }catch (Exception e){
                edDialogGia.setError("Giá không hợp lệ");
                check = -1;
            }
        }
        return check;
    }
    //hàm cập nhật recycleview tìm kiếm
    public void hienThiDanhSachMon(){
        int maNV = PreferencesHelper.getId(getContext());
        int trangThai = (chkFragmentMon.isChecked())?1:0;
        String timKiem = edTimKiemMon.getText().toString().trim();
        if(timKiem.isEmpty()){
            list = dao.trangThaiLoaiMon(maNV, trangThai, "");
            adapter = new MonAdapter(getActivity(), list);
            rcvMon.setAdapter(adapter);
            return ;
        }else{
            list = dao.trangThaiLoaiMon(maNV, trangThai, timKiem);
            adapter = new MonAdapter(getActivity(), list);
            rcvMon.setAdapter(adapter);
        }
    }
    //hàm cập nhật recycleview
    void capNhat(){
        int maNV = PreferencesHelper.getId(getContext());
        int trangThai = (chkFragmentMon.isChecked())?1:0;
        list = dao.trangThaiLoaiMon(maNV, trangThai, "");
        adapter = new MonAdapter(getContext(), list);
        rcvMon.setAdapter(adapter);
    }
}