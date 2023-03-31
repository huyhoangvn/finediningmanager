package sp23cp18103.nhom2.finedining.model;

public class ThongTinChiTietDatMon {
    private String tenMon;
    private int gia;
    private int soLuong;
    private int thanhTien;

    public ThongTinChiTietDatMon(String tenMon, int gia, int soLuong, int thanhTien) {
        this.tenMon = tenMon;
        this.gia = gia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public ThongTinChiTietDatMon() {
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }
}
