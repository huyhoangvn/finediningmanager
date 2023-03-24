package sp23cp18103.nhom2.finedining.model;

public class ThongTinDatMon extends DatMon{
    private String tenMon;

    public ThongTinDatMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public ThongTinDatMon() {
    }

    @Override
    public String toString() {
        return ""+getTenMon()+"x"+getSoLuong();
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }
}
