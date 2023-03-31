package sp23cp18103.nhom2.finedining.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sp23cp18103.nhom2.finedining.R;
import sp23cp18103.nhom2.finedining.adapter.MonBanChayAdapter;
import sp23cp18103.nhom2.finedining.database.ThongTinMonDAO;
import sp23cp18103.nhom2.finedining.model.ThongTinMon;

/*
 * Hiển thị danh sách món ăn bán chạy
 * */
public class ThongKeMonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke_mon, container, false);
    }
    List<ThongTinMon> list;
    RecyclerView rcvMonHot;
    MonBanChayAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvMonHot = view.findViewById(R.id.rcv_monhot);
        ThongTinMonDAO tinMonDAO = new ThongTinMonDAO(getContext());
        list = tinMonDAO.getTopMon();
        list.toString();
        adapter = new MonBanChayAdapter(getContext(),list);
        rcvMonHot.setAdapter(adapter);

    }
}