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

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
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
    List<Mon> list, list2;
    MonDAO dao;
    MonAdapter adapter;
    TextInputEditText edTimKiemMon;
    TextInputLayout inputTimKiemMon;
    ArrayList<LoaiMon> listLoaiMon;
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
        dao = new MonDAO(getContext());
        hideFloatingButton();
        loaiMonDAO = new LoaiMonDAO(getContext());
        capNhat();
        //Them mon
        fabMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater=((Activity)getContext()).getLayoutInflater();
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
                tv_tieude_mon.setText("Thêm món");
                Dialog dialog = builder.create();
                listLoaiMon = (ArrayList<LoaiMon>) loaiMonDAO.getAllLoaiMon();
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
                        String tenMon = edDialogTenMon.getText().toString().trim();
                        String giaMon = edDialogGia.getText().toString().trim();
                        m.setTenMon(tenMon);
                        m.setMaLM(maLoaiMon);
                        m.setGia(Integer.parseInt(giaMon));
                        m.setHinh(String.valueOf(R.drawable.default_avatar));
                        if(chkTrangThaiMon.isChecked()){
                            m.setTrangThai(1);
                        }else{
                            m.setTrangThai(0);
                        }
                        if(tenMon.isEmpty()){
                            edDialogTenMon.setError("Không được để trống");
                            return;
                        }else if(giaMon.isEmpty()){
                            edDialogGia.setError("Không được để trống");
                            return;
                        }else{
                            try {
                                Integer.parseInt(edDialogGia.getText().toString());
                            }catch (Exception e){
                                edDialogGia.setError("Giá không hợp lệ");
                                return;
                            }
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
        edTimKiemMon.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int maNV = PreferencesHelper.getId(getContext());
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    String timKiem = edTimKiemMon.getText().toString().trim();
                    if(timKiem.isEmpty()){
                        adapter = new MonAdapter(getActivity(), list);
                        rcvMon.setAdapter(adapter);
                        return false;
                    }else{
                        list2 = dao.timKiem(maNV, timKiem);
                        adapter = new MonAdapter(getActivity(), list2);
                        rcvMon.setAdapter(adapter);
                    }
                    return true;
                }
                return false;
            }
        });
        inputTimKiemMon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maNV = PreferencesHelper.getId(getContext());
                String timKiem = edTimKiemMon.getText().toString().trim();
                if(timKiem.isEmpty()){
                    adapter = new MonAdapter(getActivity(), list);
                    rcvMon.setAdapter(adapter);
                    return ;
                }else{
                    list2 = dao.timKiem(maNV, timKiem);
                    adapter = new MonAdapter(getActivity(), list2);
                    rcvMon.setAdapter(adapter);
                }
            }
        });
    }

    void hideFloatingButton(){
       NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
        int maNV = PreferencesHelper.getId(getContext());
        int chuVu = nhanVienDAO.getPhanQuyen(maNV);
        if (chuVu == 1){
            fabMon.setVisibility(View.VISIBLE);
        }else {
            fabMon.setVisibility(View.GONE);
        }
    }

    void capNhat(){
        list = dao.timKiem(PreferencesHelper.getId(getContext()),"");
        adapter = new MonAdapter(getContext(), list);
        rcvMon.setAdapter(adapter);
    }
}