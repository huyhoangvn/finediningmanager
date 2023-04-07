package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.KhachHang;
import sp23cp18103.nhom2.finedining.model.ThongTinThongKeDoanhThu;
import sp23cp18103.nhom2.finedining.model.ThongTinThongKeKhachHang;

public class KhachDAO {
    SQLiteDatabase db;

    public KhachDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(KhachHang obj){
        ContentValues values=new ContentValues();
        values.put("tenKH",obj.getTenKH());
        return db.insert("khachhang",null,values);
    }
    public int updateKhachHang(KhachHang obj){
        ContentValues values=new ContentValues();
        values.put("tenKH",obj.getTenKH());
        return db.update("khachhang",values,"maKH=?",new String[]{String.valueOf(obj.getMaKH())});

    }
    @SuppressLint("Range")
    public int getMaKhanhHangTiepTheo(){
        String sql = " SELECT seq FROM sqlite_sequence WHERE name LIKE 'khachhang' ";
        @SuppressLint("Recycle") Cursor c = db.rawQuery(sql, null);
        if(c.moveToNext()){
            return c.getInt(c.getColumnIndex("seq")) + 1;
        }
        return -1;
    }

    @SuppressLint("Range")
    public String getTenKhach(int maHD) {
        String sql = "SELECT khachhang.tenKH FROM khachhang JOIN hoadon on hoadon.maKH = khachhang.maKH WHERE hoadon.maHD = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maHD)});
        if (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex("tenKH"));
        }
        return "";
    }

    @SuppressLint("Range")
    public int getSoLuongKhachHang (int maNV, String tungay, String denngay){
        List<Integer> list=new ArrayList<>();
        String sql = "SELECT SUM(hd.soLuongKhach) as tongKhachHang FROM hoadon hd  " +
                "JOIN nhanvien nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = (SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ?) " +
                "AND hd.trangThai = 3 " +
                "AND (strftime('%Y-%m-%d',hd.thoiGianDat) BETWEEN ? AND ?) ";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(maNV),tungay,denngay});
        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("tongKhachHang"))));

            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
    @SuppressLint("Range")
    public List<ThongTinThongKeKhachHang> getsoLuongTheoNam(int maNV, String nam){
        String sql = "SELECT strftime('%m', hd.thoiGianDat) as thang , sum(hd.soLuongKhach) as tongKhachHang " +
                "FROM hoadon hd " +
                "JOIN nhanvien as nv ON nv.maNV = hd.maNV " +
                "WHERE nv.maNH = (SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND hd.trangThai = 3 " +
                "AND strftime('%Y', hd.thoiGianDat) LIKE ?" +
                "GROUP BY strftime('%m', hd.thoiGianDat)";
        List<ThongTinThongKeKhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(maNV),nam});
        while (c.moveToNext()){
            ThongTinThongKeKhachHang tttkdt = new ThongTinThongKeKhachHang();
            tttkdt.setMonth(c.getString(c.getColumnIndex("thang")));
            tttkdt.setSoLuong(c.getInt(c.getColumnIndex("tongKhachHang")));
            list.add(tttkdt);
        }
        return list;
    }

    @SuppressLint("Range")
    public List<KhachHang> getDaTa(String sql, String... selectAvg) {
        List<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectAvg);
        while (c.moveToNext()) {
            KhachHang obj = new KhachHang();
            obj.setMaKH(Integer.parseInt(c.getString(c.getColumnIndex("maKH"))));
            obj.setTenKH(c.getString(c.getColumnIndex("tenKH")));
            obj.setGioiTinh(Integer.parseInt(c.getString(c.getColumnIndex("gioiTinh"))));
            obj.setSdt(c.getString(c.getColumnIndex("sdt")));
            obj.setTaiKhoan(c.getString(c.getColumnIndex("taiKhoan")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            obj.setHinh(c.getString(c.getColumnIndex("hinh")));
            list.add(obj);
        }
        return list;
    }
}
