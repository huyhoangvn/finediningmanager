package sp23cp18103.nhom2.finedining.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Ban {
    private int maBan;
    private int maLB;
    private String viTri;
    private int trangThai; // 1:"Dùng"; 0:"Không dùng"

    public Ban(int maBan, int maLB, String viTri, int trangThai) {
        this.maBan = maBan;
        this.maLB = maLB;
        this.viTri = viTri;
        this.trangThai = trangThai;
    }

    public Ban() {
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public int getMaLB() {
        return maLB;
    }

    public void setMaLB(int maLB) {
        this.maLB = maLB;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    /*
    * Trả về tên của trạng thái
    * 1:"Dùng"; 0:"Không dùng"
    * */
    public String getTenTrangThai(){
        return (this.trangThai == 1) ? "Dùng" : "Không Dùng";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ban ban = (Ban) o;
        return maBan == ban.maBan;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maBan);
    }

    @NonNull
    @Override
    public String toString() {
        return getViTri();
    }
}
