package sp23cp18103.nhom2.finedining.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class KhachHang {
    private int maKH;
    private String tenKH;
    private int gioiTinh;//1:"Nam", 2:"Nữ", 0:"Khác"
    private String sdt;//Số điện thoại
    private int trangThai;//0:"Khách thường", 1:"Thành viên"

    public KhachHang(int maKH, String tenKH, int gioiTinh, String sdt, int trangThai) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.trangThai = trangThai;
    }

    public KhachHang() {
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    /*
     * Trả về tên của của giới tính
     * 1:"Nam", 2:"Nữ", 0:"Khác"
     * */
    public String getTenGioiTinh(){
        if ( this.gioiTinh == 1 ) {
            return "Nam";
        } else if ( this.gioiTinh == 2 ) {
            return "Nữ";
        } else {
            return "Khác";
        }
    }

    /*
     * Trả về tên của của giới tính
     * 1:"Khách thường", 2:"Thành viên"
     * */
    public String getTenTrangThai(){
        if ( this.trangThai == 1 ) {
            return "Thành viên";
        }
        else {
            return "Khách thường";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KhachHang khachHang = (KhachHang) o;
        return maKH == khachHang.maKH;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKH);
    }

    @NonNull
    @Override
    public String toString() {
        return getTenKH();
    }
}
