package com.vn.thanhnx.countingtimetogether.MemoryActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.thanhnx.countingtimetogether.Model.MemoryPic;
import com.vn.thanhnx.countingtimetogether.R;

import java.util.ArrayList;

public class MemoryRecyclerAdapter extends RecyclerView.Adapter<MemoryRecyclerViewHolder> {
    private ArrayList<MemoryPic> memoryPics;
    private Context context;
    private LayoutInflater inflater;
    private RecyclerView memoryRecyclerView;
    public MemoryRecyclerAdapter(ArrayList<MemoryPic> memoryPics, Context context, RecyclerView recyclerView) {
        this.memoryPics = memoryPics;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.memoryRecyclerView = recyclerView;
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

//        holder.memoryPic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(context, MemoryDetailActivity.class);
//                ((MemoryDetailActivity)context).startActivityForResult(i, REQUEST_CODE_MEMORY_DETAIL);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return memoryPics.size();
    }
}
