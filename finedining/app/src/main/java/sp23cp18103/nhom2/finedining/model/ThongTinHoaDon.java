package sp23cp18103.nhom2.finedining.model;

public class ThongTinHoaDon {
    private int maHD;
    private String tenKhachHang;
    private int soLuongKhachHang;
    private String thoiGianXuat;
    private int trangThai;

    public ThongTinHoaDon(int maHD,String tenKhachHang, int soLuongKhachHang, String thoiGianXuat, int trangThai) {
        this.maHD = maHD;
        this.tenKhachHang = tenKhachHang;
        this.soLuongKhachHang = soLuongKhachHang;
        this.thoiGianXuat = thoiGianXuat;
        this.trangThai = trangThai;
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
