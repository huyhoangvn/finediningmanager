package sp23cp18103.nhom2.finedining.model;

public class ThongTinMon {
    String hinhMon;
    String tenMonThongKe;
    int soLuongMon;
    long doanhThuMon;

    public ThongTinMon(String tenMonThongKe, int soLuongMon, long doanhThuMon) {
        this.tenMonThongKe = tenMonThongKe;
        this.soLuongMon = soLuongMon;
        this.doanhThuMon = doanhThuMon;
    }

    public ThongTinMon() {
    }

    public String getHinhMon() {
        return hinhMon;
    }

    public void setHinhMon(String hinhMon) {
        this.hinhMon = hinhMon;
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

    public long getDoanhThuMon() {
        return doanhThuMon;
    }

    public void setDoanhThuMon(long doanhThuMon) {
        this.doanhThuMon = doanhThuMon;
    }
}

