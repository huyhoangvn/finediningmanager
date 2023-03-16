package sp23cp18103.nhom2.finedining.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import sp23cp18103.nhom2.finedining.model.NhaHang;
import sp23cp18103.nhom2.finedining.model.NhanVien;

/*
 * Tạo bảng nhân viên
 * nhanvien ( maNV, maNH, tenNV, gioiTinh, ngaySinh, sdt, phanQuyen, trangThai, hinh, taiKhoan, matKhau )
 * */
public class NhanVienDAO {
    private SQLiteDatabase db;

    public NhanVienDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNhaHang(NhanVien nhanVien){
        ContentValues values = new ContentValues();
        values.put("maNV",nhanVien.getMaNV());
        values.put("maNH",nhanVien.getMaNH());
        values.put("tenNV", nhanVien.getTenNV());
        values.put("gioiTinh", nhanVien.getGioiTinh());
        values.put("ngaySinh", nhanVien.getNgaySinh());
        values.put("sdt", nhanVien.getSdt());
        values.put("trangThai", nhanVien.getTrangThai());
        values.put("taiKhoan", nhanVien.getTaiKhoan());
        values.put("matKhau", nhanVien.getMatKhau());
        values.put("hinh", nhanVien.getHinh());
        return db.insert("nhanvien",null,values);
    }
}
