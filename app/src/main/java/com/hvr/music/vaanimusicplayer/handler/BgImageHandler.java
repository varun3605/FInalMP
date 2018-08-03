package com.hvr.music.vaanimusicplayer.handler;

public class BgImageHandler
{
    private static BgImageHandler sBgImageHandler;

    public String getImageUri() {
        return mImageUri;
    }

    public void setImageUri(String imageUri) {
        mImageUri = imageUri;
    }

    private String mImageUri;

    public static BgImageHandler getInstance()
    {
        if(sBgImageHandler == null)
        {
            sBgImageHandler = new BgImageHandler();
        }
        return sBgImageHandler;
    }

    private BgImageHandler()
    {

    }


}
