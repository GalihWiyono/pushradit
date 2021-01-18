package com.example.project;

public class DataModel {
    private int id ;
    private String nama_makanan;
    private double harga_makanan;

    public DataModel(int id, String nama_makanan, double harga_makanan) {
        this.id = id;
        this.nama_makanan = nama_makanan;
        this.harga_makanan = harga_makanan;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "id=" + id +
                ", nama_makanan='" + nama_makanan + '\'' +
                ", harga_makanan=" + harga_makanan +
                '}';
    }

    public DataModel() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public double getHarga_makanan() {
        return harga_makanan;
    }

    public void setHarga_makanan(double harga_makanan) {
        this.harga_makanan = harga_makanan;
    }
}
