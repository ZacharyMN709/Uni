package com.unisoftwareproductions.uni.Handlers.ConnectionHandling;

/**
 * Created by Zachary on 18-Jul-16.
 */
public class DataChatMessage {

    String chatChatMessage, userUserID, chatTimeStamp, chatChatID;

    public DataChatMessage() {   }

    public DataChatMessage(String chatChatMessage, String userUserID, String chatTimeStamp, String chatChatID) {

        this.chatChatMessage = chatChatMessage;
        this.userUserID = userUserID;
        this.chatTimeStamp = chatTimeStamp;
        this.chatChatID = chatChatID;
    }

    public String getchatChatMessage() { return chatChatMessage; }
    public String getuserUserID() { return userUserID; }
    public String getchatTimeStamp() { return chatTimeStamp; }
    public String getchatChatID() { return chatChatID; }

    public void setchatChatMessage(String chatChatMessage) { this.chatChatMessage = chatChatMessage; }
    public void setuserUserID(String userUserID) { this.userUserID = userUserID; }
    public void setchatTimeStamp(String chatTimeStamp) { this.chatTimeStamp = chatTimeStamp; }
    public void setchatChatID(String chatChatID) { this.chatChatID = chatChatID; }


/*
    public void setChatMessage() {
        setchatChatMessage();
        setuserUserID();
        setchatTimeStamp();
    }

    public void getChatMessage() {
        getchatChatMessage();
        getuserUserID();
        getchatTimeStamp();
    }
    */
}
