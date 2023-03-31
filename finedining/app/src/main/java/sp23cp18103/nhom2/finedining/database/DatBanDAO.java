package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.DatBan;
import sp23cp18103.nhom2.finedining.model.ThongTinDatBan;
import sp23cp18103.nhom2.finedining.model.ThongTinDatMon;

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
        values.put("trangThai",dBan.getTrangThai());
        return db.insert("datban",null,values);
    }
    public int updateDatBan(DatBan dBan){
        ContentValues  values = new ContentValues();
        values.put("trangThai",dBan.getTrangThai());
        return db.update("datban",values," maBan = ? and maHD = ? ",
                new String[]{String.valueOf(dBan.getMaBan()), String.valueOf(dBan.getMaHD())});
    }

    @SuppressLint("Range")
    public List<DatBan> getData(String sql, String...SelectArgs){
        List<DatBan> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while (c.moveToNext()){
            DatBan dBan = new DatBan();
            dBan.setMaBan(c.getInt(c.getColumnIndex("maBan")));
            dBan.setMaHD(c.getInt(c.getColumnIndex("maHD")));
            dBan.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));

            list.add(dBan);
        }
        return list;
    }

    @SuppressLint("Range")
    public List<ThongTinDatBan> getLichSuDatBan(int maNV, int maHD) {
        ArrayList<ThongTinDatBan> list = new ArrayList<>();
        String sql = "SELECT b.viTri, db.maBan, db.maHD, db.trangThai FROM datban db " +
                "JOIN ban b ON b.maBan = db.maBan " +
                "JOIN hoadon hd ON hd.maHD = db.maHD " +
                "JOIN nhanvien nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND hd.maHD = ? " +
                "AND b.trangThai = 1 ";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maNV), String.valueOf(maHD)});
        while (cursor.moveToNext()){
            ThongTinDatBan thongTinDatBan = new ThongTinDatBan();
            thongTinDatBan.setViTri(cursor.getString(cursor.getColumnIndex("viTri")));
            thongTinDatBan.setMaBan(cursor.getInt(cursor.getColumnIndex("maBan")));
            thongTinDatBan.setMaHD(cursor.getInt(cursor.getColumnIndex("maHD")));
            thongTinDatBan.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));
            list.add(thongTinDatBan);
        }
        return list;
    }

    @SuppressLint("Range")
    public List<ThongTinDatBan> getDanhSachBanDaDat(int maNV, int maHD) {
        ArrayList<ThongTinDatBan> list = new ArrayList<>();
        String sql = "SELECT b.viTri, db.maBan, db.maHD, db.trangThai FROM datban db " +
                "JOIN ban b ON b.maBan = db.maBan " +
                "JOIN hoadon hd ON hd.maHD = db.maHD " +
                "JOIN nhanvien nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND hd.maHD = ? " +
                "AND db.trangThai = 1 " +
                "AND b.trangThai = 1 ";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maNV), String.valueOf(maHD)});
        while (cursor.moveToNext()){
            ThongTinDatBan thongTinDatBan = new ThongTinDatBan();
            thongTinDatBan.setViTri(cursor.getString(cursor.getColumnIndex("viTri")));
            thongTinDatBan.setMaBan(cursor.getInt(cursor.getColumnIndex("maBan")));
            thongTinDatBan.setMaHD(cursor.getInt(cursor.getColumnIndex("maHD")));
            thongTinDatBan.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));
            list.add(thongTinDatBan);
        }
        return list;
    }

    public int getKiemTraBanThuocHoaDon(int maBan, int maHD) {
        String sql = "SELECT * FROM datban db " +
                "WHERE maBan = ? " +
                "AND maHD = ? " +
                "AND trangThai = 1 ";
        return getData(sql, new String[]{String.valueOf(maBan), String.valueOf(maHD)}).size();
    }
}
