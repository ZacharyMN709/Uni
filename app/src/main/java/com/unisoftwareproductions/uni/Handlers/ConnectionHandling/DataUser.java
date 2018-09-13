package com.unisoftwareproductions.uni.Handlers.ConnectionHandling;

import android.graphics.Bitmap;

import com.unisoftwareproductions.uni.Handlers.ImageHandler;

/**
 * Created by Zachary on 18-Jul-16.
 */
public class DataUser {

    String userUsername, userPassword, userEmail, userRating, userItemsSold, userPSE, userStudying,
            userUserID, userGender, userBirthDate, userDateJoined, userMainString, userThumbString;
    Bitmap userUserMain, userUserThumb;

    public DataUser() {   }

    public DataUser(String userUsername, String userPassword, String userEmail, String userRating,
                    String userItemsSold, String userPSE,
                    String userStudying, String userUserID, String userGender, String userBirthDate,
                    Bitmap userUserMain, Bitmap userUserThumb) {

        this.userUsername = userUsername;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userRating = userRating;
        this.userItemsSold = userItemsSold;
        this.userPSE = userPSE;
        this.userStudying = userStudying;
        this.userUserID = userUserID;
        this.userGender = userGender;
        this.userBirthDate = userBirthDate;
        this.userUserMain = userUserMain;
        this.userUserThumb = userUserThumb;
    }

    public DataUser(String[] user) {
        this.userUserID = user[0];
        this.userDateJoined = user[2];
        this.userUsername = user[3];
        this.userPassword = user[4];
        this.userEmail = user[5];
        this.userRating = user[6];
        this.userItemsSold = user[7];
        this.userPSE = user[8];
        this.userStudying = user[9];
        this.userGender = user[10];
        this.userBirthDate = user[11];
        this.userMainString = user[12];
        this.userThumbString = user[13];
    }

    public String getuserUsername() { return userUsername; }
    public String getuserPassword() { return userPassword; }
    public String getuserEmail() { return userEmail; }
    public String getuserRating() { return userRating; }
    public String getuserItemsSold() { return userItemsSold; }
    public String getuserPSE() { return userPSE; }
    public String getuserStudying() { return userStudying; }
    public String getuserUserID() { return userUserID; }
    public String getuserGender() { return userGender; }
    public String getuserBirthDate() { return userBirthDate; }
    public String getUserDateJoined() { return userDateJoined; }
    public String getUserMainString() {
        if (userMainString == null) { this.userMainString = ImageHandler.ConvertBitmaptoString(userUserMain); }
        return userMainString; }
    public String getUserThumbString() {
        if (userThumbString == null) { this.userThumbString = ImageHandler.ConvertBitmaptoString(userUserThumb); }
        return userThumbString; }
    public Bitmap getuserUserMain() {
        if (userUserMain == null) { this.userUserMain = ImageHandler.ReassembleBitmapfromString(userMainString, ImageHandler.image_profile_main_px()); }
        return userUserMain; }
    public Bitmap getuserUserThumb() {
        if (userUserThumb == null) { this.userUserThumb = ImageHandler.ReassembleBitmapfromString(userThumbString, ImageHandler.image_profile_thumb_px()); }
        return userUserThumb; }


    public void setuserUsername(String userUsername) { this.userUsername = userUsername; }
    public void setuserPassword(String userPassword) { this.userPassword = userPassword; }
    public void setuserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setuserRating(String userRating) { this.userRating = userRating; }
    public void setuserItemsSold(String userItemsSold) { this.userItemsSold = userItemsSold; }
    public void setuserPSE(String userPSE) { this.userPSE = userPSE; }
    public void setuserStudying(String userStudying) { this.userStudying = userStudying; }
    public void setuserUserID(String userUserID) { this.userUserID = userUserID; }
    public void setuserGender(String userGender) { this.userGender = userGender; }
    public void setuserBirthDate(String userBirthDate) { this.userBirthDate = userBirthDate; }
    public void setUserDateJoined(String userDateJoined) { this.userDateJoined = userDateJoined; }
    public void setUserMainString(String userMainString) {
        this.userMainString = userMainString;
        this.userUserMain = ImageHandler.ReassembleBitmapfromString(userMainString, ImageHandler.image_profile_main_px()); }
    public void setUserThumbString(String userThumbString) {
        this.userThumbString = userThumbString;
        this.userUserThumb = ImageHandler.ReassembleBitmapfromString(userThumbString, ImageHandler.image_profile_thumb_px()); }
    public void setuserUserMain(Bitmap userUserMain) {
        this.userUserMain = userUserMain;
        this.userMainString = ImageHandler.ConvertBitmaptoString(userUserMain); }
    public void setuserUserThumb(Bitmap userUserThumb) {
        this.userUserThumb = userUserThumb;
        this.userThumbString = ImageHandler.ConvertBitmaptoString(userUserThumb); }


    public void setUserProfile(String userUsername, String userPassword, String userEmail,
//                               String userRating, String userItemsSold,
                               String userPSE, String userStudying, String userGender, String userBirthDate,
                               Bitmap userUserMain, Bitmap userUserThumb) {
        setuserUsername(userUsername);
        setuserPassword(userPassword);
        setuserEmail(userEmail);
//        setuserRating(userRating);
//        setuserItemsSold(userItemsSold);
        setuserPSE(userPSE);
        setuserStudying(userStudying);
        setuserGender(userGender);
        setuserBirthDate(userBirthDate);
        setuserUserMain(userUserMain);
        setuserUserThumb(userUserThumb);
    }

    /**
     public void setUserALL(String userUsername, String userPassword, String userEmail, String userRating,
     String userItemsSold, String userPSE,
     String userStudying, String userUserID, String userGender, String userBirthDate,
     Bitmap userUserMain, Bitmap userUserThumb, String userUserMainString, String userUserThumbString) {
     setuserUsername(userUsername);
     setuserPassword(userPassword);
     setuserEmail(userEmail);
     setuserRating(userRating);
     setuserItemsSold(userItemsSold);
     setuserPSE(userPSE);
     setuserStudying(userStudying);
     setuserUserID(userUserID);
     setuserGender(userGender);
     setuserBirthDate(userBirthDate);
     setuserUserMain(userUserMain);
     setuserUserThumb(userUserThumb);
     setuserUserMainString(userUserMainString);
     setuserUserThumbString(userUserThumbString);
     }
     */

}
