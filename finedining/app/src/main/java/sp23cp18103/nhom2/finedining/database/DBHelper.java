package sp23cp18103.nhom2.finedining.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "QuanLyNhaHang";
    public static final int DB_VERSION = 3;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableNhahang(db);
        createTableNhanvien(db);
        createTableKhachhang(db);
        createTableLoaiban(db);
        createTableban(db);
        createTableLoaimon(db);
        createTableMon(db);
        createTableDatban(db);
        createTableDatmon(db);
        createTableHoadon(db);
        //Insert dữ liệu mẫu của nhà hàng và nhân viên
        insertNhaHang(db);
        insertNhanVienQuanLy(db);
    }

    private void insertNhaHang(SQLiteDatabase db) {
        String sqlInsertNhaHang = "INSERT INTO nhahang VALUES " +
                "( 1, 'Ratatouille', 'Hà Nội', 'https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/nhahang1.jpg?alt=media&token=137f614a-6bcb-449c-a2c5-0018f5dd79d1'), " +
                "( 2, 'Golden Ramsey', 'TP Hồ Chí Minh', 'https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/nhahang2.jpg?alt=media&token=06dd917b-5504-415d-9834-f9c5974eb10c')";
        db.execSQL(sqlInsertNhaHang);
    }

    private void insertNhanVienQuanLy(SQLiteDatabase db) {
        String sqlInsertNhanVienQuanLy = "INSERT INTO nhanvien VALUES " +
                "(1, 1, 'Nguyễn Huy Hoàng', 2, '2002-01-10', '0933765999', 1, 1, 'myadmin', 'admin', 'https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/anhnhanvien%20(1).jpg?alt=media&token=b6e07e36-e6ec-4ecb-8230-5d71f6cb7d05'), " +
                "(2, 1, 'Hồ Ngọc Hà', 0, '1990-02-11', '0933763999', 1, 1, 'hongocha', 'hongocha', 'https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/anhnhanvien%20(1).png?alt=media&token=0b952486-4509-49b3-a9e7-6ae91e1ad2d2'), " +
                "(3, 2, 'Đặng Hoàng Giang', 1, '1988-12-13', '0933765999', 1, 1, 'notadmin', 'admin', 'https://firebasestorage.googleapis.com/v0/b/fine-dining-66f4b.appspot.com/o/anhnhanvien%20(2).png?alt=media&token=b22c78f2-78e8-4196-ab16-c200e2e600a9') ";
        db.execSQL(sqlInsertNhanVienQuanLy);
    }

    /*
    * Tạo bảng hóa đơn
    * hoadon( maHD, maKH, maNV, soLuongKhach, thoiGianXuat, thoiGianDat, thoiGianThanhToan, trangThai )
    * */
    private void createTableHoadon(SQLiteDatabase db) {
        String sql = "CREATE TABLE hoadon(" +
                "maHD INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maKH INTEGER NOT NULL REFERENCES khachhang(maKH)," +
                "maNV INTEGER NOT NULL REFERENCES nhanvien(maNV)," +
                "soLuongKhach INTEGER NOT NULL," +
                "thoiGianXuat TEXT NOT NULL," +
                "thoiGianDat TEXT NOT NULL," +
                "trangThai INTEGER NOT NULL CHECK (trangThai >= 0 AND trangThai <= 3))";
        db.execSQL(sql);
    }

    /*
     * Tạo bảng đặt món
     * datmon( maHD, maMon, soLuong, trangThai )
     * */
    private void createTableDatmon(SQLiteDatabase db) {
        String sql = "CREATE TABLE datmon(" +
                "maHD INTEGER NOT NULL REFERENCES hoadon(maHD)," +
                "maMon INTEGER NOT NULL REFERENCES mon(maMon)," +
                "soLuong INTEGER NOT NULL," +
                "trangThai INTEGER NOT NULL CHECK (trangThai = 1 OR trangThai = 0)," +
                "PRIMARY KEY (maHD, maMon))";
        db.execSQL(sql);
    }

    /*
     * Tạo bảng đặt bàn
     * datban( maHD, maBan, trangThai )
     * */
    private void createTableDatban(SQLiteDatabase db) {
        String sql = "CREATE TABLE datban(" +
                "maHD INTEGER NOT NULL REFERENCES hoadon(maHD)," +
                "maBan INTEGER NOT NULL REFERENCES ban(maBan)," +
                "trangThai INTEGER NOT NULL CHECK (trangThai = 1 OR trangThai = 0)," +
                "PRIMARY KEY (maHD, maBan))";
        db.execSQL(sql);
    }

    /*
    * Tạo bảng món
    * mon( maMon, maLM, tenMon, trangThai, hinh )
    * */
    private void createTableMon(SQLiteDatabase db) {
        String sql = "CREATE TABLE mon(" +
                "maMon INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maLM INTEGER NOT NULL REFERENCES loaimon(maLM)," +
                "tenMon TEXT NOT NULL," +
                "gia INTEGER NOT NULL,"+
                "trangThai INTEGER NOT NULL CHECK (trangThai = 1 OR trangThai = 0)," +
                "hinh TEXT)";
        db.execSQL(sql);
    }

    /*
    * Tạo bảng loại món
    * loaimon ( maLM, maNV, tenLoai, trangThai )
    * */
    private void createTableLoaimon(SQLiteDatabase db) {
        String sql = "CREATE TABLE loaimon(" +
                "maLM INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maNV INTEGER NOT NULL REFERENCES nhanvien(maNV)," +
                "tenLoai TEXT NOT NULL," +
                "trangThai INTEGER NOT NULL CHECK (trangThai = 1 OR trangThai = 0))";
        db.execSQL(sql);
    }

    /*
    * Tạo bảng bàn
    * ban ( maBan, maLB, viTri, trangThai )
    * */
    private void createTableban(SQLiteDatabase db) {
        String sql = "CREATE TABLE ban(" +
                "maBan INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maLB INTEGER NOT NULL REFERENCES loaiban(maLB)," +
                "viTri TEXT NOT NULL," +
                "trangThai INTEGER NOT NULL CHECK (trangThai = 1 OR trangThai = 0))";
        db.execSQL(sql);
    }

    /*
     * Tạo bảng loại bàn
     * loaiban ( maLB, maVN, tenLoai, soChoNgoi, trangThai )
     * */
    private void createTableLoaiban(SQLiteDatabase db) {
        String sql = "CREATE TABLE loaiban(" +
                "maLB INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maNV INTEGER NOT NULL REFERENCES nhanvien(maNV)," +
                "tenLoai TEXT NOT NULL," +
                "trangThai INTEGER NOT NULL CHECK (trangThai = 1 OR trangThai = 0))";
        db.execSQL(sql);
    }

    /*
    * Tạo bảng khách hàng
    * khachhang ( maKH, tenKH, gioiTinh, sdt, taiKhoan, matKhau, hinh )
    * */
    private void createTableKhachhang(SQLiteDatabase db) {
        String sql = "CREATE TABLE khachhang(" +
                "maKH INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenKH TEXT NOT NULL," +
                "gioiTinh INTEGER CHECK (gioiTinh = 1 OR gioiTinh = 2 OR gioiTinh = 0)," +
                "sdt TEXT," +
                "taiKhoan TEXT UNIQUE," +
                "matKhau TEXT," +
                "hinh TEXT)";
        db.execSQL(sql);
    }

    /*
    * Tạo bảng nhân viên
    * nhanvien ( maNV, maNH, tenNV, gioiTinh, ngaySinh, sdt, phanQuyen, trangThai, hinh, taiKhoan, matKhau )
    * */
    private void createTableNhanvien(SQLiteDatabase db) {
        String sql = "CREATE TABLE nhanvien(" +
                "maNV INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maNH INTEGER NOT NULL REFERENCES nhahang(maNH)," +
                "tenNV TEXT NOT NULL," +
                "gioiTinh INTEGER CHECK (gioiTinh = 1 OR gioiTinh = 2 OR gioiTinh = 0)," +
                "ngaySinh TEXT NOT NULL," +
                "sdt TEXT NOT NULL," +
                "phanQuyen INTEGER NOT NULL CHECK (phanQuyen = 1 OR phanQuyen = 0)," +
                "trangThai INTEGER NOT NULL CHECK (trangThai = 1 OR trangThai = 0)," +
                "taiKhoan TEXT NOT NULL UNIQUE," +
                "matKhau TEXT NOT NULL, " +
                "hinh TEXT" +
                ")";
        db.execSQL(sql);
    }

    /*
    * Tạo bảng nhà hàng
    * nhahang ( maNH, tenNH, diaChi, hinh )
    * */
    private void createTableNhahang(SQLiteDatabase db) {
        String sql = "CREATE TABLE nhahang(" +
                "maNH INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenNH TEXT NOT NULL," +
                "diaChi TEXT NOT NULL," +
                "hinh TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTableDatmon = "DROP TABLE IF EXISTS datmon";
        String dropTableDatban = "DROP TABLE IF EXISTS datban";
        String dropTableHoadon = "DROP TABLE IF EXISTS hoadon";
        String dropTableKhachhang = "DROP TABLE IF EXISTS khachhang";
        String dropTableMon = "DROP TABLE IF EXISTS mon";
        String dropTableLoaimon = "DROP TABLE IF EXISTS loaimon";
        String dropTableBan = "DROP TABLE IF EXISTS ban";
        String dropTableLoaiban = "DROP TABLE IF EXISTS loaiban";
        String dropTableNhanvien = "DROP TABLE IF EXISTS nhanvien";
        String dropTableNhahang = "DROP TABLE IF EXISTS nhahang";
        //Drop theo thứ tự để không bị ràng buộc khóa ngoại
        db.execSQL(dropTableDatmon);
        db.execSQL(dropTableDatban);
        db.execSQL(dropTableHoadon);
        db.execSQL(dropTableKhachhang);
        db.execSQL(dropTableMon);
        db.execSQL(dropTableLoaimon);
        db.execSQL(dropTableBan);
        db.execSQL(dropTableLoaiban);
        db.execSQL(dropTableNhanvien);
        db.execSQL(dropTableNhahang);
        onCreate(db);//update
    }
}