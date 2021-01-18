package com.example.project;

public class HistoryModel {
    private int id;
    private String allitem, tanggal;
    private double totalharga;

    public HistoryModel(int id, String allitem, double totalharga,  String tanggal) {
        this.id = id;
        this.allitem = allitem;
        this.tanggal = tanggal;
        this.totalharga = totalharga;
    }

    public HistoryModel() {
    }

    @Override
    public String toString() {
        return "HistoryModel{" +
                "id=" + id +
                ", allitem='" + allitem + '\'' +
                ", tanggal='" + tanggal + '\'' +
                ", totalharga=" + totalharga +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllitem() {
        return allitem;
    }

    public void setAllitem(String allitem) {
        this.allitem = allitem;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public double getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(double totalharga) {
        this.totalharga = totalharga;
    }
}
