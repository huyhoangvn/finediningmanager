package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.ThongTinDatMon;
import sp23cp18103.nhom2.finedining.model.ThongTinMon;

public class ThongTinMonDAO {
    private SQLiteDatabase db;

    public ThongTinMonDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    @SuppressLint("Range")
    public List<ThongTinMon> getTop10MonSoLuongCaoNhatYearNow(String nam,int maNV) {
        List<ThongTinMon> list = new ArrayList<>();
        String sql = "SELECT m.hinh, m.tenMon, SUM(dm.soLuong) AS soluongmon, SUM(dm.soLuong * m.gia) AS doanhThumon" +
                " FROM mon m" +
                " INNER JOIN datmon dm ON m.maMon = dm.maMon" +
                " INNER JOIN hoadon hd ON dm.maHD = hd.maHD" +
                " JOIN nhanvien nv ON nv.maNV = hd.maNV " +
                " WHERE nv.maNH = (SELECT nvHT.maNH FROM nhanvien nvHT where nvHT.maNV = ?)" +
                " AND strftime('%Y', hd.thoiGianDat) LIKE ?" +
                " AND hd.trangThai = 3" +
                " GROUP BY m.tenMon " +
                " ORDER BY soluongmon DESC " +
                " LIMIT 10;";
        Cursor cursor = db.rawQuery(sql,new String[]{String.valueOf(maNV),nam});
        while (cursor.moveToNext()) {
            ThongTinMon ttMon = new ThongTinMon();
            ttMon.setHinhMon(cursor.getString(cursor.getColumnIndex("hinh")));
            ttMon.setTenMonThongKe(cursor.getString(cursor.getColumnIndex("tenMon")));
            ttMon.setSoLuongMon(cursor.getInt(cursor.getColumnIndex("soluongmon")));
            ttMon.setDoanhThuMon(cursor.getInt(cursor.getColumnIndex("doanhThumon")));
            list.add(ttMon);
        }
        cursor.close();
        return list;
    }

    @SuppressLint("Range")
    public List<ThongTinMon> getTop10MonDoanhThuCaoNhatYearNow(String nam, int maNV) {
        List<ThongTinMon> list = new ArrayList<>();
        String sql = "SELECT m.hinh, m.tenMon, SUM(dm.soLuong) AS soluongmon, SUM(dm.soLuong * m.gia) AS doanhThumon" +
                " FROM mon m" +
                " INNER JOIN datmon dm ON m.maMon = dm.maMon" +
                " INNER JOIN hoadon hd ON dm.maHD = hd.maHD" +
                " JOIN nhanvien nv ON nv.maNV = hd.maNV " +
                " WHERE nv.maNH = (SELECT nvHT.maNH FROM nhanvien nvHT where nvHT.maNV = ?)" +
                " AND strftime('%Y', hd.thoiGianDat) LIKE ?" +
                " AND hd.trangThai = 3" +
                " GROUP BY m.tenMon " +
                " ORDER BY doanhThumon DESC " +
                " LIMIT 10;";
        Cursor cursor = db.rawQuery(sql,new String[]{String.valueOf(maNV),nam});
        while (cursor.moveToNext()) {
            ThongTinMon ttMon = new ThongTinMon();
            ttMon.setHinhMon(cursor.getString(cursor.getColumnIndex("hinh")));
            ttMon.setTenMonThongKe(cursor.getString(cursor.getColumnIndex("tenMon")));
            ttMon.setSoLuongMon(cursor.getInt(cursor.getColumnIndex("soluongmon")));
            ttMon.setDoanhThuMon(cursor.getInt(cursor.getColumnIndex("doanhThumon")));
            list.add(ttMon);
        }
        cursor.close();
        return list;
    }

    @SuppressLint("Range")
    public List<ThongTinMon> getTop10MonDoanhThuCaoNhatTrongNam(String nam,int maNV) {
        List<ThongTinMon> list = new ArrayList<>();
        String sql = "SELECT m.hinh, m.tenMon, SUM(dm.soLuong) AS soluongmon, SUM(dm.soLuong * m.gia) AS doanhThumon" +
                " FROM mon m" +
                " INNER JOIN datmon dm ON m.maMon = dm.maMon" +
                " INNER JOIN hoadon hd ON dm.maHD = hd.maHD" +
                " JOIN nhanvien nv ON nv.maNV = hd.maNV " +
                " WHERE nv.maNH = (SELECT nvHT.maNH FROM nhanvien nvHT where nvHT.maNV = ?)" +
                " AND strftime('%Y', hd.thoiGianDat) LIKE ?" +
                " AND hd.trangThai = 3" +
                " GROUP BY m.tenMon " +
                " ORDER BY doanhThumon DESC " +
                " LIMIT 10;";
        Cursor cursor = db.rawQuery(sql, new String[]{ String.valueOf(maNV),nam});
        while (cursor.moveToNext()) {
            ThongTinMon ttMon = new ThongTinMon();
            ttMon.setHinhMon(cursor.getString(cursor.getColumnIndex("hinh")));
            ttMon.setTenMonThongKe(cursor.getString(cursor.getColumnIndex("tenMon")));
            ttMon.setSoLuongMon(cursor.getInt(cursor.getColumnIndex("soluongmon")));
            ttMon.setDoanhThuMon(cursor.getInt(cursor.getColumnIndex("doanhThumon")));
            list.add(ttMon);
        }
        cursor.close();
        return list;
    }


    @SuppressLint("Range")
    public List<ThongTinMon> getTop10MonSoLuongCaoNhatTrongNam(String nam, int maNV) {
        List<ThongTinMon> list = new ArrayList<>();
        String sql = "SELECT m.hinh, m.tenMon, SUM(dm.soLuong) AS soluongmon, SUM(dm.soLuong * m.gia) AS doanhThumon" +
                " FROM mon m" +
                " INNER JOIN datmon dm ON m.maMon = dm.maMon" +
                " INNER JOIN hoadon hd ON dm.maHD = hd.maHD" +
                " JOIN nhanvien nv ON nv.maNV = hd.maNV " +
                " WHERE nv.maNH = (SELECT nvHT.maNH FROM nhanvien nvHT where nvHT.maNV = ?)" +
                " AND strftime('%Y', hd.thoiGianDat) LIKE ?" +
                " AND hd.trangThai = 3" +
                " GROUP BY m.tenMon " +
                " ORDER BY soluongmon DESC " +
                " LIMIT 10;";
        Cursor cursor = db.rawQuery(sql, new String[]{ String.valueOf(maNV),nam});
        while (cursor.moveToNext()) {
            ThongTinMon ttMon = new ThongTinMon();
            ttMon.setHinhMon(cursor.getString(cursor.getColumnIndex("hinh")));
            ttMon.setTenMonThongKe(cursor.getString(cursor.getColumnIndex("tenMon")));
            ttMon.setSoLuongMon(cursor.getInt(cursor.getColumnIndex("soluongmon")));
            ttMon.setDoanhThuMon(cursor.getInt(cursor.getColumnIndex("doanhThumon")));
            list.add(ttMon);
        }
        cursor.close();
        return list;
    }


    @SuppressLint("Range")
    public List<ThongTinMon> getTop10MonSoLuongCaoNhatTrongThangNam( String thang,String nam, int maNV) {
        List<ThongTinMon> list = new ArrayList<>();
        String sql = "SELECT m.hinh, m.tenMon, SUM(dm.soLuong) AS soluongmon, SUM(dm.soLuong * m.gia) AS doanhThumon" +
                " FROM mon m" +
                " INNER JOIN datmon dm ON m.maMon = dm.maMon" +
                " INNER JOIN hoadon hd ON dm.maHD = hd.maHD" +
                " JOIN nhanvien nv ON nv.maNV = hd.maNV " +
                " WHERE nv.maNH = (SELECT nvHT.maNH FROM nhanvien nvHT where nvHt.maNV = ?)" +
                " AND strftime('%m', hd.thoiGianDat) LIKE ?" +
                " AND strftime('%Y', hd.thoiGianDat) LIKE ?" +
                " AND hd.trangThai = 3" +
                " GROUP BY m.tenMon " +
                " ORDER BY soluongmon DESC " +
                " LIMIT 10;";
        Cursor cursor = db.rawQuery(sql, new String[]{ String.valueOf(maNV),thang,nam});
        while (cursor.moveToNext()) {
            ThongTinMon ttMon = new ThongTinMon();
            ttMon.setHinhMon(cursor.getString(cursor.getColumnIndex("hinh")));
            ttMon.setTenMonThongKe(cursor.getString(cursor.getColumnIndex("tenMon")));
            ttMon.setSoLuongMon(cursor.getInt(cursor.getColumnIndex("soluongmon")));
            ttMon.setDoanhThuMon(cursor.getInt(cursor.getColumnIndex("doanhThumon")));
            list.add(ttMon);
        }
        cursor.close();
        return list;
    }

    @SuppressLint("Range")
    public List<ThongTinMon> getTop10MonDoanhThuCaoNhatTrongThangNam( String thang,String nam,int maNV) {
        List<ThongTinMon> list = new ArrayList<>();
        String sql = "SELECT m.hinh, m.tenMon, SUM(dm.soLuong) AS soluongmon, SUM(dm.soLuong * m.gia) AS doanhThumon" +
                " FROM mon m" +
                " INNER JOIN datmon dm ON m.maMon = dm.maMon" +
                " INNER JOIN hoadon hd ON dm.maHD = hd.maHD" +
                " JOIN nhanvien nv ON nv.maNV = hd.maNV " +
                " WHERE nv.maNH = (SELECT nvHT.maNH FROM nhanvien nvHT where nvHT.maNV = ?)" +
                " AND strftime('%m', hd.thoiGianDat) LIKE ?" +
                " AND strftime('%Y', hd.thoiGianDat) LIKE ?" +
                " AND hd.trangThai = 3" +
                " GROUP BY m.tenMon " +
                " ORDER BY doanhThumon DESC " +
                " LIMIT 10;";
        Cursor cursor = db.rawQuery(sql, new String[]{ String.valueOf(maNV),thang,nam});
        while (cursor.moveToNext()) {
            ThongTinMon ttMon = new ThongTinMon();
            ttMon.setHinhMon(cursor.getString(cursor.getColumnIndex("hinh")));
            ttMon.setTenMonThongKe(cursor.getString(cursor.getColumnIndex("tenMon")));
            ttMon.setSoLuongMon(cursor.getInt(cursor.getColumnIndex("soluongmon")));
            ttMon.setDoanhThuMon(cursor.getInt(cursor.getColumnIndex("doanhThumon")));
            list.add(ttMon);
        }
        cursor.close();
        return list;
    }
}

