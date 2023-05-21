package com.example.a13905_uas_ppb;

public class modelMenu {
    int gambar_menuDashboard;
    String nama_menuDashboard;

    public modelMenu(int gambar_menuDashboard, String nama_menuDashboard) {
        this.gambar_menuDashboard = gambar_menuDashboard;
        this.nama_menuDashboard = nama_menuDashboard;
    }

    public int getGambar_menuDashboard() {
        return gambar_menuDashboard;
    }

    public void setGambar_menuDashboard(int gambar_menuDashboard) {
        this.gambar_menuDashboard = gambar_menuDashboard;
    }

    public String getNama_menuDashboard() {
        return nama_menuDashboard;
    }

    public void setNama_menuDashboard(String nama_menuDashboard) {
        this.nama_menuDashboard = nama_menuDashboard;
    }
}