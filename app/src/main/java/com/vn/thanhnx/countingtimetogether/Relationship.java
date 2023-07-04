package com.vn.thanhnx.countingtimetogether;

import java.util.Date;

public class Relationship {
    private String RelaName;
    private int FirstPic;
    private int SecondPic;
    private Date StartDate;

    public Relationship() {
    }

    public Relationship(String relaName, int firstPic, int secondPic, Date startDate) {
        RelaName = relaName;
        FirstPic = firstPic;
        SecondPic = secondPic;
        StartDate = startDate;
    }

    public String getRelaName() {
        return RelaName;
    }

    public void setRelaName(String relaName) {
        RelaName = relaName;
    }

    public int getFirstPic() {
        return FirstPic;
    }

    public void setFirstPic(int firstPic) {
        FirstPic = firstPic;
    }

    public int getSecondPic() {
        return SecondPic;
    }

    public void setSecondPic(int secondPic) {
        SecondPic = secondPic;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }
}
