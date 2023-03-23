package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.LoaiMon;
import sp23cp18103.nhom2.finedining.model.Mon;

public class MonDAO {
    SQLiteDatabase db;
    public MonDAO(Context context){
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
    public long insertMon(Mon mon){
        ContentValues values = new ContentValues();
        values.put("maLM", mon.getMaLM());
        values.put("tenMon",mon.getTenMon());
        values.put("gia",mon.getGia());
        values.put("trangThai", mon.getTrangThai());
        values.put("hinh", mon.getHinh());
        return db. insert("mon", null, values);
    }
    public int updateMon(Mon mon){
        ContentValues values = new ContentValues();
        values.put("maLM", mon.getMaLM());
        values.put("tenMon",mon.getTenMon());
        values.put("gia",mon.getGia());
        values.put("trangThai", mon.getTrangThai());
        values.put("hinh", mon.getHinh());
        return db.update("mon", values,"maMon = ?", new String[]{String.valueOf(mon.getMaMon())});
    }
    public List<Mon> timKiem(int maNV, String tenmon ){
        String sql = "Select * from mon m " +
                "JOIN loaimon lm ON lm.maLM = m.maLM " +
                "JOIN nhanvien nv ON lm.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND m.tenMon LIKE ? ";
        return getData(sql, String.valueOf(maNV), String.valueOf(tenmon + "%"));
    }
    public List<Mon> getTenMon(){
        String sql ="SELECT tenMon From mon " +
                "WHERE maMon=? ";
        return getData(sql);
    }
    public boolean checkmon(String tenmon){
        String sql = String.format("select * from mon where tenMon = '%s' ",tenmon);
        Cursor cursor = db.rawQuery(sql,null);
        return cursor.getCount() > 0;
    }

    @SuppressLint("Range")
    public List<Mon> getData(String sql, String...SelectArgs){
        List<Mon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while (c.moveToNext()){
            Mon mon = new Mon();
            mon.setMaMon(c.getInt(c.getColumnIndex("maMon")));
            mon.setMaLM(c.getInt(c.getColumnIndex("maLM")));
            mon.setTenMon(c.getString(c.getColumnIndex("tenMon")));
            mon.setGia(c.getInt(c.getColumnIndex("gia")));
            mon.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
            mon.setHinh(c.getString(c.getColumnIndex("hinh")));
            list.add(mon);
        }
        return list;
    }


}
