package sp23cp18103.nhom2.finedining.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sp23cp18103.nhom2.finedining.model.Ban;
import sp23cp18103.nhom2.finedining.model.LoaiBan;


public class BanDAO {
    SQLiteDatabase db;
    public BanDAO (Context context){
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public long insertban(Ban obj){
        ContentValues values=new ContentValues();
        values.put("maLB",obj.getMaLB());
        values.put("viTri",obj.getViTri());
        values.put("trangThai",obj.getTrangThai());
        return db.insert("ban",null,values);
    }
   public int updateban(Ban obj){
        ContentValues values=new ContentValues();
       values.put("maLB",obj.getMaLB());
       values.put("viTri",obj.getViTri());
       values.put("trangThai",obj.getTrangThai());
       return db.update("ban",values,"maBan=?",new String[]{String.valueOf(obj.getMaBan())});
   }
   public int deleteban(String id){
       return db.delete("ban","maBan=?",new String[]{id});
   }

   public List<Ban>getAllBan(){
        String sql="select*from ban";
        return getDaTa(sql);
   }

    @SuppressLint("Range")
    public String getViTri(int maBan) {
        String sql = "SELECT viTri From ban " +
                "WHERE maBan = ? ";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maBan)});
        if (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex("viTri"));
        }
        return "";
    }

    public List<Ban> gettimKiem(int maNV, String viTri ){
        String sql = "Select * from ban b " +
                "JOIN loaiban lb ON lb.maLB = b.maLB " +
                "JOIN nhanvien nv ON lb.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND b.viTri LIKE ? ";
        return getDaTa(sql, String.valueOf(maNV), String.valueOf(viTri + "%"));
    }
   @SuppressLint("Range")
   public List<Ban> getDaTa(String sql, String...selectavg){
      List<Ban>list=new ArrayList<>();
       Cursor c=db.rawQuery(sql,selectavg);
       while (c.moveToNext()){
          Ban obj = new Ban();
          obj.setMaBan(Integer.parseInt(c.getString(c.getColumnIndex("maBan"))));
          obj.setMaLB(Integer.parseInt(c.getString(c.getColumnIndex("maLB"))));
          obj.setViTri(c.getString(c.getColumnIndex("viTri")));
          obj.setTrangThai(c.getInt(c.getColumnIndex("trangThai")));
          list.add(obj);
       }
       return list;
    }

    public Ban getID(int maNV){
        String sql="select*from ban where viTri=?";
        List<Ban> list = getDaTa(sql, String.valueOf(maNV));
        return list.get(0);
    }

    public List<Ban> gettimKiem(int maNV,String timKiem ,String trangThai){
        String sql = "Select b.maBan,b.maLB,b.viTri,b.trangThai from ban b " +
                "JOIN loaiban lb ON lb.maLB = b.maLB " +
                "JOIN nhanvien nv ON lb.maNV = nv.maNV " +
                "WHERE nv.maNH = " +
                " ( SELECT nvht.maNH FROM nhanvien nvht WHERE nvht.maNV = ? ) " +
                "AND b.vitri LIKE ? " +
                "AND b.trangThai >= ?";
        return getDaTa(sql, String.valueOf(maNV),String.valueOf("%" + timKiem + "%"), String.valueOf(trangThai ));
    }
   public int getKiemTraConTrong(int maBan){
        String sql="Select * from ban b " +
                "JOIN datban db on db.maBan = b.maBan " +
                "JOIN hoadon hd on db.maHD = hd.maHD " +
                "where b.trangThai = 2 " +
                "and b.maBan = ? ";

        return getDaTa(sql,String.valueOf(maBan)).size();
   }

}
