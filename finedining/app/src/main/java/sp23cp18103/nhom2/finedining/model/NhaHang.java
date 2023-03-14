package sp23cp18103.nhom2.finedining.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class NhaHang {
    private int maNH;
    private String tenNH;
    private String diaChi;
    private byte[] hinh;

    public NhaHang(int maNH, String tenNH, String diaChi, byte[] hinh) {
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

    public String getTenNhaHang() {
        return tenNH;
    }

    public void setTenNhaHang(String tenNhaHang) {
        this.tenNH = tenNhaHang;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
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
        return getTenNhaHang();
    }
}
