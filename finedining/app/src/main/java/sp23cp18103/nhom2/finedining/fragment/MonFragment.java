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
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import sp23cp18103.nhom2.finedining.Interface.ILoaiMonFilter;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.LoaiMonFilterAdapter;
import sp23cp18103.nhom2.finedining.adapter.LoaiMonSpinnerAdapter;
import sp23cp18103.nhom2.finedining.adapter.MonAdapter;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.database.MonDAO;
import sp23cp18103.nhom2.finedining.model.LoaiMon;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.utils.GalleryHelper;
import sp23cp18103.nhom2.finedining.utils.ImageHelper;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
* Hiển thị danh sách món và thêm, sửa
* */
public class MonFragment extends Fragment {
    Context context;
    RecyclerView rcvMon, rcvFilter;
    FloatingActionButton fabMon;
    List<Mon> list;
    MonDAO dao;
    MonAdapter adapter;
    TextInputEditText edTimKiemMon, edDialogTenMon, edDialogGia;
    TextInputLayout inputTimKiemMon,inputDialogTenMon, inputDialogGia;
    ArrayList<LoaiMon> listLoaiMon;
    CheckBox chkFragmentMon,chkTrangThaiMon;
    int maLoaiMon, positionLM;
    LoaiMonSpinnerAdapter loaiMonSpinnerAdapter;
    LoaiMonDAO loaiMonDAO;
    GalleryHelper galleryHelper;
    LoaiMonFilterAdapter loaiMonFilterAdapter;
    List<String> listFilter;
    LoaiMonFilterAdapter.FilterViewHolder holderCu;
    Spinner spnrialogLoaiMon;
    String bienLoc  = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        galleryHelper = new GalleryHelper(getContext());
        rcvMon = view.findViewById(R.id.rcvMon);
        fabMon = view.findViewById(R.id.fbtnAddMon);
        edTimKiemMon = view.findViewById(R.id.edTimKiemMon);
        inputTimKiemMon = view.findViewById(R.id.inputTimKiemMon);
        chkFragmentMon = view.findViewById(R.id.chkFragmentMon);
        dao = new MonDAO(getContext());
        rcvFilter = view.findViewById(R.id.rcvFilter);
        loaiMonDAO = new LoaiMonDAO(getContext());
        Mon m = new Mon();
        timKiemMon();
        capNhat();
        hienThiFilter();




        chkFragmentMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.clear();
                list.addAll(dao.trangThaiLoaiMon(PreferencesHelper.getId(getContext()),
                        (chkFragmentMon.isChecked())?0:1,
                        edTimKiemMon.getText().toString().trim()));
                adapter.notifyDataSetChanged();
            }
        });
        //Sự kiện thêm món
        fabMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maNV = PreferencesHelper.getId(getContext());
                listLoaiMon = (ArrayList<LoaiMon>) loaiMonDAO.trangThaiLoaiMon(maNV, 1, "");
                int count = listLoaiMon.size();
                if (count<=0) {
                    Toast.makeText(context, "Chưa tồn tại loại món đang được sử dụng", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_mon, null);
                    builder.setView(view);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView tv_tieude_mon = view.findViewById(R.id.tvTieuDeMon);
                    edDialogTenMon = view.findViewById(R.id.edDialogTenMon);
                    edDialogGia = view.findViewById(R.id.edDialogGia);
                    spnrialogLoaiMon = view.findViewById(R.id.spnrDialogLoaiMon);
                    chkTrangThaiMon = view.findViewById(R.id.chkTrangThaiMon);
                    Button btnDialogLuuMon = view.findViewById(R.id.btnDialogLuuMon);
                    Button btnDialogHuyMon = view.findViewById(R.id.btnDialogHuyMon);
                    ImageButton imgDialogMon = view.findViewById(R.id.imgDialogMon);
                    inputDialogGia = view.findViewById(R.id.inputDialogGia);
                    inputDialogTenMon = view.findViewById(R.id.inputDialogTenMon);
                    tv_tieude_mon.setText("Thêm món");
                    chkTrangThaiMon.setVisibility(View.GONE);
                    Dialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    listLoaiMon = (ArrayList<LoaiMon>) loaiMonDAO.trangThaiLoaiMon(maNV, 1, "");
                    loaiMonSpinnerAdapter = new LoaiMonSpinnerAdapter(builder.getContext(), listLoaiMon);
                    spnrialogLoaiMon.setAdapter(loaiMonSpinnerAdapter);
                    for (int i = 0; i < listLoaiMon.size(); i++) {
                        if (m.getMaMon() == (listLoaiMon.get(i).getMaLM())) {
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
                    ImageHelper.loadAvatar(getContext(), imgDialogMon, m.getHinh());
                    imgDialogMon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            galleryHelper.getImageFromGallery(imgDialogMon);
                        }
                    });
                    btnDialogLuuMon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tenMon = edDialogTenMon.getText().toString();
                            String giaMon = edDialogGia.getText().toString();
                            m.setTenMon(tenMon);
                            m.setMaLM(maLoaiMon);
                            if (!giaMon.isEmpty()) {
                                m.setGia(Integer.parseInt(giaMon));
                            }
                            m.setHinh(galleryHelper.getCurrentImageUrl());
                            m.setHinh(String.valueOf(R.drawable.default_avatar));
                            m.setTrangThai(1);
                            if (ValidateMon() > 0) {
                                if (dao.insertMon(m) > 0) {
                                    Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
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
            inputDialogTenMon.setError("Không được để trống");
            check = -1;
        }else if(giaMon.isEmpty()){
            inputDialogGia.setError("Không được để trống");
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
        int trangThai = (chkFragmentMon.isChecked())?0:1;
        String timKiem = edTimKiemMon.getText().toString().trim();
            list = dao.getLocLoaiMon(maNV, trangThai, timKiem, bienLoc);
            adapter = new MonAdapter(getActivity(), list);
            rcvMon.setAdapter(adapter);
    }
    //hàm cập nhật recycleview
    void capNhat(){
        int maNV = PreferencesHelper.getId(getContext());
        int trangThai = (chkFragmentMon.isChecked())?0:1;
        list = dao.getLocLoaiMon(maNV, trangThai, "", bienLoc);
        adapter = new MonAdapter(getContext(), list);
        rcvMon.setAdapter(adapter);
    }
    //hàm cập nhật listFilter
    private void hienThiFilter() {
        int maNV = PreferencesHelper.getId(getContext());
        listFilter = loaiMonDAO.getFilterMon(maNV);
        listFilter.add(0,"Tất cả");
        loaiMonFilterAdapter = new LoaiMonFilterAdapter(getContext(), listFilter, new ILoaiMonFilter(){
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void locMon(String tenLoaiMon, LoaiMonFilterAdapter.FilterViewHolder holder) {
                //Doi mau mac dinh
                bienLoc = tenLoaiMon;
                if(holderCu != null){
                    holderCu.tvFilterLoaiMon.setBackground(AppCompatResources.getDrawable(context,R.drawable.filter_item_normal_background));
                }
                //Doi mau cam
                holderCu = holder;
                holder.tvFilterLoaiMon.setBackground(AppCompatResources.getDrawable(context,R.drawable.filter_item_clicked_background));
                if(tenLoaiMon.equalsIgnoreCase("Tất cả")){
                    bienLoc = "";
                    capNhat();
                }else{
                    int maNV = PreferencesHelper.getId(getContext());
                    int trangThai = (chkFragmentMon.isChecked())?0:1;
                    list.clear();
                    list.addAll(dao.getLocLoaiMon(maNV, trangThai, edTimKiemMon.getText().toString().trim(), tenLoaiMon));
                    adapter.notifyDataSetChanged();
                }
            }
        });
        rcvFilter.setAdapter(loaiMonFilterAdapter);
    }
    //hàm cập nhật fragment

    @Override
    public void onResume() {
        capNhat();
        hienThiFilter();
        super.onResume();
    }
}