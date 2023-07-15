package com.vn.thanhnx.countingtimetogether.Model;

import android.net.Uri;

public class Theme {
    private int ThemeID;
    private Uri Background;
    private String TextColor;

    public Theme() {
    }

    public Theme(int themeID, Uri background, String textColor) {
        ThemeID = themeID;
        Background = background;
        TextColor = textColor;
    }

    public Theme(Uri backGround, String textColor) {
        this.Background = backGround;
        this.TextColor = textColor;
    }

    public int getThemeID() {
        return ThemeID;
    }

    public void setThemeID(int themeID) {
        ThemeID = themeID;
    }

    public Uri getBackground() {
        return Background;
    }

    public void setBackground(Uri background) {
        Background = background;
    }

    public String getTextColor() {
        return TextColor;
    }

    public void setTextColor(String textColor) {
        TextColor = textColor;
    }
}
