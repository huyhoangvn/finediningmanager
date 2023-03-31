package sp23cp18103.nhom2.finedining.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Mon {
    private int maMon;
    private int maLM;
    private String tenMon;

    private int gia;
    private int trangThai; // 1:"Dùng"; 0:"Không dùng"
    private String hinh;

    public Mon(int maMon,int maLM , String tenMon , int gia, int trangThai, String hinh) {
        this.maMon = maMon;
        this.maLM = maLM;
        this.tenMon = tenMon;
        this.gia = gia;
        this.trangThai = trangThai;
        this.hinh = hinh;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public Mon() {
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public int getMaLM() {
        return maLM;
    }

    public void setMaLM(int maLM) {
        this.maLM = maLM;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    /*
     * Trả về tên của trạng thái
     * 1:"Dùng"; 0:"Không dùng"
     * */
    public String getTenTrangThai(){
        return (this.trangThai == 1) ? "Dùng" : "Không Dùng";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mon mon = (Mon) o;
        return maMon == mon.maMon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maMon);
    }
}
