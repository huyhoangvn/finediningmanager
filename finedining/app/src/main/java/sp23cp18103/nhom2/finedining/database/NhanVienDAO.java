package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.NhanVien;

/*
 * Bảng nhân viên
 * nhanvien ( maNV, maNH, tenNV, gioiTinh, ngaySinh, sdt, phanQuyen, trangThai, hinh, taiKhoan, matKhau )
 * */
public class NhanVienDAO {
    private final SQLiteDatabase db;
    private final DBHelper dbHelper;

    public NhanVienDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    /*
     * Thêm mới nhân viên với mã nhân viên tự động tăng từ 1
     * */
    public long insertNhanVien(NhanVien nv) {
        ContentValues values = new ContentValues();
        values.put("maNH", nv.getMaNH());
        values.put("tenNV", nv.getTenNV());
        values.put("gioiTinh", nv.getGioiTinh());
        values.put("ngaySinh", nv.getNgaySinh());
        values.put("sdt", nv.getSdt());
        values.put("phanQuyen", nv.getPhanQuyen());
        values.put("trangThai", nv.getTrangThai());
        values.put("hinh", nv.getHinh());
        values.put("taiKhoan", nv.getTaiKhoan());
        values.put("matKhau", nv.getMatKhau());
        return db.insert("nhanvien", null, values);
    }

    /*
     * Cập nhật thông tin công khai của nhân viên
     * Không cập nhật mã nhân viên
     * Không cập nhật tài khoản và mật khẩu
     * */
    public long updateNhanVien(NhanVien nv) {
        ContentValues values = new ContentValues();
        values.put("maNH", nv.getMaNH());
        values.put("tenNV", nv.getTenNV());
        values.put("gioiTinh", nv.getGioiTinh());
        values.put("ngaySinh", nv.getNgaySinh());
        values.put("sdt", nv.getSdt());
        values.put("phanQuyen", nv.getPhanQuyen());
        values.put("trangThai", nv.getTrangThai());
        values.put("hinh", nv.getHinh());
        return db.update("nhanvien", values, "maNV = ?", new String[]{String.valueOf(nv.getMaNV())});
    }

    /*
     * Đổi mật khẩu nhân viên
     * Nếu người dùng có mã nhân viên và mật khẩu cũ đúng
     * Đổi sang mật khẩu mới
     * */
    public long updateMatKhauNhanvien(String maNV, String matKhauCu, String matKhauMoi) {
        ContentValues values = new ContentValues();
        values.put("matKhau", matKhauMoi);
        return db.update("nhanvien", values, "maNV = ? AND matKhau LIKE ?"
                , new String[]{maNV, matKhauCu});
    }

    /*
     * Cho biết nếu tên tài khoản đã tồn tại hay chưa
     * Nếu đã tồn tại thì trả về true và báo lỗi sai tên tài khoản
     * Nếu chưa tồn tại thì trả về false và chuyển qua phần kiểm tra đăng nhập
     * */
    public boolean checkTaiKhoan(String taiKhoan) {
        String sql = "SELECT * FROM nhanvien WHERE taiKhoan LIKE ? ";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{taiKhoan});
        return cursor.getCount() > 0;
    }

    /*
     * Cho biết nếu tài khoản và mật khẩu có trùng không
     * Nếu trùng thì trả về true và lấy mã nhân viên đăng nhập thành công
     * Nếu không trùng thì trả về false và báo lỗi sai mật khẩu
     * */
    public boolean checkDangNhap(String taiKhoan, String matKhau) {
        String sql = "SELECT * FROM nhanvien WHERE taiKhoan LIKE ? AND matKhau LIKE ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{taiKhoan, matKhau});
        return cursor.getCount() > 0;
    }

    /*
     * Đăng nhập nếu tài khoản và mật khẩu có trùng không
     * Nếu trùng thì trả về mã nhân viên đăng nhập thành công lưu vào shared preferences
     * Nếu không trùng thì trả về -1
     * */
    @SuppressLint("Range")
    public int getIdNhanVienByTaiKhoan(String taiKhoan, String matKhau) {
        String sql = "SELECT maNV FROM nhanvien WHERE taiKhoan = ? AND matKhau = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{taiKhoan, matKhau});
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("maNV"));
        }
        return -1;
    }

    /*
     * Tìm tên nhân viên có mã người dùng hiện tại
     * Nếu có trả về tên nhân viên
     * Nếu không trả về chuỗi rỗng
     * */
    @SuppressLint("Range")
    public String getTenNV(int maNV) {
        String sql = "SELECT tenNV FROM nhanvien WHERE maNV = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maNV)});
        if (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex("tenNV"));
        }
        return "";
    }

    /*
     * Cho biết phân quyền nhân viên có mã người dùng hiện tại
     * Nếu có trả về phân quyền
     * Nếu không trả về -1
     * */
    @SuppressLint("Range")
    public int getPhanQuyen(int maNV) {
        String sql = "SELECT phanQuyen FROM nhanvien WHERE maNV = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maNV)});
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("phanQuyen"));
        }
        return -1;
    }

    /*
     * Lấy tất cả thông tin công khai của nhân viên
     * Không bao gồm mã nhân viên
     * Không bao gồm tài khoản và mật khẩu
     * */
    @SuppressLint("Range")
    public List<NhanVien> getThongTin(String sql, String... Args) {
        List<NhanVien> listNhanVien = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, Args);
        while (cursor.moveToNext()) {
            NhanVien nv = new NhanVien();
            nv.setMaNV(cursor.getInt(cursor.getColumnIndex("maNV")));
            nv.setMaNH(cursor.getInt(cursor.getColumnIndex("maNH")));
            nv.setTenNV(cursor.getString(cursor.getColumnIndex("tenNV")));
            nv.setGioiTinh(cursor.getInt(cursor.getColumnIndex("gioiTinh")));
            nv.setNgaySinh(cursor.getString(cursor.getColumnIndex("ngaySinh")));
            nv.setSdt(cursor.getString(cursor.getColumnIndex("sdt")));
            nv.setPhanQuyen(cursor.getInt(cursor.getColumnIndex("phanQuyen")));
            nv.setTrangThai(cursor.getInt(cursor.getColumnIndex("trangThai")));
            nv.setHinh(cursor.getString(cursor.getColumnIndex("hinh")));
            listNhanVien.add(nv);
        }
        return listNhanVien;
    }

    /*
     * Lấy tất cả thông tin công khai của nhân viên làm việc ở nhà hàng của nhân viên hiện tại
     * Trả về mảng các nhân viên có cùng mã nhà hàng với nhân viên hiện tại
     * và hiển thị những nhân viên đã nghỉ nếu trạng thái là 0
     * */
    public List<NhanVien> getAllNhanVien(int maNV, int trangThai) {
        String sql = "SELECT * FROM nhanvien nv " +
                "WHERE nv.maNH = ( SELECT maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND nv.trangThai >= ? ";
        return getThongTin(sql, String.valueOf(maNV), String.valueOf(trangThai));
    }

    /*
     * Lấy tất cả thông tin công khai của nhân viên hiện tại
     * Trả về thông tin công khai của nhân viên
     * Trả về null nếu không có thông tin
     * */
    public NhanVien getNhanVien(int maNV) {
        String sql = "SELECT * FROM nhanvien nv " +
                "WHERE nv.maNH = ? ";
        ArrayList<NhanVien> list = (ArrayList<NhanVien>) getThongTin(sql, String.valueOf(maNV));
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /*
     * Lấy thông tin công khai của nhân viên làm việc ở nhà hàng của nhân viên hiện tại
     * với tên nhân viên đó trùng với chuỗi tìm kiếm
     * và hiển thị những nhân viên đã nghỉ nếu trạng thái là 0
     * */
    public List<NhanVien> searchNhanVien(int maNV, String tenNV, int trangThai) {
        String sql = "SELECT * FROM nhanvien nv " +
                "WHERE nv.maNH = ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND nv.tenNV LIKE ? " +
                "AND LENGTH(nv.tenNV) >= LENGTH( ? ) " +
                "AND nv.trangThai >= ? ";
        return getThongTin(sql, String.valueOf(maNV), String.valueOf(tenNV + "%"),
                String.valueOf(tenNV), String.valueOf(trangThai));
    }
}
