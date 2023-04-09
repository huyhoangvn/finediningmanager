package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.Mon;
import sp23cp18103.nhom2.finedining.model.ThongTinHoaDon;
import sp23cp18103.nhom2.finedining.model.ThongTinThongKeDoanhThu;
import sp23cp18103.nhom2.finedining.utils.DateHelper;

public class ThongTinHoaDonDAO {
    private SQLiteDatabase db;
    Context context;

    public ThongTinHoaDonDAO(Context context){
        DBHelper dbHelper =new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        this.context = context;
    }


    public List<ThongTinHoaDon> getTrangThaiHoaDon(int maNV, String tenKH, int trangThai,String ngay,String gio){
        String sql = "SELECT hd.maHD,nv.tenNV,kh.tenKH,hd.soLuongKhach,hd.thoiGianXuat,hd.thoiGianDat,hd.trangThai " +
                "FROM hoadon as hd " +
                "JOIN khachhang as kh ON hd.maKH = kh.maKH " +
                "JOIN nhanvien as nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = (SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ?) " +
                " AND hd.trangThai = ? " +
                " AND kh.tenKH LIKE ? " +
                "AND strftime('%Y-%m-%d',hd.thoiGianDat) LIKE ? " +
                "AND strftime('%H:%M',hd.thoiGianDat) LIKE ? "+
                " ORDER BY hd.thoiGianDat DESC ";
        String tim = "%" + tenKH + "%";
        String ngayTim = "%" + ngay + "%";
        String gioTim = "%" + gio + "%";
        return getDaTa(sql, String.valueOf(maNV),String.valueOf(trangThai),tim,ngayTim,gioTim);

    }
    public List<ThongTinHoaDon> getTatCa(int maNV, String tenKH,String ngay,String gio){
        String sql = "SELECT hd.maHD,nv.tenNV,kh.tenKH,hd.soLuongKhach,hd.thoiGianXuat,hd.thoiGianDat,hd.trangThai " +
                "FROM hoadon as hd " +
                "JOIN khachhang as kh ON hd.maKH = kh.maKH " +
                "JOIN nhanvien as nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = (SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ?) " +
                " AND kh.tenKH LIKE ? " +
                " AND hd.trangThai <> 0 " +
                "AND strftime('%Y-%m-%d',hd.thoiGianDat) LIKE ? " +
                "AND strftime('%H:%M',hd.thoiGianDat) LIKE ? "+
                " ORDER BY  hd.trangThai ASC , hd.thoiGianDat DESC ";
        String tim = "%" + tenKH + "%";
        String ngayTim = "%" + ngay + "%";
        String gioTim = "%" + gio + "%";
        return getDaTa(sql, String.valueOf(maNV),tim,ngayTim,gioTim);

    }
    public List<ThongTinHoaDon> getTrangThai(int maNV, int trangThai){
        String sql = "SELECT hd.maHD,nv.tenNV,kh.tenKH,hd.soLuongKhach,hd.thoiGianXuat,hd.thoiGianDat,hd.trangThai " +
                "FROM hoadon as hd " +
                "JOIN khachhang as kh ON hd.maKH = kh.maKH " +
                "JOIN nhanvien as nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = (SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ?) " +
                " AND hd.trangThai = ? " +
                " ORDER BY hd.trangThai DESC";

        return getDaTa(sql, String.valueOf(maNV),String.valueOf(trangThai));

    }
    @SuppressLint("Range")
    public long getDoanhThu(int maNV , String tuNgay , String denNgay){
        String sql = "SELECT sum(m.gia * dm.soLuong) as thanhTien  " +
                "FROM hoadon as hd  " +
                "JOIN datmon as dm ON dm.maHD = hd.maHD " +
                "JOIN mon as m ON m.maMon = dm.maMon " +
                "JOIN nhanvien as nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = (SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ?) " +
                "AND hd.trangThai = 3 " +
                "AND dm.trangThai = 1 " +
                "AND (strftime('%Y-%m-%d',hd.thoiGianDat) BETWEEN ? AND ?) ";

        List<Long> list = new ArrayList<Long>();
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(maNV),tuNgay,denNgay});
        while (c.moveToNext()){
            try {
                list.add(Long.parseLong(c.getString(c.getColumnIndex("thanhTien"))));

            }catch (Exception e){
                list.add(Long.parseLong("0"));
            }
        }
        return list.get(0);
    }
    @SuppressLint("Range")
    public List<ThongTinThongKeDoanhThu> getDoanhThuTheoNam(int maNV, String nam){
        String sql = "SELECT strftime('%m', hd.thoiGianDat) as thang , sum(m. gia * dm.soLuong) as thanhTien " +
                "FROM hoadon hd " +
                "JOIN datmon as dm ON dm.maHD = hd.maHD " +
                "JOIN mon as m ON m.maMon = dm.maMon " +
                "JOIN nhanvien as nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = (SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND hd.trangThai = 3 " +
                "AND dm.trangThai = 1 " +
                "AND strftime('%Y', hd.thoiGianDat) LIKE ?" +
                "GROUP BY strftime('%m', hd.thoiGianDat)";
        List<ThongTinThongKeDoanhThu> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(maNV),nam});
        while (c.moveToNext()){
            ThongTinThongKeDoanhThu tttkdt = new ThongTinThongKeDoanhThu();
            tttkdt.setMonth(c.getString(c.getColumnIndex("thang")));
            tttkdt.setDoanhThu(c.getLong(c.getColumnIndex("thanhTien")));
            list.add(tttkdt);
        }
        return list;
    }

    @SuppressLint("Range")
    public String getNgayNhoNhat(int maNV){
        String sql = "SELECT strftime('%Y-%m-%d',hd.thoiGianDat) as ngayNhoNhat " +
                    "FROM hoadon hd " +
                    "JOIN datmon as dm ON dm.maHD = hd.maHD " +
                    "JOIN nhanvien as nv ON nv.maNV = hd.maNV " +
                    "WHERE nv.maNH = (SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                    "AND hd.trangThai = 3 " +
                    "AND dm.trangThai = 1 " +
                    "ORDER BY ngayNhoNhat ASC LIMIT 1 ";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(maNV)});
        if (c.moveToNext()){
            return DateHelper.getDateVietnam(c.getString(c.getColumnIndex("ngayNhoNhat")));
        }
        return DateHelper.getDateVietnamNow();

    }
    @SuppressLint("Range")
    public String getNgayLonNhat(int maNV){
        String sql = "SELECT strftime('%Y-%m-%d',hd.thoiGianDat) as ngayLonNhat " +
                "FROM hoadon hd " +
                "JOIN datmon as dm ON dm.maHD = hd.maHD " +
                "JOIN nhanvien as nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = (SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND hd.trangThai = 3 " +
                "AND dm.trangThai = 1 " +
                "ORDER BY ngayLonNhat DESC LIMIT 1 ";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(maNV)});
       if (c.moveToNext()){
           return DateHelper.getDateVietnam(c.getString(c.getColumnIndex("ngayLonNhat")));
        }
        return DateHelper.getDateVietnamNow();

    }






    @SuppressLint("Range")
    public List<ThongTinHoaDon> getDaTa(String sql, String...SelectArgs){
        List<ThongTinHoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while (c.moveToNext()){
            ThongTinHoaDon tthd =new ThongTinHoaDon();
            tthd.setMaHD(c.getInt(c.getColumnIndex("maHD")));
            tthd.setTenNhanVien(c.getString(c.getColumnIndex("tenNV")));
            tthd.setTenKhachHang(c.getString(c.getColumnIndex("tenKH")));
            tthd.setSoLuongKhachHang(c.getInt(c.getColumnIndex("soLuongKhach")));
            tthd.setThoiGianXuat(c.getString(c.getColumnIndex("thoiGianXuat")));
            tthd.setThoiGianDat(c.getString(c.getColumnIndex("thoiGianDat")));
            tthd.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
            list.add(tthd);
        }
        return list;
    }
}
