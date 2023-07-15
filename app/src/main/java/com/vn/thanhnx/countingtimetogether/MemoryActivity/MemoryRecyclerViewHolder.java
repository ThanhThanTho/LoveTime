package com.vn.thanhnx.countingtimetogether.MemoryActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.thanhnx.countingtimetogether.ChangeFontActivity.FontSelectActivity;
import com.vn.thanhnx.countingtimetogether.Model.MemoryPic;
import com.vn.thanhnx.countingtimetogether.R;

public class MemoryRecyclerViewHolder extends RecyclerView.ViewHolder {
    Context context;
    ImageView memoryPic;
    TextView memoryID;
    private void bindingView(){
        memoryPic = itemView.findViewById(R.id.imageViewMemoryPic);
        memoryID = itemView.findViewById(R.id.textViewID);
    }

    private void bindingAction(){
        memoryPic.setOnClickListener(this::onPicClick);
    }

    private void onPicClick(View view) {
        Intent i = new Intent(this.context, MemoryDetailActivity.class);
        i.putExtra("picID", memoryID.getText().toString());
        context.startActivity(i);
    }

    public void setPic(MemoryPic memoryPic){
        this.memoryID.setText(String.valueOf(memoryPic.getPictureID()));
        this.memoryPic.setImageURI(memoryPic.getPicture());
    }
    public MemoryRecyclerViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bindingView();
        bindingAction();
    }
}
