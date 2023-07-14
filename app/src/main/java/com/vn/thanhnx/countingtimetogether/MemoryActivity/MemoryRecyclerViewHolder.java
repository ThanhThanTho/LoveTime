package com.vn.thanhnx.countingtimetogether.MemoryActivity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.thanhnx.countingtimetogether.Model.MemoryPic;
import com.vn.thanhnx.countingtimetogether.R;

public class MemoryRecyclerViewHolder extends RecyclerView.ViewHolder {
    Context context;
    ImageView memoryPic;
    private void bindingView(){
        memoryPic = itemView.findViewById(R.id.imageViewMemoryPic);
    }

    private void bindingAction(){

    }

    public void setPic(MemoryPic memoryPic){
        this.memoryPic.setImageURI(memoryPic.getPicture());
    }
    public MemoryRecyclerViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindingView();
        bindingAction();
    }
}
