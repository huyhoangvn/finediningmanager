package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.ThongTinChiTietDatMon;
import sp23cp18103.nhom2.finedining.model.ThongTinHoaDon;

public class ThongTinChiTietDatMonDAO {
    SQLiteDatabase db;

    public ThongTinChiTietDatMonDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<ThongTinChiTietDatMon> getThongTinHoaDonChiTietDatMon(int maHD){
        String sql = "SELECT mon.tenMon,datmon.soLuong,mon.gia,(mon.gia * datmon.soLuong) as thanhTien " +
                " FROM mon JOIN datmon ON datmon.maMon = mon.maMon WHERE datmon.maHD = ? ";
        return getDaTa(sql,String.valueOf(maHD));
    }

    @SuppressLint("Range")
    public int getTongSoTien(int maHD){
        String sql = "SELECT sum(mon.gia * datmon.soLuong) as thanhTien " +
                " FROM mon JOIN datmon ON datmon.maMon = mon.maMon " +
                "WHERE datmon.maHD = ? " +
                "AND datmon.trangThai = 1 " +
                "GROUP BY datmon.maHD ";

        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(maHD)});
        if (c.moveToNext()){
            return c.getInt(c.getColumnIndex("thanhTien"));
        }
        return 0;
    }
    @SuppressLint("Range")
    public List<Ban> getBan(int maHD){
        String sql = "SELECT ban.viTri FROM " +
                "ban JOIN datban ON datban.maBan = ban.maBan " +
                "WHERE datban.maHD =? " +
                "AND datban.trangThai = 1 ";

        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(maHD)});
        List<Ban> list = new ArrayList<>();
        while (c.moveToNext()){
            Ban ban = new Ban();
            ban.setViTri(c.getString(c.getColumnIndex("viTri")));
            list.add(ban);
        }
        return list;
    }



    @SuppressLint("Range")
    public List<ThongTinChiTietDatMon> getDaTa(String sql, String...SelectArgs){
        List<ThongTinChiTietDatMon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while (c.moveToNext()){

            ThongTinChiTietDatMon ttct = new ThongTinChiTietDatMon();

            ttct.setTenMon(c.getString(c.getColumnIndex("tenMon")));
            ttct.setSoLuong(c.getInt(c.getColumnIndex("soLuong")));
            ttct.setGia(c.getInt(c.getColumnIndex("gia")));
            ttct.setThanhTien(c.getInt(c.getColumnIndex("thanhTien")));

            list.add(ttct);
        }
        Log.d("zzzz", "getDaTa: "+list);
        return list;
    }
}
