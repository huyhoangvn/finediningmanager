package sp23cp18103.nhom2.finedining.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.database.NhanVienDAO;
import sp23cp18103.nhom2.finedining.utils.PreferencesHelper;

public class NhanVienCollectionFragment extends Fragment {
    private Context context;
    //Utils
    private FragmentManager fmNhanVien;
    //Database
    private NhanVienDAO nhanVienDAO;
    //Controller
    private FloatingActionButton fbtnThemNhanVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nhan_vien_collection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        anhXa(view);
        khoiTaoDAO();
        khoiTaoFragmentManager();
        khoiTaoPhanQuyen();
        khoiTaoListener();
    }

    /*
    * Ánh xạ các view cần dùng
    * */
    private void anhXa(View view) {
        fbtnThemNhanVien = view.findViewById(R.id.ftbtn_fNhanVien_them);
    }

    /*
    * Khởi tạo các lớp DAO
    * */
    private void khoiTaoDAO() {
        nhanVienDAO = new NhanVienDAO(context);
    }

    private void khoiTaoFragmentManager() {
        fmNhanVien = getParentFragmentManager();
        fmNhanVien.beginTransaction()
                .replace(R.id.lyt_fNhanVienCollection_fragmentManager, new NhanVienFragment())
                .commit();
    }

    /*
    * Tùy theo phân quyền của người dùng hiện tại mà hiển thị
    * Người dùng không có phân quyền là 1 (quản lý) thì sẽ floating action button thêm nhân viên
    * */
    private void khoiTaoPhanQuyen() {
        if(nhanVienDAO.getPhanQuyen(PreferencesHelper.getId(context)) != 1){
            fbtnThemNhanVien.hide();
        }
    }

    /*
    * Khởi tạo listener cho floating action button thêm mới
    * Khi ấn sẽ chuyển qua fragment thêm nhân viên và ẩn nút thêm
    * */
    private void khoiTaoListener() {
        fbtnThemNhanVien.setOnClickListener(v -> {
            fmNhanVien.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                    .add(R.id.lyt_fNhanVienCollection_fragmentManager, new ThemNhanVienFragment(0),
                            "THEM_NHAN_VIEN")
                    .addToBackStack(null)
                    .commit();
            fbtnThemNhanVien.hide();
        });
    }
}