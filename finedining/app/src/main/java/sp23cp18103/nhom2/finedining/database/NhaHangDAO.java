package sp23cp18103.nhom2.finedining.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import sp23cp18103.nhom2.finedining.model.DatBan;
import sp23cp18103.nhom2.finedining.model.NhaHang;

/*
 * Tạo bảng nhà hàng
 * nhahang ( maNH, tenNH, diaChi, hinh )
 * */
public class NhaHangDAO {
    private SQLiteDatabase db;

    public NhaHangDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNhaHang(NhaHang nhaHang){
        ContentValues values = new ContentValues();
        values.put("maNH",nhaHang.getMaNH());
        values.put("tenNH",nhaHang.getTenNH());
        values.put("diaChi", nhaHang.getDiaChi());
        values.put("hinh", nhaHang.getHinh());
        return db.insert("nhahang",null,values);
    }
}
