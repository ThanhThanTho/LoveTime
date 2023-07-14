package com.vn.thanhnx.countingtimetogether.MemoryActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.thanhnx.countingtimetogether.Model.MemoryPic;
import com.vn.thanhnx.countingtimetogether.R;

import java.util.ArrayList;

public class MemoryRecyclerAdapter extends RecyclerView.Adapter<MemoryRecyclerViewHolder> {
    private ArrayList<MemoryPic> memoryPics;
    private Context context;
    private LayoutInflater inflater;
    public MemoryRecyclerAdapter(ArrayList<MemoryPic> memoryPics, Context context) {
        this.memoryPics = memoryPics;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MemoryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.memories_item, parent, false);
        return new MemoryRecyclerViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryRecyclerViewHolder holder, int position) {
        MemoryPic memoryPic = memoryPics.get(position);
        holder.setPic(memoryPic);
    }

    @Override
    public int getItemCount() {
        return memoryPics.size();
    }
}
