package com.vn.thanhnx.countingtimetogether;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


import com.vn.thanhnx.countingtimetogether.ChangeFontActivity.FontSelectActivity;
import com.vn.thanhnx.countingtimetogether.Model.Font;
import com.vn.thanhnx.countingtimetogether.Model.Relationship;

import java.util.ArrayList;

public class General {

    public static final Uri convertDrawableToUri(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

    //make a text view editable
    public static void editTextView(TextView textView, Context context,
                                    String columnName, String currentRela, MyDBHandler myDBHandler){
        EditText editText = new EditText(context);
        editText.setText(textView.getText());
        editText.setSelection(textView.getText().length());
        editText.requestFocus();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(editText);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newText = editText.getText().toString();

                if (columnName.equals("RelationshipName")){
                    Relationship rela = myDBHandler.getRelaByName(newText);
                    if (rela == null){
                        myDBHandler.updateRelationship(textView.getText().toString(), columnName, newText);
                        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                        editor.putString("currentRelationship", newText);
                        editor.commit();
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(context, "Bạn đã có mối quan hệ "+newText+" rồi", Toast.LENGTH_SHORT).show();
                    }
                    textView.setText(newText);
                }
                else {
                    myDBHandler.updateRelationship(currentRela, columnName, newText);
                    textView.setText(newText);
                    dialog.dismiss();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    //get the font by font name
    public static Typeface getFont(String nameFont) {
        ArrayList<Font> fontArrayList = FontSelectActivity.getFontList();
        for (int i = 0; i < fontArrayList.size(); i++) {
            if (fontArrayList.get(i).getFontName().equals(nameFont)) {
                return fontArrayList.get(i).getFontType();
            }
        }
        return null;
    }
}
