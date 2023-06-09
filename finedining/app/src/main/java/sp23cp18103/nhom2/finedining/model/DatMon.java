package sp23cp18103.nhom2.finedining.model;

import java.util.Objects;

import sp23cp18103.nhom2.finedining.database.MonDAO;

public class DatMon {
    private int maMon;
    private int maHD;
    private int soLuong;
    private int trangThai;//1: "Còn dùng; 0: "Hủy"

    public DatMon(int maMon, int maHD, int soLuong, int trangThai) {
        this.maMon = maMon;
        this.maHD = maHD;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public DatMon() {
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatMon datMon = (DatMon) o;
        return maMon == datMon.maMon && maHD == datMon.maHD;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maMon, maHD);
    }

    @Override
    public String toString() {
        return ""+ maMon +"x"+ soLuong
                ;
    }
}
