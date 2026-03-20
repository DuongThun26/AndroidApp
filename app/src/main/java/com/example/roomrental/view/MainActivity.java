package com.example.roomrental.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roomrental.R;
import com.example.roomrental.adapter.RoomAdapter;
import com.example.roomrental.controller.RoomController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RoomAdapter adapter;
    private RoomController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = RoomController.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RoomAdapter(controller.getRooms(), new RoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Update - Mở màn hình sửa và truyền vị trí
                Intent intent = new Intent(MainActivity.this, AddEditRoomActivity.class);
                intent.putExtra("ROOM_INDEX", position);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {
                // Delete - Hiển thị AlertDialog
                showDeleteDialog(position);
            }
        });
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            // Create - Mở màn hình thêm mới
            startActivity(new Intent(MainActivity.this, AddEditRoomActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); // Cập nhật lại list sau khi thêm/sửa
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa phòng này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    controller.deleteRoom(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, controller.getRooms().size());
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}