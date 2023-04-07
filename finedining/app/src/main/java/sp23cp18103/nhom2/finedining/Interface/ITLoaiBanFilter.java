package sp23cp18103.nhom2.finedining.Interface;

import sp23cp18103.nhom2.finedining.adapter.LoaiBanFiterAdapter;
import sp23cp18103.nhom2.finedining.adapter.LoaiMonFilterAdapter;

public interface ITLoaiBanFilter {
    public void loaiBan(String tenLoaiBan, LoaiBanFiterAdapter.filterViewHolder holder);
}
