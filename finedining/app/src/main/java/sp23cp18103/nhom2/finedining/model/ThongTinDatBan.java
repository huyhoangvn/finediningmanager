package sp23cp18103.nhom2.finedining.model;

import java.util.Objects;

public class ThongTinDatBan extends DatBan{
    private String viTri;

    public ThongTinDatBan(int maBan, int maHD, int trangThai, String viTri) {
        super(maBan, maHD, trangThai);
        this.viTri = viTri;
    }

    public ThongTinDatBan() {
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    @Override
    public String toString() {
        return ""+ getViTri();
    }
}
