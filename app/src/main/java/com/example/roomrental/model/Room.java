package com.example.roomrental.model;

public class Room {
    private String roomId;
    private String roomName;
    private double price;
    private boolean isRented; // true = Đã thuê, false = Còn trống
    private String tenantName;
    private String phone;

    // Constructor đầy đủ
    public Room(String roomId, String roomName, double price, boolean isRented, String tenantName, String phone) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.price = price;
        this.isRented = isRented;
        this.tenantName = tenantName;
        this.phone = phone;
    }

    // Getters and Setters
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public boolean isRented() { return isRented; }
    public void setRented(boolean rented) { isRented = rented; }
    public String getTenantName() { return tenantName; }
    public void setTenantName(String tenantName) { this.tenantName = tenantName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
