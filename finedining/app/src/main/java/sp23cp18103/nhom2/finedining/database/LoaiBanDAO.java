package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.LoaiBan;

public class LoaiBanDAO {
    SQLiteDatabase db;

    public LoaiBanDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertloaiban(LoaiBan obj) {
        ContentValues values = new ContentValues();
        values.put("maNV", obj.getMaNV());
        values.put("tenLoai", obj.getTenLoai());
        values.put("trangThai", obj.getTrangThai());
        return db.insert("loaiban", null, values);
    }

    public int updateloaiban(LoaiBan obj) {
        ContentValues values = new ContentValues();
        values.put("maNV", obj.getMaNV());
        values.put("tenLoai", obj.getTenLoai());
        values.put("trangThai", obj.getTrangThai());
        return db.update("loaiban", values, "maLB=?", new String[]{String.valueOf(obj.getMaLB())});
    }

    public List<LoaiBan> getAllLoaiBan() {
        String sql = "SELECT * FROM loaiban";
        return getDaTa(sql);
    }

    @SuppressLint("Range")
    public List<LoaiBan> getDaTa(String sql, String... selectAvg) {
        List<LoaiBan> list = new ArrayList<>();
        @SuppressLint("Recycle")
        Cursor c = db.rawQuery(sql, selectAvg);
        while (c.moveToNext()) {
            LoaiBan obj = new LoaiBan();
            obj.setMaLB(c.getInt(c.getColumnIndex("maLB")));
            obj.setMaNV((c.getInt(c.getColumnIndex("maNV"))));
            obj.setTenLoai(c.getString(c.getColumnIndex("tenLoai")));
            obj.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
            list.add(obj);
        }
        return list;
    }
    public LoaiBan getID(int maLB){
        String sql="select*from loaiban where maLB=?";
        List<LoaiBan> list = getDaTa(sql, String.valueOf(maLB));
        return list.get(0);
    }


    // tìm kiếm tương đối theo nhân viên và tên loại bàn
    public List<LoaiBan> getTimKiem(int maNV, String timKiem ,String trangThai  ) {
        String sql = "Select  lb.maLB,lb.maNV,lb.tenLoai,lb.trangThai from loaiban lb " +
                "JOIN nhanvien nv ON lb.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND lb.tenLoai LIKE ? " +
                "AND lb.trangThai >= ?";
        return getDaTa(sql, String.valueOf(maNV),String.valueOf("%" + timKiem + "%"), String.valueOf(trangThai ));
    }
    public int getlienKetTrangThai(int maLB, int maNV) {
        String sql = "Select b.maBan from ban b " +
                "JOIN loaiban lb ON b.maLB = lb.maLB " +
                "JOIN nhanvien nv ON lb.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND b.trangThai = 1 " +
                "AND b.maLB = ? " ;
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(maNV), String.valueOf(maLB)});
        return c.getCount();
    }

    /*
    * Cái này trả về số lượng bàn đây muốn tìm số bàn trống thì lấy tổng số bàn trừ đi
    * */
   @SuppressLint("Range")
   public int getSoLuongBan(int maLB, int maNV){
        String sql="select count(b.maBan) AS DemSoLuong from ban b  " +
                "Join loaiban lb on lb.maLB = b.maLB " +
                "Join datban db on db.maBan = b.maBan " +
                "Join hoadon hd on db.maHD = hd.maHD " +
                "Join nhanvien nv on nv.maNV = lb.maNV " +
                "where nv.maNH = " +
                "(Select nvht.maNH from nhanvien nvht where nvht.maNV = ?) " +
                "AND b.trangThai = 1 " +
                "And  hd.trangThai <> 2 " +
                "And lb.maLB = ? ";

        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(maNV),String.valueOf(maLB)});
        if (c.moveToNext()){
            return c.getInt(c.getColumnIndex("DemSoLuong"));
        }
        return 0;
   }
}
