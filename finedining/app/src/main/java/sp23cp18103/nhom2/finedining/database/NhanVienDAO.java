package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.NhanVien;
public class NhanVienDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public NhanVienDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<NhanVien> getDataNhanVien(String sql, String...Args){
        List<NhanVien> listNhanVien = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,Args);
        while (cursor.moveToNext()){
            NhanVien nv = new NhanVien();
            nv.setMaNV(cursor.getInt(cursor.getColumnIndex("maNV")));
            nv.setMaNH(cursor.getInt(cursor.getColumnIndex("maNH")));
            nv.setTenNV(cursor.getString(cursor.getColumnIndex("tenNV")));
            nv.setGioiTinh(cursor.getInt(cursor.getColumnIndex("gioiTinh")));
            nv.setNgaySinh(cursor.getString(cursor.getColumnIndex("ngaySinh")));
            nv.setSdt(cursor.getString(cursor.getColumnIndex("sdt")));
            nv.setPhanQuyen(cursor.getInt(cursor.getColumnIndex("phanQuyen")));
            nv.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));
            nv.setTaiKhoan(cursor.getString(cursor.getColumnIndex("taikhoan")));
            nv.setMatKhau(cursor.getString(cursor.getColumnIndex("matkhau")));
            listNhanVien.add(nv);
        }
        return listNhanVien;
    }

    public List<NhanVien> getallNhanVien(){
        String sql = "select * from nhanvien";
        return getDataNhanVien(sql);
    }

    public long insertNhanVien(NhanVien nv){
        ContentValues values = new ContentValues();
        values.put("maNH",nv.getMaNH());
        values.put("tenNV",nv.getTenNV());
        values.put("gioiTinh",nv.getGioiTinh());
        values.put("ngaySinh",nv.getNgaySinh());
        values.put("sdt",nv.getSdt());
        values.put("phanQuyen",nv.getPhanQuyen());
        values.put("trangThai",nv.getTrangThai());
        values.put("taikhoan",nv.getTaiKhoan());
        values.put("matkhau",nv.getMatKhau());
        return db.insert("nhanvien",null,values);
    }

    public long updateNhanVien(NhanVien nv){
        ContentValues values = new ContentValues();
        values.put("maNH",nv.getMaNH());
        values.put("tenNV",nv.getTenNV());
        values.put("gioiTinh",nv.getGioiTinh());
        values.put("ngaySinh",nv.getNgaySinh());
        values.put("sdt",nv.getSdt());
        values.put("phanQuyen",nv.getPhanQuyen());
        values.put("trangThai",nv.getTrangThai());
        return db.update("nhanvien",values,"maNV = ?", new String[]{String.valueOf(nv.getMaNV())});
    }


    public long updateMatKhauNhanvien(NhanVien nv, String matKhauCu){
        ContentValues values = new ContentValues();
        values.put("matkhau", nv.getMatKhau());

        String whereClause = "maNV = ? AND matkhau = ?";
        String[] whereArgs = {String.valueOf(nv.getMaNV()), matKhauCu};

        return db.update("nhanvien", values, whereClause, whereArgs);
    }

    public boolean checkDangnhap(String taikhoan, String matkhau){
        String sql = String.format("select * from nhanvien where taikhoan = '%s' and matkhau = '%s' ",taikhoan,matkhau);
        Cursor cursor = db.rawQuery(sql,null);
        return cursor.getCount() > 0;
    }


    public boolean checkTaikhoan(String taikhoan){
        String sql = String.format("select * from nhanvien where taikhoan = '%s' ",taikhoan);
        Cursor cursor = db.rawQuery(sql,null);
        return cursor.getCount() > 0;
    }







}