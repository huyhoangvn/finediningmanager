package sp23cp18103.nhom2.finedining.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class NhaHang {
    private int maNH;
    private String tenNH;
    private String diaChi;
    private String hinh;

    public NhaHang(String tenNH, String diaChi, String hinh) {
        this.maNH = maNH;
        this.tenNH = tenNH;
        this.diaChi = diaChi;
        this.hinh = hinh;
    }

    public NhaHang() {
    }

    public int getMaNH() {
        return maNH;
    }

    public void setMaNH(int maNH) {
        this.maNH = maNH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTenNH() {
        return tenNH;
    }

    public void setTenNH(String tenNH) {
        this.tenNH = tenNH;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhaHang nhaHang = (NhaHang) o;
        return maNH == nhaHang.maNH;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNH);
    }

    @NonNull
    @Override
    public String toString() {
        return getTenNH();
    }
}
