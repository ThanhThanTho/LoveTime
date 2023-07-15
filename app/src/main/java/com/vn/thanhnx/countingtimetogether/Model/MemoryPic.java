package com.vn.thanhnx.countingtimetogether.Model;

import android.net.Uri;

public class MemoryPic {
    private int PictureID;
    private Uri Picture;
    private String Status;

    public MemoryPic() {
    }

    public MemoryPic(int pictureID, Uri picture, String status) {
        PictureID = pictureID;
        Picture = picture;
        Status = status;
    }


    public int getPictureID() {
        return PictureID;
    }

    public void setPictureID(int pictureID) {
        PictureID = pictureID;
    }

    public Uri getPicture() {
        return Picture;
    }

    public void setPicture(Uri picture) {
        Picture = picture;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
