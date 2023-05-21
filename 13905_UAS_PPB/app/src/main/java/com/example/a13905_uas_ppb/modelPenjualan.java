package com.example.a13905_uas_ppb;

public class modelPenjualan {
    String kode;
    String nama_barang;
    String satuan;
    String harga;
    String jumlah;
    String terjual;

    public modelPenjualan(String kode, String nama_barang, String satuan, String harga, String jumlah, String terjual) {
        this.kode = kode;
        this.nama_barang = nama_barang;
        this.satuan = satuan;
        this.harga = harga;
        this.jumlah = jumlah;
        this.terjual = terjual;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTerjual() {
        return terjual;
    }

    public void setTerjual(String terjual) {
        this.terjual = terjual;
    }
}
