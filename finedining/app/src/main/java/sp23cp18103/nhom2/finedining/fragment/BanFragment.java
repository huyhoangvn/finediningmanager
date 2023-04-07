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
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.Interface.ITLoaiBanFilter;
import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.BanAdapter;
import sp23cp18103.nhom2.finedining.adapter.BanSpinnerAdapter;
import sp23cp18103.nhom2.finedining.adapter.LoaiBanAdapter;
import sp23cp18103.nhom2.finedining.adapter.LoaiBanFiterAdapter;
import sp23cp18103.nhom2.finedining.adapter.LoaiMonFilterAdapter;
import sp23cp18103.nhom2.finedining.adapter.NhanVienAdapter;
import sp23cp18103.nhom2.finedining.database.BanDAO;
import sp23cp18103.nhom2.finedining.database.LoaiBanDAO;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Hiển thị danh sách Bàn, thêm, sửa bàn
 * */
public class BanFragment extends Fragment {
    RecyclerView rcvBan,rcvFilter;
    FloatingActionButton fab;
    ArrayList<Ban> list, list2;
    EditText edViTriBan;
    CheckBox chkTrangThaiBan;
    AppCompatButton btnShaveBan, btnCancelBan;
    BanDAO banDAO;
    Spinner spnrBan;
    BanAdapter banAdapter;
    Context context;
    TextView tvTieuDeBan;
    BanSpinnerAdapter banSpinnerAdapter;
    ArrayList<LoaiBan> listloaiban;
    CheckBox chk_fBan_conDung;
    int maLoaiBan;
    LoaiBanDAO loaiBanDAO;
    TextInputLayout inputTimKiemViTri,input_viTriBan;
    EditText edTimKiemBan;
    NhanVienDAO nhanVienDAO;
    List<String> listFilter;
    LoaiBanFiterAdapter loaiBanFiterAdapter;
    LoaiBanFiterAdapter.filterViewHolder holderCu;
    String bienLoc = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_ban, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvBan = view.findViewById(R.id.rcvBan);
        edTimKiemBan = view.findViewById(R.id.edTimKiemBan);
        inputTimKiemViTri = view.findViewById(R.id.inputTimKiemViTri);
        rcvFilter = view.findViewById(R.id.rcv_fillTer);
        chk_fBan_conDung = view.findViewById(R.id.chk_fBan_conDung);

        fab = view.findViewById(R.id.fbtnBan);
        banDAO = new BanDAO(getContext());
        context = getContext();
        anChucNang();
        khoiTaoCheckboxListener();
        khoiTaoRecyclerView();
        khoiTaoTimKiem();

        hienThiFilter();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openDialog(getContext(), 0);

            }
        });
    }

    public void openDialog(final Context context, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ban, null);
        builder.setView(view);

        edViTriBan = view.findViewById(R.id.edViTriBan);
        spnrBan = view.findViewById(R.id.spnrBan);
        tvTieuDeBan = view.findViewById(R.id.tvTieuDeBan);
        tvTieuDeBan.setText("Thêm bàn");
        chkTrangThaiBan = view.findViewById(R.id.chkTrangThaiBan);
        btnShaveBan = view.findViewById(R.id.btnShaveBan);
        btnCancelBan = view.findViewById(R.id.btnCancelBan);
        input_viTriBan = view.findViewById(R.id.input_ViTriBan);

        Dialog dialog = builder.create();
        chkTrangThaiBan.setChecked(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        banDAO = new BanDAO(getContext());
        loaiBanDAO = new LoaiBanDAO(getContext());
        chkTrangThaiBan.setVisibility(View.GONE);
        listloaiban = (ArrayList<LoaiBan>) loaiBanDAO.getTimKiem(PreferencesHelper.getId(getContext()),"",1);
        int count = listloaiban.size();
        if (count<=0){
            Toast.makeText(context, "Chưa tồn tại loại bàn đang được sử dụng", Toast.LENGTH_SHORT).show();
            return;
        }
        banSpinnerAdapter = new BanSpinnerAdapter(getContext(), listloaiban);
        spnrBan.setAdapter(banSpinnerAdapter);
        spnrBan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                maLoaiBan = listloaiban.get(i).getMaLB();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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

                if (validate() > 0) {
                    Ban ban = new Ban();
                    LoaiBan loaiBan = (LoaiBan) spnrBan.getSelectedItem();
                    ban.setMaLB(loaiBan.getMaLB());
                    ban.setViTri(edViTriBan.getText().toString());
                    // ban.setMaNV(maNV);
                    ;
                    if (chkTrangThaiBan.isChecked()) {
                        ban.setTrangThai(1);
                    } else {
                        ban.setTrangThai(0);
                    }
                    if (type == 0) {
                        if (banDAO.insertban(ban) > 0) {
                            Toast.makeText(context, "Thêm loại bàn thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm bàn chưa thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhat();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    void capNhat() {
        int maNV = PreferencesHelper.getId(getContext());
        int trangThai = (chk_fBan_conDung.isChecked())?0:1;
        list.clear();
        list.addAll(banDAO.getLocLoaiBan(maNV,trangThai,edTimKiemBan.getText().toString().trim(),bienLoc));
        banAdapter.notifyDataSetChanged();

    }

    public int validate() {
        int check = 1;
        if (edViTriBan.getText().toString().trim().isEmpty()) {
            input_viTriBan.setError("Không được để trống");
            check = -1;
        } else {
        }
        return check;
    }

    private void khoiTaoRecyclerView() {
        list = (ArrayList<Ban>) banDAO.gettimKiem(PreferencesHelper.getId(context),
                edTimKiemBan.getText().toString().trim(),
                (chk_fBan_conDung.isChecked())?0:1);
        banAdapter = new BanAdapter(getContext(), list);
        rcvBan.setAdapter(banAdapter);
    }

    private void khoiTaoCheckboxListener() {
        chk_fBan_conDung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                capNhat();
            }
        });
    }


    private void khoiTaoTimKiem() {
        edTimKiemBan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                capNhat();
            }
        });
        edTimKiemBan.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    capNhat();
                    return true;
                }
                return false;
            }
        });
        inputTimKiemViTri.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capNhat();
            }
        });
    }

    @Override
    public void onResume() {
        capNhat();
        hienThiFilter();
        super.onResume();
    }


    void anChucNang(){
        nhanVienDAO = new NhanVienDAO(context);
         int phanQuyen = nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(getContext()));
        if(phanQuyen == 0){
            fab.setVisibility(View.GONE);
        }
    }
//    hàm cập nhật listFilter
    private void hienThiFilter() {
        loaiBanDAO = new LoaiBanDAO(context);
        int maNV = PreferencesHelper.getId(getContext());
        listFilter = loaiBanDAO.getFilterBan(maNV);
        listFilter.add(0,"Tất cả");
        loaiBanFiterAdapter = new LoaiBanFiterAdapter(getContext(), listFilter, new ITLoaiBanFilter(){
            @Override
            public void loaiBan(String tenLoaiBan,LoaiBanFiterAdapter.filterViewHolder holder) {
                bienLoc = tenLoaiBan;
                if(holderCu != null){
                    holderCu.tvFilterLoaiBan.setBackground(AppCompatResources.getDrawable(context,R.drawable.filter_item_normal_background));
                }
                //Doi mau cam
                holderCu = holder;
                holder.tvFilterLoaiBan.setBackground(AppCompatResources.getDrawable(context,R.drawable.filter_item_clicked_background));
                if(tenLoaiBan.equalsIgnoreCase("Tất cả")){
                    bienLoc = "";
                    capNhat();
                }else{
                    int maNV = PreferencesHelper.getId(getContext());
                    int trangThai = (chk_fBan_conDung.isChecked())?0:1;
                    list.clear();
                    list.addAll(banDAO.getLocLoaiBan(maNV,trangThai,edTimKiemBan.getText().toString().trim(),tenLoaiBan));
                    banAdapter.notifyDataSetChanged();

                }
            }
        });
        rcvFilter.setAdapter(loaiBanFiterAdapter);
    }
}