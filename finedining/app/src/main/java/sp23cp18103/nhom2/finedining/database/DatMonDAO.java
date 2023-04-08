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
        db = dbHelper.getWritableDatabase();

    }

    public int updateDatMonSoluong(DatMon dm) {
        ContentValues values = new ContentValues();
        values.put("soLuong", dm.getSoLuong());
        values.put("trangThai",dm.getTrangThai());
        String selection = "maHD = ? AND maMon = ?";
        String[] selectionArgs = {String.valueOf(dm.getMaHD()), String.valueOf(dm.getMaMon())};
        return db.update("datmon", values, selection, selectionArgs);
    }

    public long insertDatMon(DatMon dm){
        ContentValues values = new ContentValues();
        values.put("maMon",dm.getMaMon());
        values.put("maHD",dm.getMaHD());
        values.put("soLuong",dm.getSoLuong());
        values.put("trangThai",dm.getTrangThai());
        return db.insert("datmon",null,values);
    }

//    public int updateDatMon(DatMon dm){
//       ContentValues values = new ContentValues();
//        values.put("soLuong",dm.getSoLuong());
//        values.put("trangThai",dm.getTrangThai());
//        return db.update("datmon",values,"maMon = ? AND maHD = ?",new String[]{String.valueOf(dm.getMaMon()),String.valueOf(dm.getMaHD())});
//    }



    public boolean checkDatMonExist(int maHD, int maMon) {
        String query = "SELECT * FROM datmon WHERE maHD = ? AND maMon = ?";
        String[] selectionArgs = {String.valueOf(maHD), String.valueOf(maMon)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
//    @SuppressLint("Range")
//    public List<ThongTinDatMon> getSlMon(int maHD) {
//        List<ThongTinDatMon> list = new ArrayList<>();
//        String sql = "SELECT datmon.soLuong FROM datmon JOIN mon on mon.maMon = datmon.maMon WHERE datmon.maHD = ?";
//        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD)});
//        if (cursor.moveToNext()) {
//            ThongTinDatMon datMon = new ThongTinDatMon();
//            datMon.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
//            list.add(datMon);
//        }
//        return list;
//    }

    @SuppressLint("Range")
    public List<ThongTinDatMon> getDatMonTheoHoaDon(int maHD,int maNV) {
        List<ThongTinDatMon> list = new ArrayList<>();
        String sql = "SELECT m.tenMon, dm.soLuong, dm.maMon, dm.maHD, dm.trangThai  FROM datmon dm" +
                " JOIN mon m on m.maMon = dm.maMon " +
                " JOIN loaimon lm on m.maLM = lm.maLM" +
                " JOIN nhanVien nv on nv.maNV = lm.maNV" +
                " WHERE dm.maHD = ?" +
                " AND nv.maNH = (Select nv1.maNH FROM nhanvien nv1 where nv1.maNV = ?) " +
                " AND dm.trangThai = 1";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD), String.valueOf(maNV)});
        while (cursor.moveToNext()) {
            ThongTinDatMon datMon = new ThongTinDatMon();
            datMon.setTenMon(cursor.getString(cursor.getColumnIndex("tenMon")));
            datMon.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
            datMon.setMaMon(cursor.getInt(cursor.getColumnIndex("maMon")));
            datMon.setMaHD(cursor.getInt(cursor.getColumnIndex("maHD")));
            datMon.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));
            list.add(datMon);
        }
        cursor.close();
        return list;
    }

    @SuppressLint("Range")
    public List<ThongTinDatMon> getLichSuDatMonTheoHoaDon(int maHD,int maNV) {
        List<ThongTinDatMon> list = new ArrayList<>();
        String sql = "SELECT m.tenMon, dm.soLuong, dm.maMon, dm.maHD, dm.trangThai  FROM datmon dm" +
                " JOIN mon m on m.maMon = dm.maMon " +
                " JOIN loaimon lm on m.maLM = lm.maLM" +
                " JOIN nhanVien nv on nv.maNV = lm.maNV" +
                " WHERE dm.maHD = ?" +
                " AND nv.maNH = (Select nv1.maNH FROM nhanvien nv1 where nv1.maNV = ?) ";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD), String.valueOf(maNV)});
        while (cursor.moveToNext()) {
            ThongTinDatMon datMon = new ThongTinDatMon();
            datMon.setTenMon(cursor.getString(cursor.getColumnIndex("tenMon")));
            datMon.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
            datMon.setMaMon(cursor.getInt(cursor.getColumnIndex("maMon")));
            datMon.setMaHD(cursor.getInt(cursor.getColumnIndex("maHD")));
            datMon.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));
            list.add(datMon);
        }
        cursor.close();
        return list;
    }



    //    @SuppressLint("Range")
//    public List<ThongTinDatMon> getDatMonTheoHoaDon(int maHD) {
//        List<ThongTinDatMon> list = new ArrayList<>();
//        String sql = "SELECT mon.tenMon,datmon.soLuong FROM datmon JOIN mon on mon.maMon = datmon.maMon WHERE datmon.maHD = ?";
//        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD)});
//        while (cursor.moveToNext()) {
//            ThongTinDatMon datMon = new ThongTinDatMon();
//            datMon.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
//            datMon.setTenMon(cursor.getString(cursor.getColumnIndex("tenMon")));
//            list.add(datMon);
//        }
//        cursor.close();
//        return list;
//    }
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
