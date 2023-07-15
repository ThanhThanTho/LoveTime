package com.vn.thanhnx.countingtimetogether.ChangeFontActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vn.thanhnx.countingtimetogether.ChangeFontActivity.FontRecyclerAdapter;
import com.vn.thanhnx.countingtimetogether.MainActivity;
import com.vn.thanhnx.countingtimetogether.Model.Font;
import com.vn.thanhnx.countingtimetogether.R;

import java.util.ArrayList;

public class FontSelectActivity extends AppCompatActivity {
    private static final int STATUS_SEND_FONT_DONE = 1;
    private static final int STATUS_SEND_FONT_NOT_DONE = 0;
    private RecyclerView fontRecycler;
    private Button cancelButton;
    private Button okButton;
    //private static ArrayList<Font> fontList = MainActivity.fontList;
    private TextView previewTextView;
    private TextView fontNameVar;

    public static ArrayList<Font> getFontList() {
        return MainActivity.fontList;
    }

    private void bindDataToFontRecycler(){
        fontRecycler.setLayoutManager(new LinearLayoutManager(this));
        fontRecycler.setAdapter(new FontRecyclerAdapter(MainActivity.fontList, this, fontRecycler, previewTextView, fontNameVar));
    }

    private void bindingView(){
        cancelButton = findViewById(R.id.cancelButton);
        okButton = findViewById(R.id.okButton);
        fontRecycler = findViewById(R.id.recyclerViewFont);
        previewTextView = findViewById(R.id.previewTextView);
        fontNameVar = findViewById(R.id.fontNameVar);
    }

    private void bindingAction(){
        cancelButton.setOnClickListener(this::cancelClick);
        okButton.setOnClickListener(this::okClick);
    }

    private void okClick(View view) {
        Intent i = new Intent(this, MainActivity.class);
        String nameFont = fontNameVar.getText().toString();
        if (!nameFont.equals("")) {
            i.putExtra("fontFamily", nameFont);
            setResult(STATUS_SEND_FONT_DONE, i);
            finish();
        }
        else {
            setResult(STATUS_SEND_FONT_NOT_DONE, i);
            finish();
        }
    }

    private void cancelClick(View view) {
        Intent i = new Intent(this, MainActivity.class);
        setResult(STATUS_SEND_FONT_NOT_DONE, i);
        finish();
    }

    private void onReceiveIntent(){
        Intent i = getIntent();
        String previewString = i.getStringExtra("previewString");
        previewTextView.setText(previewString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_select);
        bindingView();
        bindingAction();
        bindDataToFontRecycler();
        onReceiveIntent();
    }
}