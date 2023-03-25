package sp23cp18103.nhom2.finedining.model;

public class ThongTinHoaDon {
    private int maHD;
    private String tenNhanVien;
    private String tenKhachHang;
    private int soLuongKhachHang;
    private String thoiGianXuat;
    private int trangThai;

    public ThongTinHoaDon(int maHD,String tenNhanVien,String tenKhachHang, int soLuongKhachHang, String thoiGianXuat, int trangThai) {
        this.maHD = maHD;
        this.tenKhachHang = tenKhachHang;
        this.tenNhanVien = tenNhanVien;
        this.soLuongKhachHang = soLuongKhachHang;
        this.thoiGianXuat = thoiGianXuat;
        this.trangThai = trangThai;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public ThongTinHoaDon() {
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public int getSoLuongKhachHang() {
        return soLuongKhachHang;
    }

    public void setSoLuongKhachHang(int soLuongKhachHang) {
        this.soLuongKhachHang = soLuongKhachHang;
    }

    public String getThoiGianXuat() {
        return thoiGianXuat;
    }

    public void setThoiGianXuat(String thoiGianXuat) {
        this.thoiGianXuat = thoiGianXuat;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
