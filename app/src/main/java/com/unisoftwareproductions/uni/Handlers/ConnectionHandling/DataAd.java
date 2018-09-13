package com.unisoftwareproductions.uni.Handlers.ConnectionHandling;

import android.graphics.Bitmap;

import com.unisoftwareproductions.uni.Handlers.ImageHandler;

/**
 * Created by Zachary on 18-Jul-16.
 */
public class DataAd {

    String adName, adPrice, userUsername, userRating, adAdID, userUserID, adCondition, adDescription, adTags,
        AdThumbString, UserThumbString, ImageAString, ImageBString, ImageCString, ImageDString, ImageEString;
    Bitmap adImageThumb, userUserThumb, adImageBitmapA, adImageBitmapB, adImageBitmapC, adImageBitmapD, adImageBitmapE;

    public DataAd() {   }

    public DataAd(String adName, String adPrice, String userUsername, String userRating,
                  String adAdID, String userUserID, String adCondition, String adDescription, String adTags,
                  Bitmap adImageThumb, Bitmap userUserThumb,
                  Bitmap adImageBitmapA, Bitmap adImageBitmapB, Bitmap adImageBitmapC, Bitmap adImageBitmapD, Bitmap adImageBitmapE) {

        this.adName = adName;
        this.adPrice = adPrice;
        this.userUsername = userUsername;
        this.userRating = userRating;
        this.adAdID = adAdID;
        this.userUserID = userUserID;
        this.adCondition = adCondition;
        this.adDescription = adDescription;
        this.adTags = adTags;
        this.adImageThumb = adImageThumb;
        this.userUserThumb = userUserThumb;
        this.adImageBitmapA = adImageBitmapA;
        this.adImageBitmapB = adImageBitmapB;
        this.adImageBitmapC = adImageBitmapC;
        this.adImageBitmapD = adImageBitmapD;
        this.adImageBitmapE = adImageBitmapE;
    }

    public DataAd(String[] ad, boolean adpreview) {


        this.adAdID = ad[0];

        this.userUserID = ad[2];
        this.UserThumbString = ad[3];
        this.userUsername = ad[4];
        this.userRating = ad[5];

        String AdTitle = ad[6];
        int endIndex = AdTitle.lastIndexOf("*");
        String toBeReplaced = AdTitle.substring(0, endIndex + 2);
        String holder = AdTitle.replace(toBeReplaced, "");
        this.adName = holder;
        this.adPrice = ad[7];
        this.adDescription = ad[8];
        this.adCondition = ad[9];
        this.AdThumbString = ad[10];
        if (!adpreview) {
            this.ImageAString = ad[11];
            this.ImageBString = ad[12];
            this.ImageCString = ad[13];
            this.ImageDString = ad[14];
            this.ImageEString = ad[15];
        }
    }

    public DataAd(String adName, String adPrice,
                  String userUsername, String userRating,
                  String adAdID, String userUserID,
                  Bitmap adImageThumb, Bitmap userUserThumb) {

        this.adName = adName;
        this.adPrice = adPrice;
        this.userUsername = userUsername;
        this.userRating = userRating;
        this.adAdID = adAdID;
        this.userUserID = userUserID;
        this.adImageThumb = adImageThumb;
        this.userUserThumb = userUserThumb;
    }

    public String getadName() { return adName; }
    public String getadPrice() { return adPrice; }
    public String getuserUsername() { return userUsername; }
    public String getuserRating() { return userRating; }
    public String getadAdID() { return adAdID; }
    public String getuserUserID() { return userUserID; }
    public String getadCondition() { return adCondition; }
    public String getadDescription() { return adDescription; }
    public String getadTags() { return adTags; }
    public String getAdThumbString() {
        if (AdThumbString == null) { AdThumbString = ImageHandler.ConvertBitmaptoString(adImageThumb);}
        return AdThumbString; }
    public String getUserThumbString() {
        if (UserThumbString == null) { UserThumbString = ImageHandler.ConvertBitmaptoString(userUserThumb);}
        return UserThumbString; }
    public String getImageAString() {
        if (ImageAString == null) { ImageAString = ImageHandler.ConvertBitmaptoString(adImageBitmapA);}
        return ImageAString; }
    public String getImageBString() {
        if (ImageBString == null) { ImageBString = ImageHandler.ConvertBitmaptoString(adImageBitmapB);}
        return ImageBString; }
    public String getImageCString() {
        if (ImageCString == null) { ImageCString = ImageHandler.ConvertBitmaptoString(adImageBitmapC);}
        return ImageCString; }
    public String getImageDString() {
        if (ImageDString == null) { ImageDString = ImageHandler.ConvertBitmaptoString(adImageBitmapD);}
        return ImageDString; }
    public String getImageEString() {
        if (ImageEString == null) { ImageEString = ImageHandler.ConvertBitmaptoString(adImageBitmapE);}
        return ImageEString; }
    public Bitmap getadImageThumb() {
        if (adImageThumb == null) { adImageThumb = ImageHandler.ReassembleBitmapfromString(AdThumbString, ImageHandler.image_ad_thumb_px());}
        return adImageThumb; }
    public Bitmap getuserUserThumb() {
        if (userUserThumb == null) { userUserThumb = ImageHandler.ReassembleBitmapfromString(UserThumbString, ImageHandler.image_profile_thumb_px());}
        return userUserThumb; }
    public Bitmap getadImageBitmapA() {
        if (adImageBitmapA == null) { adImageBitmapA = ImageHandler.ReassembleBitmapfromString(ImageAString, ImageHandler.image_ad_main_px());}
        return adImageBitmapA; }
    public Bitmap getadImageBitmapB() {
        if (adImageBitmapB == null) { adImageBitmapB = ImageHandler.ReassembleBitmapfromString(ImageBString, ImageHandler.image_ad_main_px());}
        return adImageBitmapB; }
    public Bitmap getadImageBitmapC() {
        if (adImageBitmapC == null) { adImageBitmapC = ImageHandler.ReassembleBitmapfromString(ImageCString, ImageHandler.image_ad_main_px());}
        return adImageBitmapC; }
    public Bitmap getadImageBitmapD() {
        if (adImageBitmapD == null) { adImageBitmapD = ImageHandler.ReassembleBitmapfromString(ImageDString, ImageHandler.image_ad_main_px());}
        return adImageBitmapD; }
    public Bitmap getadImageBitmapE() {
        if (adImageBitmapE == null) { adImageBitmapE = ImageHandler.ReassembleBitmapfromString(ImageEString, ImageHandler.image_ad_main_px());}
        return adImageBitmapE; }



    public void setadName(String adName) { this.adName = adName; }
    public void setadPrice(String adPrice) { this.adPrice = adPrice; }
    public void setuserUsername(String userUsername) { this.userUsername = userUsername; }
    public void setuserRating(String userRating) { this.userRating = userRating; }
    public void setadAdID(String adAdID) { this.adAdID = adAdID; }
    public void setuserUserID(String userUserID) { this.userUserID = userUserID; }
    public void setadCondition(String adCondition) { this.adCondition = adCondition; }
    public void setadDescription(String adDescription) { this.adDescription = adDescription; }
    public void setadTags(String adTags) { this.adTags = adTags; }
    public void setAdThumbString(String AdThumbString) {
        this.AdThumbString = AdThumbString;
        this.adImageThumb = ImageHandler.ReassembleBitmapfromString(AdThumbString, ImageHandler.image_ad_thumb_px());}
    public void setUserThumbString(String UserThumbString) {
        this.UserThumbString = UserThumbString;
        this.userUserThumb = ImageHandler.ReassembleBitmapfromString(UserThumbString, ImageHandler.image_profile_thumb_px());}
    public void setImageAString(String ImageAString) {
        this.ImageAString = ImageAString;
        this.adImageBitmapA = ImageHandler.ReassembleBitmapfromString(ImageAString, ImageHandler.image_ad_main_px());}
    public void setImageBString(String ImageBString) {
        this.ImageBString = ImageBString;
        this.adImageBitmapB = ImageHandler.ReassembleBitmapfromString(ImageBString, ImageHandler.image_ad_main_px());}
    public void setImageCString(String ImageCString) {
        this.ImageCString = ImageCString;
        this.adImageBitmapC = ImageHandler.ReassembleBitmapfromString(ImageCString, ImageHandler.image_ad_main_px());}
    public void setImageDString(String ImageDString) {
        this.ImageDString = ImageDString;
        this.adImageBitmapD = ImageHandler.ReassembleBitmapfromString(ImageDString, ImageHandler.image_ad_main_px());}
    public void setImageEString(String ImageEString) {
        this.ImageEString = ImageEString;
        this.adImageBitmapE = ImageHandler.ReassembleBitmapfromString(ImageEString, ImageHandler.image_ad_main_px());}
    public void setadImageThumb(Bitmap adImageThumb) {
        this.adImageThumb = adImageThumb;
        this.AdThumbString = ImageHandler.ConvertBitmaptoString(adImageThumb);}
    public void setuserUserThumb(Bitmap userUserThumb) {
        this.userUserThumb = userUserThumb;
        this.UserThumbString = ImageHandler.ConvertBitmaptoString(userUserThumb);}
    public void setadImageA(Bitmap adImageBitmapA) {
        this.adImageBitmapA = adImageBitmapA;
        this.ImageAString = ImageHandler.ConvertBitmaptoString(adImageBitmapA);}
    public void setadImageB(Bitmap adImageBitmapB) {
        this.adImageBitmapB = adImageBitmapB;
        this.ImageBString = ImageHandler.ConvertBitmaptoString(adImageBitmapB);}
    public void setadImageC(Bitmap adImageBitmapC) {
        this.adImageBitmapC = adImageBitmapC;
        this.ImageCString = ImageHandler.ConvertBitmaptoString(adImageBitmapC);}
    public void setadImageD(Bitmap adImageBitmapD) {
        this.adImageBitmapD = adImageBitmapD;
        this.ImageDString = ImageHandler.ConvertBitmaptoString(adImageBitmapD);}
    public void setadImageE(Bitmap adImageBitmapE) {
        this.adImageBitmapE = adImageBitmapE;
        this.ImageEString = ImageHandler.ConvertBitmaptoString(adImageBitmapE);}



    public void setAdPreview(String adName, String adPrice, String userUsername, String userRating, String adAdID,
                             String userUserID, Bitmap adImageThumb, Bitmap userUserThumb) {
        setadName(adName);
        setadPrice(adPrice);
        setuserUsername(userUsername);
        setuserRating(userRating);
        setadAdID(adAdID);
        setuserUserID(userUserID);
        setadImageThumb(adImageThumb);
        setuserUserThumb(userUserThumb);
    }


    public void setAdCreate(String adName, String adPrice,
                            String userUsername, String userUserID,
                            Bitmap adImageThumb, Bitmap userUserThumb,
                            String adCondition, String adDescription, String adTags,
                            Bitmap adImageA, Bitmap adImageB, Bitmap adImageC, Bitmap adImageD, Bitmap adImageE) {
        setadName(adName);
        setadPrice(adPrice);
        setuserUsername(userUsername);
        setuserUserID(userUserID);
        setadImageThumb(adImageThumb);
        setuserUserThumb(userUserThumb);
        setadCondition(adCondition);
        setadDescription(adDescription);
        setadTags(adTags);
        setadImageA(adImageA);
        setadImageB(adImageB);
        setadImageC(adImageC);
        setadImageD(adImageD);
        setadImageE(adImageE);
    }

    /*
    /////////////////////////////

    public void getAdPreview() {
        getadName();
        getadPrice();
        getuserUsername();
        getuserRating();
        getadAdID();
        getuserUserID();
        getadImageThumb();
        getuserUserThumb();
    }

    public void getAdFull() {
        getadCondition();
        getadDescription();
        getadImageA();
        getadImageB();
        getadImageC();
        getadImageD();
        getadImageE();
    }
    */


    /***
     public void setAdALL() {
     setadName(adName);
     setadPrice(adPrice);
     setuserUsername(userUsername);
     setuserRating(userRating);
     setadAdID(adAdID);
     setuserUserID(userUserID);
     setadImageThumb(adImageThumb);
     setadImageStringThumb(adImageStringThumb);
     setuserUserThumb(userUserThumb);
     setuserImageStringThumb(userImageStringThumb);
     setadCondition(adCondition);
     setadDescription(adDescription);
     setadImageA(adImageBitmapA);
     setadImageB(adImageBitmapB);
     setadImageC(adImageBitmapC);
     setadImageD(adImageBitmapD);
     setadImageE(adImageBitmapE);
     setadImageStringA(adImageStringA);
     setadImageStringB(adImageStringB);
     setadImageStringC(adImageStringC);
     setadImageStringD(adImageStringD);
     setadImageStringE(adImageStringE);
     }
     ***/
}
