package sp23cp18103.nhom2.finedining.model;

public class ThongTinDatBan extends DatBan{
    private String viTri;

    public ThongTinDatBan(String viTri) {
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
