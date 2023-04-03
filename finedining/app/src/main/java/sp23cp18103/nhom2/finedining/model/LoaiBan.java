package sp23cp18103.nhom2.finedining.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class LoaiBan {
    private int maLB;
    private String tenLoai;
    private int maNV;//Nhân viên quản lý
//    private int soChoNgoi;
    private int trangThai;// 1:"Dùng"; 0:"Không dùng"

    public LoaiBan(int maLB, String tenLoai, int maNV, int trangThai) {
        this.maLB = maLB;
        this.tenLoai = tenLoai;
        this.maNV = maNV;
//        this.soChoNgoi = soChoNgoi;
        this.trangThai = trangThai;
    }

    public LoaiBan() {
    }

    public int getMaLB() {
        return maLB;
    }

    public void setMaLB(int maLB) {
        this.maLB = maLB;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

//    public int getSoChoNgoi() {
//        return soChoNgoi;
//    }
//
//    public void setSoChoNgoi(int soChoNgoi) {
//        this.soChoNgoi = soChoNgoi;
//    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
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
        LoaiBan loaiBan = (LoaiBan) o;
        return maLB == loaiBan.maLB;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maLB);
    }

    @NonNull
    @Override
    public String toString() {
        return getTenLoai();
    }
}
