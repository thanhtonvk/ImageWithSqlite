package com.example.imagewithsqlite;

public class HinhAnh {
    private String ten;
    private byte[]anh;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public HinhAnh(String ten, byte[] anh) {
        this.ten = ten;
        this.anh = anh;
    }
}
