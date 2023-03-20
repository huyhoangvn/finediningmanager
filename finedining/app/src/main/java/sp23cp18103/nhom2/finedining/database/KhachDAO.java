package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.KhachHang;

public class KhachDAO {
    SQLiteDatabase db;

    public KhachDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(KhachHang obj) {
        ContentValues values = new ContentValues();
        values.put("tenKH", obj.getTenKH());
        return db.insert("khachhang", null, values);
    }

    @SuppressLint("Range")
    public List<KhachHang> getDaTa(String sql, String... selectAvg) {
        List<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectAvg);
        while (c.moveToNext()) {
            KhachHang obj = new KhachHang();
            obj.setMaKH(Integer.parseInt(c.getString(c.getColumnIndex("maKH"))));
            obj.setTenKH(c.getString(c.getColumnIndex("tenKH")));
            obj.setGioiTinh(Integer.parseInt(c.getString(c.getColumnIndex("gioiTinh"))));
            obj.setSdt(c.getString(c.getColumnIndex("sdt")));
            obj.setTaiKhoan(c.getString(c.getColumnIndex("taiKhoan")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            obj.setHinh(c.getString(c.getColumnIndex("hinh")));
            list.add(obj);
        }
        return list;
    }
}
