package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.DatMon;

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
        return db.insert("datmon",null,values);
    }
    public int updateDatMon(DatMon dm){
        ContentValues values = new ContentValues();
        values.put("maMon",dm.getMaMon());
        values.put("maHD",dm.getMaHD());
        values.put("soLuong",dm.getSoLuong());

        return db.update("datmon",values,"maMon=?",new String[]{String.valueOf(dm.getMaMon())});

    }

    public List<DatMon> getAllDatMon(){
        String sql = "SELECT *FROM datmon";
        return getData(sql);
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
            list.add(dm);

        }
        return list;
    }
}
