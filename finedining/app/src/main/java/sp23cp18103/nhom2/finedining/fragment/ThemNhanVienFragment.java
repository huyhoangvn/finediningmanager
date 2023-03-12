package sp23cp18103.nhom2.finedining.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sp23cp18103.nhom2.finedining.R;

/*
* Người dùng có vai trò quản lý có thể thêm tài khoản
* Có thể dùng để sửa nhân viên nếu bỏ tài khoản và mật khẩu
* */
public class ThemNhanVienFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_them_nhan_vien, container, false);
    }
}