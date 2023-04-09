package sp23cp18103.nhom2.finedining.model;

public class ThongTinThongKeDoanhThu {
    private String month;
    private long doanhThu;

    public ThongTinThongKeDoanhThu(String month, long doanhThu) {
        this.month = month;
        this.doanhThu = doanhThu;
    }

    public ThongTinThongKeDoanhThu() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(long doanhThu) {
        this.doanhThu = doanhThu;
    }
}
