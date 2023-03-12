package sp23cp18103.nhom2.finedining.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sp23cp18103.nhom2.finedining.R;

/*
* Hiển thị danh sách Bàn, thêm, sửa bàn
* */
public class BanFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ban, container, false);
    }
}