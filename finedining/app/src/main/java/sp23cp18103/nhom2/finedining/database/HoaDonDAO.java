package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.HoaDon;

public class HoaDonDAO{
    private SQLiteDatabase db;

    public HoaDonDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();

    }
    /*insert hóa đơn*/
    public long insertHoaDon(HoaDon hd){
        ContentValues values = new ContentValues();
        values.put("maKH",hd.getMaKH());
        values.put("maNV",hd.getMaNV());
        values.put("soLuongKhach",hd.getSoLuongKhach());
        values.put("thoiGianXuat",hd.getThoiGianXuat());
        values.put("thoiGianDat",hd.getThoiGianDat());
        values.put("trangThai",hd.getTrangThai());

        return db.insert("hoadon",null,values);

    }
    /*update hóa đơn*/
    public int updateHoaDon(HoaDon hd){
        ContentValues values = new ContentValues();
        values.put("maKH",hd.getMaKH());
        values.put("maNV",hd.getMaNV());
        values.put("soLuongKhach",hd.getSoLuongKhach());
        values.put("thoiGianDat",hd.getThoiGianDat());
        values.put("trangThai",hd.getTrangThai());

        return db.update("hoadon",values,"maHD=?",new String[]{String.valueOf(hd.getMaHD())});

    }
    /*getAll danh sach hóa đơn*/
    public List<HoaDon> getAllHoaDon(){
        String sql ="SELECT *FROM hoadon";
        return getData(sql);
    }
    @SuppressLint("Range")
    public int getMaKhachHang(int maHD){
        String sql = "SELECT maKH FROM hoadon WHERE maHD = ? ";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD)});
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("maKH"));
        }
        return -1;
    }
    /* hóa đơn*/
    public HoaDon getID(String id){
        String sql = "SELECT * FROM hoadon WHERE maHD = ? ";
        List<HoaDon> list = getData(sql,id);
        if(list==null){
            return null;
        }else
            return list.get(0);
    }
    @SuppressLint("Range")
    public int getSoLuongKhach(int maHD) {
        String sql = "SELECT soLuongKhach FROM hoadon WHERE maHD = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD)});
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("soLuongKhach"));
        }
        return -1;
    }
    @SuppressLint("Range")
    public String getNgayDat(int maHD) {
        String sql = "SELECT thoiGianDat FROM hoadon WHERE maHD = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD)});
        if (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex("thoiGianDat"));
        }
        return "";
    }
    @SuppressLint("Range")
    public int getTrangThai(int maHD) {
        String sql = "SELECT trangThai FROM hoadon WHERE maHD = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD)});
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("trangThai"));
        }
        return -1;
    }

//    @SuppressLint("Range")
//    public int getMaHoaDonTiepTheo (){
//        String sql = " SELECT seq FROM sqlite_sequence WHERE name LIKE 'hoadon' ";
//        @SuppressLint("Recycle") Cursor c = db.rawQuery(sql, null);
//        if(c.moveToNext()){
//            return c.getInt(c.getColumnIndex("seq"));
//        }
//        return 0;
//    }

    @SuppressLint("Range")
    public int getMaHoaDonTiepTheo() {
        String sql = "SELECT seq FROM sqlite_sequence WHERE name = 'hoadon'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("seq")) + 1;
        }
        //Trường hợp bảng khách hàng chưa có phần tử nào
        return 1;
    }

    @SuppressLint("Range")
    public List<HoaDon> getData(String sql, String...SelectArgs){
        List<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while (c.moveToNext()){
            HoaDon hd = new HoaDon();
            hd.setMaHD(c.getInt(c.getColumnIndex("maHD")));
            hd.setMaKH(c.getInt(c.getColumnIndex("maKH")));
            hd.setMaNV(c.getInt(c.getColumnIndex("maNV")));
            hd.setSoLuongKhach(c.getInt(c.getColumnIndex("soLuongKhach")));
            hd.setThoiGianXuat(c.getString(c.getColumnIndex("thoiGianXuat")));
            hd.setThoiGianDat(c.getString(c.getColumnIndex("thoiGianDat")));
            hd.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
            list.add(hd);
        }
        return list;
    }

/*
*
* */



}
