package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.ThongTinMon;

public class ThongTinMonDAO {
    private SQLiteDatabase db;

    public ThongTinMonDAO(Context context){
        DBHelper dbHelper =new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<ThongTinMon> getTopMon(){
        String sql = "SELECT mon.hinh, mon.tenMon, SUM(datmon.soLuong) AS soluongmon, SUM(datmon.soLuong * mon.gia) AS doanhThumon\n" +
                "FROM mon \n" +
                "INNER JOIN datmon ON mon.maMon = datmon.maMon \n" +
                "INNER JOIN hoadon ON datmon.maHD = hoadon.maHD \n" +
                "WHERE strftime('%Y', hoadon.thoiGianXuat) LIKE '2023' " +
                "GROUP BY mon.tenMon\n" +
                "ORDER BY soLuong DESC \n" +
                "LIMIT 10;";
       return getDaTa(sql);
    }

    @SuppressLint("Range")
    public List<ThongTinMon> getDaTa(String sql, String...SelectArgs){
        List<ThongTinMon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,SelectArgs);
        while (cursor.moveToNext()){
            ThongTinMon ttMon = new ThongTinMon();
            ttMon.setHinhMon(cursor.getString(cursor.getColumnIndex("hinh")));
            ttMon.setTenMonThongKe(cursor.getString(cursor.getColumnIndex("tenMon")));
            ttMon.setSoLuongMon(cursor.getInt(cursor.getColumnIndex("soluongmon")));
            ttMon.setDoanhThuMon(cursor.getInt(cursor.getColumnIndex("doanhThumon")));
            list.add(ttMon);
        }
        return list;
    }
}
