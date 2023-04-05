package sp23cp18103.nhom2.finedining.Interface;

import android.widget.TextView;

import sp23cp18103.nhom2.finedining.adapter.LoaiMonFilterAdapter;

public interface ILoaiMonFilter {
    public void locMon(String tenLoaiMon, LoaiMonFilterAdapter.FilterViewHolder holder);
}
