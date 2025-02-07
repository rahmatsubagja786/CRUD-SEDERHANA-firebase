package com.rahmat.myuasti;

public class MainModel {
    String nama,npm,alamat,nohp,murl;

    MainModel(){

    }
    public MainModel(String nama, String npm, String alamat, String nohp ,String murl) {
        this.nama = nama;
        this.npm = npm;
        this.alamat = alamat;
        this.nohp = nohp;
        this.murl = murl;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getMurl() {
        return murl;
    }

    public void setMurl(String murl) {
        this.murl = murl;
    }
}
