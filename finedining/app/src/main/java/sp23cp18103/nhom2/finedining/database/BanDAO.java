package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.LoaiBan;
import sp23cp18103.nhom2.finedining.model.Mon;


public class BanDAO {
    SQLiteDatabase db;

    public BanDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertban(Ban obj) {
        ContentValues values = new ContentValues();
        values.put("maLB", obj.getMaLB());
        values.put("viTri", obj.getViTri());
        values.put("trangThai", obj.getTrangThai());
        return db.insert("ban", null, values);
    }

    public int updateban(Ban obj) {
        ContentValues values = new ContentValues();
        values.put("maLB", obj.getMaLB());
        values.put("viTri", obj.getViTri());
        values.put("trangThai", obj.getTrangThai());
        return db.update("ban", values, "maBan=?", new String[]{String.valueOf(obj.getMaBan())});
    }

    public int deleteban(String id) {
        return db.delete("ban", "maBan=?", new String[]{id});
    }



    @SuppressLint("Range")
    public String getViTri(int maBan) {
        String sql = "SELECT viTri From ban " +
                "WHERE maBan = ? ";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maBan)});
        if (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex("viTri"));
        }
        return "";
    }


    public List<Ban> trangThaiBan(int maNV, int trangThai, String timKiem) {
        String sql = "Select b.maBan,b.maLB, b.trangThai, b.viTri from ban b " +
                "JOIN loaiban lb ON lb.maLB = lb.maLB " +
                "JOIN nhanvien nv ON lm.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND b.trangThai >= ? " +
                "AND b.maBan ? " +
                "ORDER BY b.trangThai DESC, b.maBan ASC";
        return getDaTa(sql, String.valueOf(maNV), String.valueOf(trangThai),String.valueOf("%" + timKiem + "%"));
    }


    public List<Ban> gettimKiem(int maNV, String timKiem, int trangThai) {
        String sql = "Select b.maBan,b.maLB,b.viTri,b.trangThai from ban b " +
                "JOIN loaiban lb ON lb.maLB = b.maLB " +
                "JOIN nhanvien nv ON lb.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND b.vitri LIKE ? " +
                "AND b.trangThai = ?";
        return getDaTa(sql, String.valueOf(maNV), String.valueOf("%" + timKiem + "%"), String.valueOf(trangThai));
    }

    /*
     * Lấy danh sách bàn cả còn đầy và trống
     * */
    public List<Ban> getDanhSachBan(int maNV) {
        String sql = "Select b.maBan, b.maLB, b.viTri, b.trangThai " +
                "FROM ban b " +
                "JOIN loaiban lb ON lb.maLB = b.maLB " +
                "JOIN nhanvien nv ON lb.maNV = nv.maNV " +
                "WHERE nv.maNH = ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND b.trangThai = 1";
        return getDaTa(sql, String.valueOf(maNV));
    }

    /*
     * Trả về bằng 1 là bàn đầy còn 0 là bàn trống
     * */
    public int getKiemTraConTrong(int maNV, int maBan) {
        String sql = "Select * from ban b " +
                "JOIN datban db on db.maBan = b.maBan " +
                "JOIN hoadon hd on db.maHD = hd.maHD " +
                "JOIN nhanvien nv on nv.maNV = hd.maNV " +
                "WHERE nv.maNH = (SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND b.maBan = ? " +
                "AND hd.trangThai = 2 " +
                "AND db.trangThai = 1 ";
        return getDaTa(sql, String.valueOf(maNV), String.valueOf(maBan)).size();
    }

    @SuppressLint("Range")
    public int getTuDongChuyenTrangThai(int maBan, int maNV) {
        String sql = "SELECT lb.maLB FROM loaiban lb " +
                "JOIN ban b ON b.maLB = lb.maLB " +
                "JOIN nhanvien nv ON nv.maNV = lb.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND lb.trangThai = 0 " +
                "AND lb.maLB = ( SELECT b2.maLB FROM ban b2 WHERE b2.maBan = ? ) ";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(maNV), String.valueOf(maBan)});
        int maLB = 0;
        if (c.moveToNext()) {
            maLB = c.getInt(c.getColumnIndex("maLB"));
        }
        ContentValues values = new ContentValues();
        values.put("trangThai", 1);
        return db.update("loaiban", values, "maLB = ?", new String[]{String.valueOf(maLB)});
    }
    public List<Ban> getLocLoaiBan(int maNV, int trangThai, String timKiem, String tenLoai) {
        String sql = "Select b.maBan, b.maLB, B.viTri, b.trangThai from ban b " +
                "JOIN loaiban lb ON b.maLB = lb.maLB " +
                "JOIN nhanvien nv ON lb.maNV = nv.maNV " +
                "WHERE nv.maNH = ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND b.trangThai = ? " +
                "AND b.viTri LIKE ? " +
                "AND lb.tenLoai  LIKE ? ";
        return getDaTa(sql, String.valueOf(maNV), String.valueOf(trangThai),String.valueOf("%" + timKiem + "%"), String.valueOf("%" + tenLoai+ "%"));
    }
    @SuppressLint("Range")
    public List<Ban> getDaTa(String sql, String... selectavg) {
        List<Ban> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectavg);
        while (c.moveToNext()) {
            Ban obj = new Ban();
            obj.setMaBan(Integer.parseInt(c.getString(c.getColumnIndex("maBan"))));
            obj.setMaLB(Integer.parseInt(c.getString(c.getColumnIndex("maLB"))));
            obj.setViTri(c.getString(c.getColumnIndex("viTri")));
            obj.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
            list.add(obj);
        }
        return list;
    }
}
