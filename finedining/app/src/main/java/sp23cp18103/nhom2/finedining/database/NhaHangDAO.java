package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.NhaHang;
import sp23cp18103.nhom2.finedining.model.NhanVien;

public class NhaHangDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public NhaHangDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<NhaHang> getDataNhaHang(String sql, String...Args){
        List<NhaHang> listnhahang = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,Args);
        while (cursor.moveToNext()){
            NhaHang nhaHang = new NhaHang();
            nhaHang.setMaNH(cursor.getInt(cursor.getColumnIndex("maNH")));
            nhaHang.setTenNH(cursor.getString(cursor.getColumnIndex("tenNH")));
            nhaHang.setDiaChi(cursor.getString(cursor.getColumnIndex("diaChi")));
            nhaHang.setHinh(cursor.getString(cursor.getColumnIndex("hinh")));
            listnhahang.add(nhaHang);
        }
        return listnhahang;
    }

    public List<NhaHang> getAllNhaHang(){
        String sql = "select * from nhahang";
        return getDataNhaHang(sql);
    }

    public long insertNhaHang(NhaHang nhaHang){
        ContentValues values = new ContentValues();
        values.put("tenNH",nhaHang.getTenNH());
        values.put("diaChi",nhaHang.getDiaChi());
        values.put("hinh",nhaHang.getHinh());
        return db.insert("nhahang",null,values);
    }

    public long updateNhaHang(NhaHang nhaHang){
        ContentValues values = new ContentValues();
        values.put("tenNH",nhaHang.getTenNH());
        values.put("diaChi",nhaHang.getDiaChi());
        values.put("hinh",nhaHang.getHinh());
        return db.update("nhahang",values,"maNH = ?", new String[]{String.valueOf(nhaHang.getMaNH())});
    }

    public boolean checknhahang(String nhahang){
        String sql = String.format("select * from nhahang where tenNH = '%s' ",nhahang);
        Cursor cursor = db.rawQuery(sql,null);
        return cursor.getCount() > 0;
    }
}