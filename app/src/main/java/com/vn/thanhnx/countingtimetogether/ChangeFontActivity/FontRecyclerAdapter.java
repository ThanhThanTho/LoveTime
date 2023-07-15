package com.vn.thanhnx.countingtimetogether.ChangeFontActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.thanhnx.countingtimetogether.Model.Font;
import com.vn.thanhnx.countingtimetogether.R;

import java.util.List;

public class FontRecyclerAdapter extends RecyclerView.Adapter<FontRecyclerViewHolder> {
    private List<Font> fontList;
    private Context context;
    private LayoutInflater inflater;
    private RecyclerView fontRecycler;
    private TextView previewTextView;
    private TextView fontNameVar;
    public FontRecyclerAdapter(List<Font> fontList, Context context,
                               RecyclerView fontRecycler, TextView textView, TextView fontNameVar) {
        this.fontList = fontList;
        this.context = context;
        this.fontRecycler = fontRecycler;
        inflater = LayoutInflater.from(context);
        this.previewTextView = textView;
        this.fontNameVar = fontNameVar;
    }

    @NonNull
    @Override
    public FontRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.font_item, parent, false);
        return new FontRecyclerViewHolder(view, context);
    }

    private int selectedPosition = fontRecycler.NO_POSITION;
    @Override
    public void onBindViewHolder(@NonNull FontRecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //set data for each element in recycler
        Font font = fontList.get(position);
        holder.setFont(font);
        if (position == selectedPosition) {
            holder.getFontName().setBackgroundColor(Color.parseColor("#5dbbec"));
        } else {
            holder.getFontName().setBackgroundColor(Color.WHITE);
        }

        // Set an OnClickListener for the TextView
        holder.getFontName().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previewTextView.setTypeface(fontList.get(position).getFontType());
                fontNameVar.setText(fontList.get(position).getFontName());

                // Update the selected position and notify the adapter
                int previousSelectedPosition = selectedPosition;
                selectedPosition = position;
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fontList.size();
    }
}
