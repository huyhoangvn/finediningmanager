package sp23cp18103.nhom2.finedining.model;

public class ThongTinThongKeKhachHang {
    private String month;
    private int soLuong;

    public ThongTinThongKeKhachHang() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public ThongTinThongKeKhachHang(String month, int soLuong) {
        this.month = month;
        this.soLuong = soLuong;
    }
}
