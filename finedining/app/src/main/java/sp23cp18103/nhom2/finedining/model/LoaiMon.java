package sp23cp18103.nhom2.finedining.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class LoaiMon {
    private int maLM;
    private String tenLoai;
    private int maNV;//Nhân viên quản lý
    private int trangThai;

    public LoaiMon(int maLM,String tenLoai, int maNV, int trangThai) {
        this.maLM = maLM;
        this.tenLoai = tenLoai;
        this.maNV = maNV;
        this.trangThai = trangThai;
    }

    public LoaiMon() {
    }

    public int getMaLM() {
        return maLM;
    }

    public void setMaLM(int maLM) {
        this.maLM = maLM;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoaiMon loaiMon = (LoaiMon) o;
        return maLM == loaiMon.maLM;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maLM);
    }

    /*
     * Trả về tên của trạng thái
     * 1:"Dùng"; 0:"Không dùng"
     * */
    public String getTenTrangThai(){
        return (this.trangThai == 1) ? "Dùng" : "Không Dùng";
    }

    @NonNull
    @Override
    public String toString() {
        return getTenLoai();
    }
}
