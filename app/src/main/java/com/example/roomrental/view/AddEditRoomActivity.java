package com.example.roomrental.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.roomrental.R;
import com.example.roomrental.controller.RoomController;
import com.example.roomrental.model.Room;

/**
 * @author Dương Văn Thuận - B22DCCN841
 */
public class AddEditRoomActivity extends AppCompatActivity {

    private EditText edtRoomId, edtRoomName, edtPrice, edtTenantName, edtPhone;
    private Switch switchStatus;
    private Button btnSave;

    private RoomController controller;
    private int roomIndex = -1; // -1 nghĩa là thêm mới

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_room);

        controller = RoomController.getInstance();
        initViews();

        // Kiểm tra xem là Thêm hay Sửa
        if (getIntent() != null && getIntent().hasExtra("ROOM_INDEX")) {
            roomIndex = getIntent().getIntExtra("ROOM_INDEX", -1);
            if (roomIndex != -1) {
                loadRoomData(roomIndex);
                setTitle("Sửa thông tin phòng");
            }
        } else {
            setTitle("Thêm phòng mới");
        }

        btnSave.setOnClickListener(v -> saveRoom());
    }

    private void initViews() {
        edtRoomId = findViewById(R.id.edtRoomId);
        edtRoomName = findViewById(R.id.edtRoomName);
        edtPrice = findViewById(R.id.edtPrice);
        edtTenantName = findViewById(R.id.edtTenantName);
        edtPhone = findViewById(R.id.edtPhone);
        switchStatus = findViewById(R.id.switchStatus);
        btnSave = findViewById(R.id.btnSave);
    }

    private void loadRoomData(int index) {
        Room room = controller.getRooms().get(index);
        edtRoomId.setText(room.getRoomId());
        // Khóa không cho sửa mã phòng (Tùy chọn)
        edtRoomId.setEnabled(false);
        edtRoomName.setText(room.getRoomName());
        edtPrice.setText(String.valueOf(room.getPrice()));
        switchStatus.setChecked(room.isRented());
        edtTenantName.setText(room.getTenantName());
        edtPhone.setText(room.getPhone());
    }

    private void saveRoom() {
        String id = edtRoomId.getText().toString().trim();
        String name = edtRoomName.getText().toString().trim();
        String priceStr = edtPrice.getText().toString().trim();
        boolean isRented = switchStatus.isChecked();
        String tenantName = edtTenantName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        // Validate dữ liệu từ Controller
        String validationError = controller.validateRoom(id, name, priceStr);
        if (!validationError.isEmpty()) {
            Toast.makeText(this, validationError, Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

        // Logic bổ sung: Nếu check đã thuê thì nên có tên người thuê
        if (isRented && tenantName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên người thuê!", Toast.LENGTH_SHORT).show();
            return;
        }

        Room room = new Room(id, name, price, isRented, tenantName, phone);

        if (roomIndex == -1) {
            // Thêm mới
            controller.addRoom(room);
            Toast.makeText(this, "Thêm phòng thành công!", Toast.LENGTH_SHORT).show();
        } else {
            // Cập nhật
            controller.updateRoom(roomIndex, room);
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }

        finish(); // Đóng Activity và quay lại MainActivity
    }
}