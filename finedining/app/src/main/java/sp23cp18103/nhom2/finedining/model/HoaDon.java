package sp23cp18103.nhom2.finedining.model;

import java.util.Objects;

public class HoaDon {
    private int maHD;
    private int maKH;
    private int maNV;
    private int soLuongKhach;
    private String thoiGianXuat;//YYYY-mm-dd hh:mm
    private String thoiGianDat;//YYYY-mm-dd hh:mm
    private int trangThai;// 1:"Đang đặt"; 2:"Chờ thanh toán"; 3:"Đã thanh toán"; 0:"Hủy"

    public HoaDon(int maHD, int maKH, int maNV, int soLuongKhach, String thoiGianXuat, String thoiGianDat, int trangThai) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.soLuongKhach = soLuongKhach;
        this.thoiGianXuat = thoiGianXuat;
        this.thoiGianDat = thoiGianDat;
        this.trangThai = trangThai;
    }

    public HoaDon() {
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getSoLuongKhach() {
        return soLuongKhach;
    }

    public void setSoLuongKhach(int soLuongKhach) {
        this.soLuongKhach = soLuongKhach;
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

    public String getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(String thoiGianĐat) {
        this.thoiGianDat = thoiGianDat;
    }


    /*
     * Trả về tên của của trạng thái
     * 1:"Đã thanh toán", 2:"Chưa thanh toán", 0:"Hủy"
     * */
    public String getTenGioiTinh(){
        if ( this.trangThai == 3 ) {
            return "Đã thanh toán";
        } else if ( this.trangThai == 2 ) {
            return "Chờ thanh toán";
        } else if ( this.trangThai == 1 ) {
            return "Đang đặt";
        } else {
            return "Hủy";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoaDon hoaDon = (HoaDon) o;
        return maHD == hoaDon.maHD;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHD);
    }
}
