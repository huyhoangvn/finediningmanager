package sp23cp18103.nhom2.finedining.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class NhanVien {
    private int maNV;
    private int maNH;
    private String tenNV;
    private int gioiTinh;// 1:"Nam", 2:"Nữ", 0:"Khác"
    private String ngaySinh;//YYYY-mm-dd
    private String sdt;//Số điện thoại
    private int phanQuyen;// 1:"Quản lý"; 0:"Nhân viên"
    private int trangThai;// 1:"Làm"; 0:"Nghỉ"
    private String taiKhoan;
    private String matKhau;
    private String hinh;//URL



    public NhanVien() {
    }

    public NhanVien(int maNV, int maNH, String tenNV, int gioiTinh, String ngaySinh, String sdt, int phanQuyen, int trangThai, String taiKhoan, String matKhau, String hinh) {
        this.maNV = maNV;
        this.maNH = maNH;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.phanQuyen = phanQuyen;
        this.trangThai = trangThai;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.hinh = hinh;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getMaNH() {
        return maNH;
    }

    public void setMaNH(int maNH) {
        this.maNH = maNH;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(int phanQuyen) {
        this.phanQuyen = phanQuyen;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    /*
     * Trả về tên của trạng thái
     * 1:"Làm"; 0:"Nghỉ"
     * */
    public String getTenTrangThai(){
        return (this.trangThai == 1) ? "Làm" : "Nghỉ";
    }

    /*
     * Trả về tên của phân quyền
     * 1:"Quản lý"; 0:"Nhân viên"
     * */
    public String getTenPhanQuyen(){
        return (this.phanQuyen == 1) ? "Quản lý" : "Nhân viên";
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhanVien nhanVien = (NhanVien) o;
        return maNV == nhanVien.maNV;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNV);
    }

    @NonNull
    @Override
    public String toString() {
        return getTenNV();
    }
}
