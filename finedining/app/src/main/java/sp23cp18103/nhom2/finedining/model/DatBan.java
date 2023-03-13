package sp23cp18103.nhom2.finedining.model;

import java.util.Objects;

public class DatBan {
    private int maBan;
    private int maHD;
    private String thoiGianDat;//YYYY-mm-dd hh:mm

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

    public String getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(String thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
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
}
