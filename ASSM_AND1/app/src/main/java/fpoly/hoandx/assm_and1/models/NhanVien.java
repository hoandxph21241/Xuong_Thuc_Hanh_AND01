package fpoly.hoandx.assm_and1.models;

import java.io.Serializable;

public class NhanVien implements Serializable {
    int id;
    String name;
    int img;
    String manv;
    String phongban;

    public NhanVien(int id, String name, int img, String manv, String phongban) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.manv = manv;
        this.phongban = phongban;
    }

    public NhanVien(String name, int img, String manv, String phongban) {
        this.name = name;
        this.img = img;
        this.manv = manv;
        this.phongban = phongban;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getPhongban() {
        return phongban;
    }

    public void setPhongban(String phongban) {
        this.phongban = phongban;
    }
}
