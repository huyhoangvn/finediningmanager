package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.BanAdapter;
import sp23cp18103.nhom2.finedining.adapter.BanSpinnerAdapter;
import sp23cp18103.nhom2.finedining.adapter.LoaiBanAdapter;
import sp23cp18103.nhom2.finedining.adapter.NhanVienAdapter;
import sp23cp18103.nhom2.finedining.database.BanDAO;
import sp23cp18103.nhom2.finedining.database.LoaiBanDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.model.NhanVien;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Hiển thị danh sách Bàn, thêm, sửa bàn
 * */
public class BanFragment extends Fragment {
    RecyclerView rcvBan;
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
    TextInputEditText edTimKhiemBan;
    LoaiBanDAO loaiBanDAO;
    TextInputLayout inputTimKiemViTri;
    EditText edTimBan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ban, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvBan = view.findViewById(R.id.rcvBan);
        edTimBan = view.findViewById(R.id.edTimKiemBan);
        inputTimKiemViTri = view.findViewById(R.id.inputTimKiemViTri);

        chk_fBan_conDung = view.findViewById(R.id.chk_fBan_conDung);
        fab = view.findViewById(R.id.fbtnBan);
        banDAO = new BanDAO(getContext());
        context = getContext();


        khoiTaoRecyclerView();
        khoiTaoCheckboxListener();
        khoiTaoTimKiem();
        CapNhat();
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
        tvTieuDeBan.setText("Thêm loại bàn");

        chkTrangThaiBan = view.findViewById(R.id.chkTrangThaiBan);
        btnShaveBan = view.findViewById(R.id.btnShaveBan);
        btnCancelBan = view.findViewById(R.id.btnCancelBan);
        Dialog dialog = builder.create();

        banDAO = new BanDAO(getContext());
        loaiBanDAO = new LoaiBanDAO(getContext());
        listloaiban = (ArrayList<LoaiBan>) loaiBanDAO.getAllLoaiBan();
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
                    dialog.dismiss();
                    CapNhat();

                }
            }
        });
        dialog.show();
    }

    void CapNhat() {
        list = (ArrayList<Ban>) banDAO.getAllBan();
        banAdapter = new BanAdapter(getContext(), list);
        rcvBan.setAdapter(banAdapter);
    }

    public int validate() {
        int check = 1;
        if (edViTriBan.getText().toString().trim().isEmpty()) {
            edViTriBan.setError("Không được để trống");
            check = -1;
        } else {
        }
        return check;
    }

    private void khoiTaoRecyclerView() {
        list = (ArrayList<Ban>) banDAO.gettimKiem(PreferencesHelper.getId(context),
                edTimBan.getText().toString().trim(),
                String.valueOf((chk_fBan_conDung.isChecked())?0:1));

        banAdapter = new BanAdapter(getContext(), list);
        rcvBan.setAdapter(banAdapter);
    }

    private void khoiTaoCheckboxListener() {
        chk_fBan_conDung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hienThiDanhSachBan();
            }
        });
    }


    private void khoiTaoTimKiem() {
        edTimBan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hienThiDanhSachBan();
            }
        });
        edTimBan.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hienThiDanhSachBan();
                    return true;
                }
                return false;
            }
        });
        inputTimKiemViTri.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiDanhSachBan();
            }
        });
    }

    @Override
    public void onResume() {
        hienThiDanhSachBan();
        super.onResume();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void hienThiDanhSachBan() {
        list.clear();
        list.addAll(banDAO.gettimKiem(PreferencesHelper.getId(context),
                edTimBan.getText().toString().trim(),
                String.valueOf((chk_fBan_conDung.isChecked())?0:1)));
        banAdapter.notifyDataSetChanged();
    }
}