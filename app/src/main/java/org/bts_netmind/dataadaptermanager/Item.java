package org.bts_netmind.dataadaptermanager;

public class Item
{
    private int mImage;
    private String mTitle;
    private String mBody;

    public Item(int imgRef, String aTitle, String aBody)
    {
        this.mImage = imgRef;
        this.mTitle = aTitle;
        this.mBody = aBody;
    }

    public int getmImageRef() { return mImage; }
    public String getmTitle() { return mTitle; }
    public String getmBody() { return mBody; }
}
