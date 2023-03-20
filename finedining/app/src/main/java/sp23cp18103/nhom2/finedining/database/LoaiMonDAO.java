package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.HoaDon;
import sp23cp18103.nhom2.finedining.model.LoaiMon;

public class LoaiMonDAO {
    SQLiteDatabase db;
    public LoaiMonDAO(Context context){
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
    public long insertLoaiMon(LoaiMon lm){
        ContentValues values = new ContentValues();
        values.put("maNV",lm.getMaNV());
        values.put("tenLoai", lm.getTenLoai());
        values.put("trangThai", lm.getTrangThai());
        return db.insert("loaimon", null, values);
    }
    public int updateLoaiMon(LoaiMon lm){
        ContentValues values = new ContentValues();
        values.put("maNV",lm.getMaNV());
        values.put("tenLoai", lm.getTenLoai());
        values.put("trangThai", lm.getTrangThai());
        return db.update("loaimon", values,"maLM = ?", new String[]{String.valueOf(lm.getMaLM())});
    }
    public List<LoaiMon> getAllLoaiMon(){
        String sql ="SELECT *FROM loaimon";
        return getData(sql);
    }
    @SuppressLint("Range")
    public List<LoaiMon> getData(String sql, String...SelectArgs){
        List<LoaiMon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while (c.moveToNext()){
            LoaiMon lm = new LoaiMon();
            lm.setMaLM(c.getInt(c.getColumnIndex("maLM")));
            lm.setMaNV(c.getInt(c.getColumnIndex("maNV")));
            lm.setTenLoai(c.getString(c.getColumnIndex("tenLoai")));
            lm.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
            list.add(lm);
        }
        return list;
    }
    public boolean checkloaimon(String loaimon){
        String sql = String.format("select * from loaimon where tenLoai = '%s' ",loaimon);
        Cursor cursor = db.rawQuery(sql,null);
        return cursor.getCount() > 0;
    }
}
