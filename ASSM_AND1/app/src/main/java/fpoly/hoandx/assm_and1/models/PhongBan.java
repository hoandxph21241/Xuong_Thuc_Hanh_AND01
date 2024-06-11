package fpoly.hoandx.assm_and1.models;

import java.io.Serializable;

public class PhongBan implements Serializable {
    int idPhongBan;
    String tenPhongBan;
    int imgPhongBan;

    public PhongBan(int idPhongBan, String tenPhongBan, int imgPhongBan) {
        this.idPhongBan = idPhongBan;
        this.tenPhongBan = tenPhongBan;
        this.imgPhongBan = imgPhongBan;
    }

    public PhongBan(String tenPhongBan, int imgPhongBan) {
        this.tenPhongBan = tenPhongBan;
        this.imgPhongBan = imgPhongBan;
    }

    public int getIdPhongBan() {
        return idPhongBan;
    }

    public void setIdPhongBan(int idPhongBan) {
        this.idPhongBan = idPhongBan;
    }

    public String getTenPhongBan() {
        return tenPhongBan;
    }

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }

    public int getImgPhongBan() {
        return imgPhongBan;
    }

    public void setImgPhongBan(int imgPhongBan) {
        this.imgPhongBan = imgPhongBan;
    }

    @Override
    public String toString() {
        return tenPhongBan;
    }
}
