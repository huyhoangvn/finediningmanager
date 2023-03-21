package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.DatBan;

public class DatBanDAO {
    private SQLiteDatabase db;

    public DatBanDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insertDatBan(DatBan dBan){
        ContentValues  values = new ContentValues();
        values.put("maBan",dBan.getMaBan());
        values.put("maHD",dBan.getMaHD());
        values.put("thoiGianDat",dBan.getThoiGianDat());

        return db.insert("datban",null,values);
    }
    public int updateDatBan(DatBan dBan){
        ContentValues  values = new ContentValues();
        values.put("maBan",dBan.getMaBan());
        values.put("maHD",dBan.getMaHD());
        values.put("thoiGianDat",dBan.getThoiGianDat());

        return db.update("datban",values,"maBan=?",new String[]{String.valueOf(dBan.getMaBan())});
    }

    public List<DatBan> getAllDatBan(){
        String sql = "SELECT *FROM datban";
        return getData(sql);
    }
    @SuppressLint("Range")
    public List<DatBan> getData(String sql, String...SelectArgs){
        List<DatBan> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while (c.moveToNext()){
            DatBan dBan = new DatBan();
            dBan.setMaBan(c.getInt(c.getColumnIndex("maBan")));
            dBan.setMaHD(c.getInt(c.getColumnIndex("maHD")));
            dBan.setThoiGianDat(c.getString(c.getColumnIndex("thoiGianDat")));

            list.add(dBan);
        }
        return list;


    }
}
