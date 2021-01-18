package com.example.project;

public class ModelCart {
    private  int id;
    private String nama;
    private double harga;
    private int quantity;
    private double totalHarga;

    public ModelCart(int id, String nama, double harga, int quantity, double totalHarga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.quantity = quantity;
        this.totalHarga = totalHarga;
    }

    @Override
    public String toString() {
        return "ModelCart{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", harga=" + harga +
                ", quantity=" + quantity +
                ", totalHarga=" + totalHarga +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }
}
