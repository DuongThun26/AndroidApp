package com.example.roomrental.controller;

import com.example.roomrental.model.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private static RoomController instance;
    private List<Room> roomList;

    private RoomController() {
        roomList = new ArrayList<>();
        // Mock data để test ban đầu
        roomList.add(new Room("P101", "Phòng 101", 1500000, false, "", ""));
        roomList.add(new Room("P102", "Phòng 102", 2000000, true, "Nguyễn Văn A", "0987654321"));
    }

    public static synchronized RoomController getInstance() {
        if (instance == null) {
            instance = new RoomController();
        }
        return instance;
    }

    public List<Room> getRooms() {
        return roomList;
    }

    // Xử lý Validate dữ liệu trả về String báo lỗi, nếu rỗng là hợp lệ
    // Thêm tham số isNewRoom để biết là đang Thêm mới hay Đang sửa
    public String validateRoom(String id, String name, String priceStr, String phone, boolean isNewRoom) {
        // 1. Kiểm tra rỗng
        if (id.trim().isEmpty() || name.trim().isEmpty() || priceStr.trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ Mã phòng, Tên phòng và Giá thuê!";
        }

        // 2. Kiểm tra trùng Mã phòng (Chỉ áp dụng khi Thêm mới)
        if (isNewRoom) {
            for (Room room : roomList) {
                if (room.getRoomId().equalsIgnoreCase(id.trim())) {
                    return "Mã phòng này đã tồn tại, vui lòng nhập mã khác!";
                }
            }
        }

        // 3. Kiểm tra Giá thuê hợp lệ
        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) return "Giá thuê phải lớn hơn 0!";
        } catch (NumberFormatException e) {
            return "Giá thuê không hợp lệ!";
        }

        // 4. Kiểm tra số điện thoại (Nếu có nhập thì phải từ 9-11 số)
        if (!phone.trim().isEmpty() && !phone.matches("\\d{9,11}")) {
            return "Số điện thoại phải bao gồm 9 đến 11 chữ số!";
        }

        return ""; // Chuỗi rỗng nghĩa là Validate thành công
    }

    public void addRoom(Room room) {
        roomList.add(room);
    }

    public void updateRoom(int index, Room updatedRoom) {
        if (index >= 0 && index < roomList.size()) {
            roomList.set(index, updatedRoom);
        }
    }

    public void deleteRoom(int index) {
        if (index >= 0 && index < roomList.size()) {
            roomList.remove(index);
        }
    }
}