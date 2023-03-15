package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.LoaiBan;

public class LoaiBanDAO {
    SQLiteDatabase db;
    public LoaiBanDAO(Context context){
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public long insertloaiban(LoaiBan obj){
        ContentValues values=new ContentValues();
        values.put("tenLoai",obj.getTenLoai());
        values.put("maNV",obj.getMaNV());
        values.put("soChoNgoi",obj.getSoChoNgoi());
        values.put("trangThai",obj.getTrangThai());
        return db.insert("loaiban",null,values);
    }
    public int updateloaiban(LoaiBan obj){
        ContentValues values=new ContentValues();
        values.put("tenLoai",obj.getTenLoai());
        values.put("maNV",obj.getMaNV());
        values.put("soChoNgoi",obj.getSoChoNgoi());
        values.put("trangThai",obj.getTrangThai());
        return db.update("loaiban",values,"maLB=?",new String[]{String.valueOf(obj.getMaLB())});
    }
    public List<LoaiBan> getAllLoaiBan(){
        String sql="select*from loaiban";
        return getDaTa(sql);
    }
   @SuppressLint("Range")
   public List<LoaiBan> getDaTa(String sql, String...selectavg){
        List<LoaiBan>list=new ArrayList<>();
       Cursor c=db.rawQuery(sql,selectavg);
       while (c.moveToNext()){
           LoaiBan obj=new LoaiBan();
           obj.setMaLB(Integer.parseInt(c.getString(c.getColumnIndex("maLB"))));
           obj.setTenLoai(c.getString(c.getColumnIndex("tenLoai")));
           obj.setMaNV(Integer.parseInt(c.getString(c.getColumnIndex("maNV"))));
           obj.setSoChoNgoi(Integer.parseInt(c.getString(c.getColumnIndex("soChoNgoi"))));
           obj.setTrangThai(Integer.parseInt(c.getString(c.getColumnIndex("trangThai"))));
           list.add(obj);
       }
       return list;
   }
}
