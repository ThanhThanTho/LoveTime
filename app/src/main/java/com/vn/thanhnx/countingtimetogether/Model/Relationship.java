package com.vn.thanhnx.countingtimetogether.Model;

import android.net.Uri;


public class Relationship {

    private int RelationshipID;
    private String RelationshipName;
    private int ThemeID;
    private String FirstPersonName;
    private Uri FirstPic;
    private String SecondPersonName;
    private Uri SecondPic;
    private String StartDate;
    private String FontType;


    public Relationship() {
    }

    public Relationship(int relationshipID, String relationshipName, int ThemeID, String firstPersonName, Uri firstPic, String secondPersonName, Uri secondPic, String startDate, String fontType) {
        RelationshipID = relationshipID;
        RelationshipName = relationshipName;
        this.ThemeID = ThemeID;
        FirstPersonName = firstPersonName;
        FirstPic = firstPic;
        SecondPersonName = secondPersonName;
        SecondPic = secondPic;
        StartDate = startDate;
        this.FontType = fontType;
    }

    public String getFontType() {
        return FontType;
    }

    public void setFontType(String fontType) {
        FontType = fontType;
    }

    public int getThemeID() {
        return ThemeID;
    }

    public void setThemeID(int themeID) {
        ThemeID = themeID;
    }

    public int getRelationshipID() {
        return RelationshipID;
    }

    public void setRelationshipID(int relationshipID) {
        RelationshipID = relationshipID;
    }

    public String getRelationshipName() {
        return RelationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        RelationshipName = relationshipName;
    }

    public String getFirstPersonName() {
        return FirstPersonName;
    }

    public void setFirstPersonName(String firstPersonName) {
        FirstPersonName = firstPersonName;
    }

    public Uri getFirstPic() {
        return FirstPic;
    }

    public void setFirstPic(Uri firstPic) {
        FirstPic = firstPic;
    }

    public String getSecondPersonName() {
        return SecondPersonName;
    }

    public void setSecondPersonName(String secondPersonName) {
        SecondPersonName = secondPersonName;
    }

    public Uri getSecondPic() {
        return SecondPic;
    }

    public void setSecondPic(Uri secondPic) {
        SecondPic = secondPic;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }
}
