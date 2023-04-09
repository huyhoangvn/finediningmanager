package sp23cp18103.nhom2.finedining.model;

public class ThongTinThongKeKhachHang {
    private String month;
    private long soLuong;

    public ThongTinThongKeKhachHang() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(long soLuong) {
        this.soLuong = soLuong;
    }

    public ThongTinThongKeKhachHang(String month, long soLuong) {
        this.month = month;
        this.soLuong = soLuong;
    }
}
