package sp23cp18103.nhom2.finedining.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.BanAdapter;
import sp23cp18103.nhom2.finedining.adapter.BanSpinnerAdapter;
import sp23cp18103.nhom2.finedining.adapter.LoaiBanAdapter;
import sp23cp18103.nhom2.finedining.database.BanDAO;
import sp23cp18103.nhom2.finedining.database.LoaiBanDAO;
import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

/*
 * Hiển thị danh sách Bàn, thêm, sửa bàn
 * */
public class BanFragment extends Fragment {
    RecyclerView rcvBan;
    FloatingActionButton fab;
    List<Ban> list, list2;
    EditText edViTriBan;
    CheckBox chkTrangThaiBan;
    AppCompatButton btnShaveBan, btnCancelBan;
    BanDAO banDAO;
    Spinner spnrBan;
    BanAdapter banAdapter;
    Context context;
    TextView tvTieuDeBan;
    Ban ban;
    BanSpinnerAdapter banSpinnerAdapter;
    ArrayList<LoaiBan> listloaiban;
    int maLoaiBan, positonLB;
    TextInputEditText edTimKhiemBan;
    LoaiBanDAO loaiBanDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ban, container, false);
        rcvBan = view.findViewById(R.id.rcvBan);
        edTimKhiemBan = view.findViewById(R.id.edTimKiemBan);
        fab = view.findViewById(R.id.fbtnBan);
        banDAO = new BanDAO(getContext());

        CapNhat();
        timkiemBan();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getContext(), 0);
            }
        });
        return view;

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

                    ban = new Ban();
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
        list = banDAO.getAllBan();
        banAdapter = new BanAdapter(getContext(), list);
        rcvBan.setAdapter(banAdapter);
    }



    public int validate() {
        int check = 1;
        if (edViTriBan.getText().length() == 0) {
            Toast.makeText(getContext(), "Dữ liệu không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {

        }
        return check;
    }

    void timkiemBan() {
        list2 = banDAO.getAllBan();
        banAdapter = new BanAdapter(getContext(), list2);
        rcvBan.setAdapter(banAdapter);
        edTimKhiemBan.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    int maNV = PreferencesHelper.getId(getContext());
                    String timKiem = edTimKhiemBan.getText().toString().trim();
                    if (timKiem.isEmpty()) {
                        list = banDAO.getAllBan();
                        banAdapter = new BanAdapter(getActivity(), list);
                        rcvBan.setAdapter(banAdapter);
                        return false;
                    } else {
                        list = banDAO.timKiem(maNV, timKiem);
                        banAdapter = new BanAdapter(getContext(), list);
                        rcvBan.setAdapter(banAdapter);

                    }

                    return true;
                }
                return false;
            }
        });
    }
    }