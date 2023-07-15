package com.vn.thanhnx.countingtimetogether;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.vn.thanhnx.countingtimetogether.Model.MemoryPic;
import com.vn.thanhnx.countingtimetogether.Model.Relationship;
import com.vn.thanhnx.countingtimetogether.Model.Theme;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LoveTime.sqlite";
    private static final int DATABASE_VERSION = 1;
    //table relationship and it's column
    public static final String Table_Relationship = "Relationship";
    public static final String Column_RelationshipID = "RelationshipID";
    public static final String Column_RelationshipName = "RelationshipName";
    public static final String Column_FirstPersonName = "FirstPersonName";
    public static final String Column_FirstPic = "FirstPic";
    public static final String Column_SecondPersonName = "SecondPersonName";
    public static final String Column_SecondPic = "SecondPic";
    public static final String Column_StartDate = "StartDate";

    //table MemoryPicture and it's column
    public static final String Table_MemoryPicture = "MemoryPicture";
    public static final String Column_PictureID = "PictureID";
    //public static final String Column_RelationShipID = "RelationShipID"; foreign key
    public static final String Column_Picture = "Picture";
    public static final String Column_Status = "Status";

    //table Theme and it's column
    public static final String Table_Theme = "Theme";
    public static final String Column_ThemeID = "ThemeID";
    public static final String Column_Background = "Background";
    public static final String Column_TextColor = "TextColor";
    public static final String Column_FontType = "FontType";
    Context context;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableRelationship =
                "CREATE TABLE "+Table_Relationship+"(\n" +
                        Column_RelationshipID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                        Column_RelationshipName+" TEXT NOT NULL, \n" +
                        Column_ThemeID+" INTEGER, \n"+
                        Column_FirstPersonName+" TEXT, \n" +
                        Column_FirstPic+" TEXT, \n" +
                        Column_SecondPersonName+" TEXT, \n" +
                        Column_SecondPic+" TEXT, \n" +
                        Column_StartDate+" TEXT, \n" +
                        Column_FontType + " TEXT, \n" +
                        "FOREIGN KEY("+Column_ThemeID+") REFERENCES "+Table_Theme+"("+Column_ThemeID+")\n"+
                        ")";
        String createTableMemoryPicture =
                "CREATE TABLE "+Table_MemoryPicture+"(\n" +
                        Column_PictureID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                        Column_RelationshipID + " INTEGER NOT NULL, \n" +
                        Column_Picture + " TEXT NOT NULL, \n" +
                        Column_Status + " TEXT, \n"+
                        "FOREIGN KEY("+Column_RelationshipID+") REFERENCES " +Table_Relationship+"("+Column_RelationshipID+")\n"+
                        ")";
        String createTableTheme =
                "CREATE TABLE "+Table_Theme+"(\n" +
                        Column_ThemeID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                        Column_Background + " TEXT, \n" +
                        Column_TextColor + " TEXT \n" +
                        ")";
        db.execSQL(createTableTheme);
        db.execSQL(createTableRelationship);
        db.execSQL(createTableMemoryPicture);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTables(){

    }

    public void insertRelationship(Relationship relationship){
        String command = "INSERT INTO "+Table_Relationship+"(\n"+
                Column_RelationshipName+", \n"+
                Column_ThemeID+", \n"+
                Column_FirstPersonName+", \n"+
                Column_FirstPic+", \n"+
                Column_SecondPersonName+", \n"+
                Column_SecondPic+", \n"+
                Column_StartDate+", \n"+
                Column_FontType+") \n"+
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        this.getWritableDatabase().execSQL(command, new Object[]{relationship.getRelationshipName(),
                                                                 relationship.getThemeID(),
                                                                 relationship.getFirstPersonName(),
                                                                 relationship.getFirstPic().toString(),
                                                                 relationship.getSecondPersonName(),
                                                                 relationship.getSecondPic().toString(),
                                                                 relationship.getStartDate(),
                                                                 relationship.getFontType()});
    }

    public Relationship getRelaByName(String relaName){
        String command = "SELECT * FROM " + Table_Relationship + " WHERE " + Column_RelationshipName + " = ?";
        String[] selectionArgs = {String.valueOf(relaName)};
        Cursor cursor = this.getReadableDatabase().rawQuery(command, selectionArgs);
        if (cursor.moveToFirst()) {
            int RelationshipID = cursor.getInt(0);
            String RelationshipName = cursor.getString(1);
            int ThemeID = cursor.getInt(2);
            String FirstPersonName = cursor.getString(3);
            Uri FirstPic = cursor.getString(4).equals("")?null:Uri.parse(cursor.getString(4));
            String SecondPersonName = cursor.getString(5);
            Uri SecondPic = cursor.getString(6).equals("")?null:Uri.parse(cursor.getString(6));
            String StartDate = cursor.getString(7);
            String FontType = cursor.getString(8);
            return new Relationship(RelationshipID, RelationshipName, ThemeID, FirstPersonName, FirstPic, SecondPersonName, SecondPic, StartDate, FontType);
        }
        return null;
    }

    public void insertTheme(Theme theme){
        String command = "INSERT INTO "+Table_Theme+"(" +
                Column_Background+", " +
                Column_TextColor+") \n" +
                "VALUES (?, ?)";
        this.getWritableDatabase().execSQL(command, new Object[]{
                theme.getBackground().toString(),
                theme.getTextColor()
        });
    }

    public void insertMemory(int RelaID, MemoryPic memoryPic){
        String command = "INSERT INTO "+Table_MemoryPicture+"("+
                Column_RelationshipID+", \n"+
                Column_Picture+", \n"+
                Column_Status+") \n"+
                "VALUES (?, ?, ?)";
        this.getWritableDatabase().execSQL(command, new Object[]{
                RelaID,
                memoryPic.getPicture().toString(),
                memoryPic.getStatus()
        });
    }

    public Theme getThemeByID(int ID){
        String command = "SELECT * FROM " + Table_Theme + " WHERE " + Column_ThemeID + " = ?";
        String[] selectionArgs = {String.valueOf(ID)};
        Cursor cursor = this.getReadableDatabase().rawQuery(command, selectionArgs);
        if (cursor.moveToFirst()) {
            int themeID = cursor.getInt(0);
            Uri backGround = Uri.parse(cursor.getString(1));
            String textColor = cursor.getString(2);
            return new Theme(themeID, backGround, textColor);
        }
        return null;
    }

    public int getRecordCount(String TABLE_NAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public void updateRelationship(String relaName, String columnName, String value){
        SQLiteDatabase db = this.getReadableDatabase();
        String command = "UPDATE "+Table_Relationship+
                " SET "+columnName+" = '"+value+"'"+
                " WHERE "+Column_RelationshipName+" = '"+relaName+"'";
        db.execSQL(command);
        db.close();
    }

    public ArrayList<MemoryPic> getAllMemoryByRela(int relaID){
        ArrayList<MemoryPic> memoryPics = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String command = "SELECT * FROM "+Table_MemoryPicture+"\n"+
                         "WHERE "+Column_RelationshipID+" = ?";
        String[] selectionArgs = {String.valueOf(relaID)};
        Cursor cursor = db.rawQuery(command, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int PictureID = cursor.getInt(0);
                String Picture = cursor.getString(2);
                String Status = cursor.getString(3);
                memoryPics.add(new MemoryPic(PictureID, Uri.parse(Picture), Status));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return memoryPics;
    }

    public MemoryPic getMemoryPicByID(int ID){
        String command = "SELECT * FROM "+Table_MemoryPicture+"\n"+
                "WHERE "+Column_PictureID+" = ?";
        String[] selectionArgs = {String.valueOf(ID)};
        Cursor cursor = this.getReadableDatabase().rawQuery(command, selectionArgs);
        if (cursor.moveToFirst()) {
            Uri picture = Uri.parse(cursor.getString(2));
            String status = cursor.getString(3);
            return new MemoryPic(ID, picture, status);
        }
        return null;
    }
}
