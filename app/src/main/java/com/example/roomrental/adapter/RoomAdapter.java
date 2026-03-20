package com.example.roomrental.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roomrental.R;
import com.example.roomrental.model.Room;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

    public RoomAdapter(List<Room> roomList, OnItemClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvRoomName.setText(room.getRoomName() + " (" + room.getRoomId() + ")");
        holder.tvPrice.setText(String.format("Giá: %,.0f VND", room.getPrice()));

        // Yêu cầu: Tô màu tình trạng
        if (room.isRented()) {
            holder.tvStatus.setText("Đã thuê - " + room.getTenantName());
            holder.tvStatus.setTextColor(Color.RED);
        } else {
            holder.tvStatus.setText("Còn trống");
            holder.tvStatus.setTextColor(Color.parseColor("#008000")); // Màu xanh Green
        }
    }

    @Override
    public int getItemCount() { return roomList.size(); }

    class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvPrice, tvStatus;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            // Bắt sự kiện
            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                listener.onItemLongClick(getAdapterPosition());
                return true;
            });
        }
    }
}