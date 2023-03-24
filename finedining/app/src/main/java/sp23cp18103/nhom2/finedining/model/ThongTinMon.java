package sp23cp18103.nhom2.finedining.model;

public class ThongTinMon {
    String tenMonThongKe;
    int soLuongMon;
    int doanhThuMon;

    public ThongTinMon(String tenMonThongKe, int soLuongMon, int doanhThuMon) {
        this.tenMonThongKe = tenMonThongKe;
        this.soLuongMon = soLuongMon;
        this.doanhThuMon = doanhThuMon;
    }

    public ThongTinMon() {
    }

    public String getTenMonThongKe() {
        return tenMonThongKe;
    }

    public void setTenMonThongKe(String tenMonThongKe) {
        this.tenMonThongKe = tenMonThongKe;
    }

    public int getSoLuongMon() {
        return soLuongMon;
    }

    public void setSoLuongMon(int soLuongMon) {
        this.soLuongMon = soLuongMon;
    }

    public int getDoanhThuMon() {
        return doanhThuMon;
    }

    public void setDoanhThuMon(int doanhThuMon) {
        this.doanhThuMon = doanhThuMon;
    }
}

