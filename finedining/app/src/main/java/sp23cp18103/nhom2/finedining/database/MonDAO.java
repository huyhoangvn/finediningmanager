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
    public List<Mon> trangThaiLoaiMon(int maNV, int trangThai, String timKiem) {
        String sql = "Select m.maMon, m.maLM, m.tenMon, m.gia, m.trangThai, m.hinh from mon m " +
                "JOIN loaimon lm ON m.maLM = lm.maLM " +
                "JOIN nhanvien nv ON lm.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND m.trangThai = ? " +
                "AND m.tenMon LIKE ? " +
                "ORDER BY m.trangThai DESC, m.tenMon ASC";
        return getData(sql, String.valueOf(maNV), String.valueOf(trangThai),String.valueOf("%" + timKiem + "%"));
    }
    @SuppressLint("Range")
    public int getTuDongChuyenTrangThai(int maMon, int maNV){
        String sql = "SELECT lm.maLM FROM loaimon lm " +
                "JOIN mon m ON m.maLM = lm.maLM " +
                "JOIN nhanvien nv ON nv.maNV = lm.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND lm.trangThai = 0 " +
                "AND lm.maLM = ( SELECT m2.maLM FROM mon m2 WHERE m2.maMon = ? ) " ;
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(maNV), String.valueOf(maMon)});
        int maLM = 0;
        if(c.moveToNext()){
           maLM =  c.getInt(c.getColumnIndex("maLM"));
        }
        ContentValues values = new ContentValues();
        values.put("trangThai", 1 );
        return db.update("loaimon", values,"maLM = ?", new String[]{String.valueOf(maLM)} );
    }
    @SuppressLint("Range")
    public int getTrangThaiDatMon(int maMon, int maNV){
        int check = 0;
        String sql = "SELECT dm.maHD, dm.maMon, dm.soLuong, dm.trangThai  FROM datmon dm " +
                "JOIN hoadon hd ON hd.maHD = dm.maHD " +
                "JOIN nhanvien nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND dm.maMon = ? " +
                "AND dm.trangThai = 1 " +
                "AND (hd.trangThai = 1 OR hd.trangThai = 2) ";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(maMon), String.valueOf(maNV)});
        if(c.moveToNext()){
            int tempMaMon = 0;
            int tempMaHD = 0;
            tempMaMon = c.getInt(c.getColumnIndex("maMon"));
            tempMaHD = c.getInt(c.getColumnIndex("maHD"));
            ContentValues values = new ContentValues();
            values.put("trangThai", 0);
            db.update("datmon", values, "maMon = ? and maHD = ?", new String[]{String.valueOf(tempMaMon), String.valueOf(tempMaHD)});
            check++;
        }
        return check;
    }
    public List<Mon> timKiem(int maNV, String tenmon ){
        String sql = "Select * from mon m " +
                "JOIN loaimon lm ON lm.maLM = m.maLM " +
                "JOIN nhanvien nv ON lm.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND m.tenMon LIKE ? AND m.trangThai = 1";
        return getData(sql, String.valueOf(maNV), String.valueOf(tenmon + "%"));
    }


    
    @SuppressLint("Range")
    public String getTenMon(int maMon) {
        String sql = "SELECT tenMon From mon " +
                "WHERE maMon = ? ";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maMon)});
        if (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex("tenMon"));
        }
        return "";
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
