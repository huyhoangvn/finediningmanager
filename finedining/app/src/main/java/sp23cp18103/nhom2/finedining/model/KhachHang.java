package sp23cp18103.nhom2.finedining.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class KhachHang {
    private int maKH;
    private String tenKH;
    private int gioiTinh;//1:"Nam", 2:"Nữ", 0:"Khác"
    private String sdt;//Số điện thoại
    private String taiKhoan;//Hiện tại chưa dùng
    private String matKhau;//Hiện tại chưa dùng
    private String hinh;

    public KhachHang(int maKH, String tenKH, int gioiTinh, String sdt, String taiKhoan, String matKhau, String hinh) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.hinh = hinh;
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
