package sp23cp18103.nhom2.finedining.model;

public class ThongTinThongKeDoanhThu {
    private String month;
    private int doanhThu;

    public ThongTinThongKeDoanhThu(String month, int doanhThu) {
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

    public int getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(int doanhThu) {
        this.doanhThu = doanhThu;
    }
}
