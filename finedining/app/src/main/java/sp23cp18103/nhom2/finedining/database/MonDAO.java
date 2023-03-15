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
        values.put("maNV",mon.getMaLM());
        values.put("tenLoai", mon.getTenMon());
        values.put("trangThai", mon.getTrangThai());
        values.put("hinh", mon.getHinh());
        return db.insert("mon", null, values);
    }
    public int updateMon(Mon mon){
        ContentValues values = new ContentValues();
        values.put("maNV",mon.getMaLM());
        values.put("tenLoai", mon.getTenMon());
        values.put("trangThai", mon.getTrangThai());
        values.put("hinh", mon.getHinh());
        return db.update("mon", values,"maMon = ?", new String[]{String.valueOf(mon.getMaMon())});
    }
    public List<Mon> getAllMon(){
        String sql ="SELECT *FROM mon";
        return getData(sql);
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
            mon.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
            mon.setHinh(c.getString(c.getColumnIndex("hinh")));
            list.add(mon);
        }
        return list;
    }
}
