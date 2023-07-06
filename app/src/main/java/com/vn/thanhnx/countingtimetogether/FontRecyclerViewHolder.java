package com.vn.thanhnx.countingtimetogether;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.thanhnx.countingtimetogether.Model.Font;

import java.util.ArrayList;

public class FontRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private TextView fontName;

    private void bindingView(){
        fontName = itemView.findViewById(R.id.fontName);
    }

    private void bindingAction(){
        fontName.setOnClickListener(this::onFontClick);
    }

    private void onFontClick(View view) {

    }

    public void setFont(Font font){
        fontName.setText(font.getFontName());
        fontName.setTypeface(font.getFontType());
        //textViewList.add(fontName);
    }
    public FontRecyclerViewHolder(@NonNull View view, Context context) {
        super(view);
        this.context = context;
        bindingView();
        bindingAction();
    }

    public TextView getFontName() {
        return fontName;
    }
}
