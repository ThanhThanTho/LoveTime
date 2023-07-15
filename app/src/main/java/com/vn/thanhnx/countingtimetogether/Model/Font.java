package com.vn.thanhnx.countingtimetogether.Model;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Font implements Parcelable {
    private String FontName;
    private Typeface FontType;

    public Font() {
    }

    public Font(String fontName, Typeface fontType) {
        FontName = fontName;
        FontType = fontType;
    }

    protected Font(Parcel in) {
        FontName = in.readString();
    }

    public static final Creator<Font> CREATOR = new Creator<Font>() {
        @Override
        public Font createFromParcel(Parcel in) {
            return new Font(in);
        }

        @Override
        public Font[] newArray(int size) {
            return new Font[size];
        }
    };

    public String getFontName() {
        return FontName;
    }

    public void setFontName(String fontName) {
        FontName = fontName;
    }

    public Typeface getFontType() {
        return FontType;
    }

    public void setFontType(Typeface fontType) {
        FontType = fontType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(FontName);
    }
}
