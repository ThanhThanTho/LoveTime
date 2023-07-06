package com.vn.thanhnx.countingtimetogether;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vn.thanhnx.countingtimetogether.Model.Font;

import java.util.ArrayList;

public class FontSelectActivity extends AppCompatActivity {
    private static final int STATUS_SEND_FONT_DONE = 1;
    private static final int STATUS_SEND_FONT_NOT_DONE = 0;
    private RecyclerView fontRecycler;
    private Button cancelButton;
    private Button okButton;
    private static ArrayList<Font> fontList;
    private TextView previewTextView;
    private TextView fontNameVar;

    public static ArrayList<Font> getFontList() {
        return fontList;
    }

    private void bindDataToFontRecycler(){
        fontRecycler.setLayoutManager(new LinearLayoutManager(this));
        fontRecycler.setAdapter(new FontRecyclerAdapter(fontList, this, fontRecycler, previewTextView, fontNameVar));
    }
    public void getFontData(){
        fontList = new ArrayList<>();
        fontList.add(new Font("AlexBrush-Regular", Typeface.createFromAsset(getAssets(),
                              "AlexBrush-Regular.ttf")));
        fontList.add(new Font("Allura-Regular", Typeface.createFromAsset(getAssets(),
                              "Allura-Regular.ttf")));
        fontList.add(new Font("Beaurivage-Regular", Typeface.createFromAsset(getAssets(),
                              "BeauRivage-Regular.ttf")));
        fontList.add(new Font("BlackOpsOne-Regular", Typeface.createFromAsset(getAssets(),
                              "BlackOpsOne-Regular.ttf")));
        fontList.add(new Font("BungeeShade-Regular", Typeface.createFromAsset(getAssets(),
                              "BungeeShade-Regular.ttf")));
        fontList.add(new Font("ComforterBrush-Regular", Typeface.createFromAsset(getAssets(),
                              "ComforterBrush-Regular.ttf")));
        fontList.add(new Font("DancingScript-VariableFont", Typeface.createFromAsset(getAssets(),
                              "DancingScript-VariableFont_wght.ttf")));
        fontList.add(new Font("DenkOne-Regular", Typeface.createFromAsset(getAssets(),
                              "DenkOne-Regular.ttf")));
        fontList.add(new Font("GreatVibes-Regular", Typeface.createFromAsset(getAssets(),
                              "GreatVibes-Regular.ttf")));
        fontList.add(new Font("GrenzeGotisch_VariableFont", Typeface.createFromAsset(getAssets(),
                              "GrenzeGotisch-VariableFont_wght.ttf")));
        fontList.add(new Font("Mansalva-Regular", Typeface.createFromAsset(getAssets(),
                              "Mansalva-Regular.ttf")));
        fontList.add(new Font("MeowScript-Regular", Typeface.createFromAsset(getAssets(),
                              "MeowScript-Regular.ttf")));
        fontList.add(new Font("Pacifico-Regular", Typeface.createFromAsset(getAssets(),
                              "Pacifico-Regular.ttf")));
        fontList.add(new Font("QwitcherGrypen-Bold", Typeface.createFromAsset(getAssets(),
                              "QwitcherGrypen-Bold.ttf")));
        fontList.add(new Font("Srisakdi-Bold", Typeface.createFromAsset(getAssets(),
                              "Srisakdi-Bold.ttf")));
        fontList.add(new Font("Srisakdi-Regular", Typeface.createFromAsset(getAssets(),
                              "Srisakdi-Regular.ttf")));
        fontList.add(new Font("VT323-Regular", Typeface.createFromAsset(getAssets(),
                              "VT323-Regular.ttf")));
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
        getFontData();
        bindDataToFontRecycler();
        onReceiveIntent();
    }
}