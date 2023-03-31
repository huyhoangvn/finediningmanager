package sp23cp18103.nhom2.finedining.model;

import java.util.Objects;

public class DatBan {
    private int maBan;
    private int maHD;
    private int trangThai;//1: "Còn dùng; 0: "Hủy"

    public DatBan() {
    }

    public DatBan(int maBan, int maHD, int trangThai) {
        this.maBan = maBan;
        this.maHD = maHD;
        this.trangThai = trangThai;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
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
        DatBan datBan = (DatBan) o;
        return maBan == datBan.maBan && maHD == datBan.maHD;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maBan, maHD);
    }


    @Override
    public String toString() {
        return "" +maBan ;

    }
}
