package com.unisoftwareproductions.uni.Handlers.ConnectionHandling;

import android.graphics.Bitmap;

/**
 * Created by Zachary on 18-Jul-16.
 */
public class DataChatPreview {

    String userUsername, chatRecentMessage, chatTimeStamp, adName, adAdID, userUserID, chatChatID;
    Bitmap adImageThumb, userUserThumb;

    public DataChatPreview() {   }

    public DataChatPreview(String userUsername, String chatRecentMessage, String chatTimeStamp, String adName,
                  String adAdID, String userUserID, String chatChatID, Bitmap adImageThumb, Bitmap userUserThumb) {

        this.userUsername = userUsername;
        this.chatRecentMessage = chatRecentMessage;
        this.chatTimeStamp = chatTimeStamp;
        this.adName = adName;
        this.adAdID = adAdID;
        this.userUserID = userUserID;
        this.chatChatID = chatChatID;
        this.adImageThumb = adImageThumb;
        this.userUserThumb = userUserThumb;
    }

    public String getuserUsername() { return userUsername; }
    public String getchatRecentMessage() { return chatRecentMessage; }
    public String getchatTimeStamp() { return chatTimeStamp; }
    public String getadName() { return adName; }
    public String getadAdID() { return adAdID; }
    public String getuserUserID() { return userUserID; }
    public String getchatChatID() { return chatChatID; }
    public Bitmap getadImageThumb() { return adImageThumb; }
    public Bitmap getuserUserThumb() { return userUserThumb; }

    public void setuserUsername(String userUsername) { this.userUsername = userUsername; }
    public void setchatRecentMessage(String chatRecentMessage) { this.chatRecentMessage = chatRecentMessage; }
    public void setchatTimeStamp(String chatTimeStamp) { this.chatTimeStamp = chatTimeStamp; }
    public void setadName(String adName) { this.adName = adName; }
    public void setadAdID(String adAdID) { this.adAdID = adAdID; }
    public void setuserUserID(String userUserID) { this.userUserID = userUserID; }
    public void setchatChatID(String chatChatID) { this.chatChatID = chatChatID; }
    public void setadImageThumb(Bitmap adImageThumb) { this.adImageThumb = adImageThumb; }
    public void setuserUserThumb(Bitmap userUserThumb) { this.userUserThumb = userUserThumb; }


    public void setChatPreview(String userUsername, String chatRecentMessage, String chatTimeStamp, String adName,
                               String adAdID, String userUserID, Bitmap adImageThumb, Bitmap userUserThumb) {
        setuserUsername(userUsername);
        setchatRecentMessage(chatRecentMessage);
        setchatTimeStamp(chatTimeStamp);
        setadName(adName);
        setadAdID(adAdID);
        setuserUserID(userUserID);
        setadImageThumb(adImageThumb);
        setuserUserThumb(userUserThumb);
    }

    /*

    /////////////////////////////

    public void getChatPreview() {
        getuserUsername();
        getchatRecentMessage();
        getchatTimeStamp();
        getadAdID();
        getuserUserID();
        getadImageThumb();
        getuserUserThumb();
    }

    */
}
