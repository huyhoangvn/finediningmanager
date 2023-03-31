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
import sp23cp18103.nhom2.finedining.model.NhanVien;

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
    public List<LoaiMon> trangThaiLoaiMon(int maNV, int trangThai, String timKiem) {
        String sql = "Select lm.maLM, lm.maNV, lm.tenLoai, lm.trangThai from loaimon lm " +
                "JOIN nhanvien nv ON lm.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND lm.trangThai = ? " +
                "AND lm.tenLoai LIKE ? " +
                "ORDER BY lm.trangThai DESC, lm.tenLoai ASC";
        return getData(sql, String.valueOf(maNV), String.valueOf(trangThai),String.valueOf("%" + timKiem + "%"));
    }
    public int getLienKetTrangThai(int maLM, int maNV) {
        String sql = "Select m.maMon, lm.maLM from mon m " +
                "JOIN loaimon lm ON m.maLM = lm.maLM " +
                "JOIN nhanvien nv ON lm.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND m.trangThai = 1 " +
                "AND m.maLM = ? " ;
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(maNV), String.valueOf(maLM)});
        return c.getCount();
    }
    public LoaiMon getId(String id){
        String sql = "select * from loaimon where maLM = ?";
        List<LoaiMon> list = getData(sql,id);
        return list.get(0);
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
