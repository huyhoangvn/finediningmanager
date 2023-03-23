package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.LoaiBan;

public class LoaiBanDAO {
    SQLiteDatabase db;

    public LoaiBanDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertloaiban(LoaiBan obj) {
        ContentValues values = new ContentValues();
        values.put("maNV", obj.getMaNV());
        values.put("tenLoai", obj.getTenLoai());
        values.put("soChoNgoi", obj.getSoChoNgoi());
        values.put("trangThai", obj.getTrangThai());
        return db.insert("loaiban", null, values);
    }

    public int updateloaiban(LoaiBan obj) {
        ContentValues values = new ContentValues();
        values.put("maNV", obj.getMaNV());
        values.put("tenLoai", obj.getTenLoai());
        values.put("soChoNgoi", obj.getSoChoNgoi());
        values.put("trangThai", obj.getTrangThai());
        return db.update("loaiban", values, "maLB=?", new String[]{String.valueOf(obj.getMaLB())});
    }

    public List<LoaiBan> getAllLoaiBan() {
        String sql = "SELECT * FROM loaiban";
        return getDaTa(sql);
    }

    @SuppressLint("Range")
    public List<LoaiBan> getDaTa(String sql, String... selectAvg) {
        List<LoaiBan> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor c = db.rawQuery(sql, selectAvg);
        while (c.moveToNext()) {
            LoaiBan obj = new LoaiBan();
            obj.setMaLB(c.getInt(c.getColumnIndex("maLB")));
            obj.setMaNV((c.getInt(c.getColumnIndex("maNV"))));
            obj.setTenLoai(c.getString(c.getColumnIndex("tenLoai")));
            obj.setSoChoNgoi(c.getInt(c.getColumnIndex("soChoNgoi")));
            obj.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
            list.add(obj);
        }
        return list;
    }

//    public LoaiBan getID(String id) {
//        String sql = "Select * from loaiban where tenLoai = ?";
//        List<LoaiBan> list = getDaTa(sql, id);
//        return list.get(0);
//    }

    // tìm kiếm tương đối theo nhân viên và tên loại bàn
    public List<LoaiBan> getTimKiem(int maNV, String tenloai  ) {
        String sql = "Select * from loaiban lb " +
                "JOIN nhanvien nv ON lb.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND lb.tenLoai LIKE ? ";
        return getDaTa(sql, String.valueOf(maNV), String.valueOf(tenloai + "%"));
    }
    public LoaiBan getID(int maLB){
        String sql="select*from loaiban where maLB=?";
        List<LoaiBan> list = getDaTa(sql, String.valueOf(maLB));
        return list.get(0);
    }
}
