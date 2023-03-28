package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.DatMon;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.ThongTinDatMon;

public class DatMonDAO {
    private SQLiteDatabase db;

    public DatMonDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db =dbHelper.getWritableDatabase();

    }
    public long insertDatMon(DatMon dm){
        ContentValues values = new ContentValues();
        values.put("maMon",dm.getMaMon());
        values.put("maHD",dm.getMaHD());
        values.put("soLuong",dm.getSoLuong());
        values.put("trangThai",dm.getTrangThai());
        return db.insert("datmon",null,values);
    }

    public int updateDatMon(DatMon dm){
        ContentValues values = new ContentValues();
        values.put("soLuong",dm.getSoLuong());
        values.put("trangThai",dm.getTrangThai());
        values.put("trangThai",dm.getTrangThai());
        return db.update("datmon",values,"maMon=? AND maHD=?",new String[]{String.valueOf(dm.getMaMon()),String.valueOf(dm.getMaHD())});

    }

    @SuppressLint("Range")
    public ThongTinDatMon getMon(int maHD) {
        ThongTinDatMon datMon = new ThongTinDatMon();
        String sql = "SELECT mon.tenMon,datmon.soLuong FROM datmon JOIN mon on mon.maMon = datmon.maMon WHERE datmon.maHD = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD)});
        if (cursor.moveToNext()) {
             datMon.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
             datMon.setTenMon(cursor.getString(cursor.getColumnIndex("tenMon")));
        }
        return datMon;
    }
    @SuppressLint("Range")
    public List<ThongTinDatMon> getDatMonTheoHoaDon(int maHD) {
        ThongTinDatMon datMon = new ThongTinDatMon();
        List<ThongTinDatMon> list = new ArrayList<>();
        String sql = "SELECT mon.tenMon,datmon.soLuong FROM datmon JOIN mon on mon.maMon = datmon.maMon WHERE datmon.maHD = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD)});
        while (cursor.moveToNext()) {
            datMon.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
            datMon.setTenMon(cursor.getString(cursor.getColumnIndex("tenMon")));
            list.add(datMon);
        }
        return list;
    }
    @SuppressLint("Range")
    public List<DatMon> getData(String sql, String...SelectArgs){
        List<DatMon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while (c.moveToNext()){
            DatMon dm = new DatMon();
            dm.setMaMon(c.getInt(c.getColumnIndex("maMon")));
            dm.setMaHD(c.getInt(c.getColumnIndex("maHD")));
            dm.setSoLuong(c.getInt(c.getColumnIndex("soLuong")));
            dm.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
            list.add(dm);

        }
        return list;
    }
}
