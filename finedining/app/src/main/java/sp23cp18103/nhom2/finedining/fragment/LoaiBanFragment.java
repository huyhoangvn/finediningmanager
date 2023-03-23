package sp23cp18103.nhom2.finedining.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

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
import sp23cp18103.nhom2.finedining.adapter.LoaiBanAdapter;
import sp23cp18103.nhom2.finedining.database.LoaiBanDAO;
import sp23cp18103.nhom2.finedining.database.LoaiMonDAO;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Hiển thị danh sách loại bàn và thêm, sửa
 * */
public class LoaiBanFragment extends Fragment {
    List<LoaiBan> mListOld;
    RecyclerView rcv_loaiban;
    List<LoaiBan> list, list2, list3;
    LoaiBanDAO loaiBanDAO;
    Dialog dialog;
    EditText edTenLoaiBan, edSoChoNgoi;
    CheckBox chk_TrangThai_LoaiBan;
    Button btn_ShaveLoaiBan, btn_CancelLoaiBan;
    FloatingActionButton fab;
    LoaiBan loaiBan;
    Context context;
    LoaiBanAdapter loaiBanAdapter;
    TextView tvTieuDeLoaiBan;
    TextInputEditText edTimKhiemLoaiBan;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loai_ban, container, false);
        rcv_loaiban = view.findViewById(R.id.rcv_loaiban);
        fab = view.findViewById(R.id.ftbtn_them_loaiban);
        edTimKhiemLoaiBan = view.findViewById(R.id.edTimKiemLoaiBan);
        loaiBanDAO = new LoaiBanDAO(getContext());
        CapNhat();
        timkiem();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
        return view;
    }

    public void openDialog(final Context context, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_loai_ban, null);
        builder.setView(view);
        edTenLoaiBan = view.findViewById(R.id.edTenLoaiBan);
        edSoChoNgoi = view.findViewById(R.id.edSoChoNgoi);
        tvTieuDeLoaiBan = view.findViewById(R.id.tvTieuDeLoaiBan);
        tvTieuDeLoaiBan.setText("Thêm loại bàn");
        chk_TrangThai_LoaiBan = view.findViewById(R.id.chkTrangThaiLoaiBan);
        btn_ShaveLoaiBan = view.findViewById(R.id.btn_ShaveLoaiBan);
        btn_CancelLoaiBan = view.findViewById(R.id.btn_CancelLoaiBan);
        Dialog dialog = builder.create();
        if (type != 0) {
            edTenLoaiBan.setText(loaiBan.getTenLoai());
            edSoChoNgoi.setText(String.valueOf(loaiBan.getSoChoNgoi()));
            if (loaiBan.getTrangThai() == 1) {
                chk_TrangThai_LoaiBan.setChecked(true);
            } else {
                chk_TrangThai_LoaiBan.setChecked(false);
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
                    Log.d("aaaaaa", "onClick: " + maNV);
                    loaiBan = new LoaiBan();
                    loaiBan.setTenLoai(edTenLoaiBan.getText().toString());
                    loaiBan.setSoChoNgoi(Integer.parseInt(edSoChoNgoi.getText().toString()));
                    loaiBan.setMaNV(maNV);
                    if (chk_TrangThai_LoaiBan.isChecked()) {
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
                    dialog.dismiss();
                    CapNhat();
                }
            }
        });
        dialog.show();
    }

    void CapNhat() {
        list = loaiBanDAO.getAllLoaiBan();
        loaiBanAdapter = new LoaiBanAdapter(list, getContext());
        rcv_loaiban.setAdapter(loaiBanAdapter);
    }

    public int validate() {
        int check = 1;
        if (edTenLoaiBan.getText().length() == 0 || edSoChoNgoi.getText().length() == 0) {
            Toast.makeText(getContext(), "Dữ liệu không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        return check;
    }

    void timkiem() {
        list2 = loaiBanDAO.getAllLoaiBan();
        loaiBanAdapter = new LoaiBanAdapter(list2, getContext());
        rcv_loaiban.setAdapter(loaiBanAdapter);
        edTimKhiemLoaiBan.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    int maNV = PreferencesHelper.getId(getContext());
                    String timKiem = edTimKhiemLoaiBan.getText().toString().trim();
                    if (timKiem.isEmpty()) {
                        list = loaiBanDAO.getAllLoaiBan();
                        loaiBanAdapter = new LoaiBanAdapter(list, getActivity());
                        rcv_loaiban.setAdapter(loaiBanAdapter);
                        return false;
                    } else {
                        list = loaiBanDAO.getTimKiem(maNV, timKiem);
                        loaiBanAdapter = new LoaiBanAdapter(list, getActivity());
                        rcv_loaiban.setAdapter(loaiBanAdapter);
                    }

                    return true;
                }
                return false;
            }
        });
    }

}