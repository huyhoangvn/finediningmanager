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
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.BanAdapter;
import sp23cp18103.nhom2.finedining.adapter.LoaiBanAdapter;
import sp23cp18103.nhom2.finedining.adapter.LoaiMonAdapter;
import sp23cp18103.nhom2.finedining.database.LoaiBanDAO;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Hiển thị danh sách loại bàn và thêm, sửa
 * */
public class LoaiBanFragment extends Fragment {
    RecyclerView rcv_loaiban;
    ArrayList<LoaiBan> list;
    LoaiBanDAO loaiBanDAO;
    EditText edTenLoaiBan;
    CheckBox chkTrangThaiLoaiBan;
    Button btn_ShaveLoaiBan, btn_CancelLoaiBan;
    CheckBox chk_fLoaiBan_conDung;
    FloatingActionButton fab;
    LoaiBan loaiBan;
    Context context;
    LoaiBanAdapter loaiBanAdapter;
    TextView tvTieuDeLoaiBan;
    EditText edTimKhiemLoaiBan;
    TextInputLayout inputTimKiemLoaiBan;
    NhanVienDAO nhanVienDAO;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loai_ban, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chk_fLoaiBan_conDung = view.findViewById(R.id.chk_fLoaiBan_conDung);
        rcv_loaiban = view.findViewById(R.id.rcv_loaiban);
        fab = view.findViewById(R.id.ftbtn_them_loaiban);
        inputTimKiemLoaiBan = view.findViewById(R.id.inputTimKiemLoaiBan);
        edTimKhiemLoaiBan = view.findViewById(R.id.edTimKiemLoaiBan);

        loaiBanDAO = new LoaiBanDAO(getContext());
        context = getContext();
        anChucNang();
        khoiTaoRecyclerView();
        khoiTaoCheckboxListener();
        khoiTaoTimKiem();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
    }

    public void openDialog(final Context context, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_loai_ban, null);
        builder.setView(view);

        edTenLoaiBan = view.findViewById(R.id.edTenLoaiBan);
        tvTieuDeLoaiBan = view.findViewById(R.id.tvTieuDeLoaiBan);
        tvTieuDeLoaiBan.setText("Thêm loại bàn");
        chkTrangThaiLoaiBan = view.findViewById(R.id.chkTrangThaiLoaiBan);
        btn_ShaveLoaiBan = view.findViewById(R.id.btn_ShaveLoaiBan);
        btn_CancelLoaiBan = view.findViewById(R.id.btn_CancelLoaiBan);

        Dialog dialog = builder.create();
        if (type != 0) {
            edTenLoaiBan.setText(loaiBan.getTenLoai());
            if (loaiBan.getTrangThai() == 1) {
                chkTrangThaiLoaiBan.setChecked(true);
            } else {
                chkTrangThaiLoaiBan.setChecked(false);
            }
        }
        btn_CancelLoaiBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });
        btn_ShaveLoaiBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate() > 0) {
                    int maNV = PreferencesHelper.getId(getContext());
                    loaiBan = new LoaiBan();
                    loaiBan.setTenLoai(edTenLoaiBan.getText().toString());
                    loaiBan.setMaNV(maNV);
                    if (chkTrangThaiLoaiBan.isChecked()) {
                        loaiBan.setTrangThai(1);
                    } else {
                        loaiBan.setTrangThai(0);
                    }
                    if (type == 0) {
                        if (loaiBanDAO.insertloaiban(loaiBan) > 0) {
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
    @SuppressLint("NotifyDataSetChanged")
    void capNhat() {
        int maNV = PreferencesHelper.getId(context);
        int trangThai = (chk_fLoaiBan_conDung.isChecked())?0:1;
        list.clear();
        list.addAll(loaiBanDAO.getTimKiem(maNV,edTimKhiemLoaiBan.getText().toString().trim(),trangThai));
        loaiBanAdapter.notifyDataSetChanged();
    }

    public int validate() {
        String tenLoai = edTenLoaiBan.getText().toString();
        int check = 1;
        if (tenLoai.isEmpty()) {
            edTenLoaiBan.setError("Không được để trống");
            check = -1;
        }
        return check;
    }

    private void khoiTaoRecyclerView() {
        list = (ArrayList<LoaiBan>) loaiBanDAO.getTimKiem(PreferencesHelper.getId(context),
                edTimKhiemLoaiBan.getText().toString().trim(),
                ((chk_fLoaiBan_conDung.isChecked())?0:1));
        loaiBanAdapter = new LoaiBanAdapter(list, getContext());
        rcv_loaiban.setAdapter(loaiBanAdapter);
    }

    private void khoiTaoCheckboxListener() {
        chk_fLoaiBan_conDung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                capNhat();
            }
        });
    }


    private void khoiTaoTimKiem() {
        edTimKhiemLoaiBan.addTextChangedListener(new TextWatcher() {
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
        edTimKhiemLoaiBan.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        inputTimKiemLoaiBan.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capNhat();
            }
        });
    }

    @Override
    public void onResume() {
        capNhat();
        super.onResume();
    }


    void anChucNang(){
        nhanVienDAO = new NhanVienDAO(context);
        int phanQuyen = nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(getContext()));
        if(phanQuyen == 0){
            fab.setVisibility(View.GONE);
        }
    }
}