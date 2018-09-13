package com.unisoftwareproductions.uni.Handlers.ConnectionHandling;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Created by Zachary Koszegi on 9/3/2016.
 */
public class UniClient {

    //Base IP of the server
    String ip;

    //Username of currently active user
    String user;

    //Password of currently active user
    String pass;

    //Context from parent
    Context parentContext;

    //Simply sets up the UniClient, doesn't require much configuration
    public UniClient (String ServerAddress, String Username, String Password, Context con)
    {
        ip = ServerAddress; user = Username; pass = Password; parentContext = con;
    }

    //Runs a simple ping request to the server to test connection
    public boolean pingServer()
    {
        try
        {
            request req = new request(ip + "/ping", user, pass, parentContext, "Sending Ping".getBytes());
            String result = req.execute().get();
            if(result != null && result != "")
            {
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    //Runs a simple check to see if credentials are valid
    public String tryLogin()
    {
        try
        {
            request req = new request(ip + "/login", user, pass, parentContext, "Trying Login".getBytes());
            String result = req.execute().get();
            return result;
        }
        catch(Exception e) {
            return "Login Unsuccessful";
        }
    }

    //Gets username/password given email
    public boolean getUserPass()
    {
        return false;
    }

    //Change Password
    public String changePass(String pass)
    {
        return "";
    }

    //Get list of relevent users
    public String[] getUserList(String AdId)
    {
        return null;
    }

    //Rate user
    public String rateUser(Boolean good)
    {
        return "Not implemented";
    }

    //Sends a register request with the appropriate information
    //to the server
    public String register(String username, String password, String email, String institution, String field, String gender, String birthday, String mainPicture, String thumbPicture)
    {
        try
        {
            if(username.contains(",") || password.contains(",") || email.contains(","))
            {
                return "Invalid Character: ,";
            }
            String toSend = username + "," + password + "," + email + "," + institution + "," + field + "," + gender + "," + birthday + "," + mainPicture + "," + thumbPicture;
            request req = new request(ip + "/register", "Null", "Null", parentContext, toSend.getBytes());
            String result = req.execute().get();
            return result;
        }
        catch(Exception e)
        {
        }
        return "Registration Failed, Unknown Reason";
    }

    //Get a specefic User by ID
    public String[] getUser(String Id)
    {
        try {

            request req = new request(ip + "/getUser", user, pass, parentContext, Id.getBytes());
            String result = req.execute().get();
            if (result.contains(","))
            {
                return result.split(",");
            }
        } catch (Exception e) {

        }
        return null;
    }

    //Sends an Ad serially to the server, returns a String
    //that matches the Ad's ID
    public String postAd(String[] ad)
    {
        try
        {
            String toSend = "";
            for(String s:ad)
            {
                toSend += s + ",";
            }
            toSend = toSend.substring(0, toSend.length()-2);
            request req = new request(ip + "/postAd", user, pass, parentContext, toSend.getBytes());
            return req.execute().get();
        }
        catch(Exception e)
        {
        }
        return "Posting Failed";
    }

    //Sends a message given the User ID of the recepient and
    //the message. 500 character limit.
    public boolean sendMessage(String ID, String message)
    {
        return false;
    }

    //Fetches a list of AdIDs that match the search parameters
    public String[] searchDataBase(String search)
    {
        try
        {
            request req = new request(ip + "/searchDatabase", user, pass, parentContext, search.getBytes());
            String result = req.execute().get();
            if (result.contains(","))
            {
                return result.split(",");
            }
            return new String[]{result};
        }
        catch (Exception e)
        {
        }
        return null;
    }

    //Gets a specific Ad by ID
    public String[] getAd(String ID)
    {
        try {

            request req = new request(ip + "/getAd", user, pass, parentContext, ID.getBytes());
            String result = req.execute().get();
            if (result.contains(","))
            {
                return result.split(",");
            }
        } catch (Exception e) {

        }
        return null;
    }

    //Gets a specific Ad by ID
    public String[] getAdPreview(String ID)
    {
        try {

            request req = new request(ip + "/getAdPreview", user, pass, parentContext, ID.getBytes());
            String result = req.execute().get();
            if (result.contains(","))
            {
                return result.split(",");
            }
        } catch (Exception e) {

        }
        return null;
    }

    //Gets ads for given user
    public String[] getUserAds(String Id)
    {
        try {

            request req = new request(ip + "/getUserAds", user, pass, parentContext, Id.getBytes());
            String result = req.execute().get();
            if (result != null)
            {
                return result.split(",");
            }
        } catch (Exception e) {

        }
        return null;
    }


    //Gets recent ads
    public String[] getRecentAds()
    {
        try {

            request req = new request(ip + "/getRecentAds", user, pass, parentContext, "".getBytes());
            String result = req.execute().get();
            if (result != null)
            {
                return result.split(",");
            }
        } catch (Exception e) {

        }
        return null;
    }

    //Gets a list of Ad previews
    public String[][] getAdPreviews(String[] Ids)
    {
        try
        {
            String toFetch = "";
            for(String s: Ids)
            {
                toFetch += s + ",";
            }
            toFetch = toFetch.substring(0, toFetch.length() - 1);

            Log.e("to server: ", toFetch);
            request req = new request(ip + "/getAdPreviews", user, pass, parentContext, toFetch.getBytes());
            String response = req.execute().get();
            Log.e("getAds: ", "response length:" + String.valueOf(response.length()));
            if (response != null)
            {
                Log.e("getAds: ", "Response contains: $_$");
                String[] results = response.split("\\$_\\$");
                Log.e("getAds: ", "results length:" + String.valueOf(results.length));
                String[][] finalResult = new String[results.length][];
                Log.e("getAds: ", "finalResult length:" + String.valueOf(finalResult.length));
                int i = 0;
                for(String s: results)
                {
                    finalResult[i] = s.split(",");
                    i++;
                }
                return finalResult;
            }
            else
            {
                Log.e("getAds: ", "Does not contain $_$");
                return new String[][]{response.split(",")};
            }
        }
        catch (Exception e)
        {
        }
        return null;
    }

    //Gets a list of Ads
    public String[][] getAds(String[] Ids)
    {
        try
        {
            String toFetch = "";
            for(String s: Ids)
            {
                toFetch += s + ",";
            }
            toFetch = toFetch.substring(0, toFetch.length() - 1);

            Log.e("to server: ", toFetch);
            request req = new request(ip + "/getAds", user, pass, parentContext, toFetch.getBytes());
            String response = req.execute().get();
            Log.e("getAds: ", "response length:" + String.valueOf(response.length()));
            if (response != null)
            {
                Log.e("getAds: ", "Response contains: $_$");
                String[] results = response.split("\\$_\\$");
                Log.e("getAds: ", "results length:" + String.valueOf(results.length));
                String[][] finalResult = new String[results.length][];
                Log.e("getAds: ", "finalResult length:" + String.valueOf(finalResult.length));
                int i = 0;
                for(String s: results)
                {
                    finalResult[i] = s.split(",");
                    i++;
                }
                return finalResult;
            }
            else
            {
                Log.e("getAds: ", "Does not contain $_$");
                return new String[][]{response.split(",")};
            }
        }
        catch (Exception e)
        {
        }
        return null;
    }

    //Gets favorites list
    public String[] getFavorites()
    {
        return null;
    }

    //Updates favorites
    public boolean updateFavorites(String[] favorites)
    {
        return false;
    }

    //Deletes an ad given an ID
    public boolean deleteAd(String Id)
    {
        return false;
    }

    //Deletes all ads by this user
    public boolean deleteAllAds()
    {
        return false;
    }

    //Sends feedback to the server
    public boolean feedback(String feedback)
    {
        return false;
    }

    //Get all unread messages from the server
    public String[] getMessages(String AdId)
    {
        return null;
    }

    //Deletes a chat
    public boolean deleteChat(String ChatId)
    {
        return false;
    }

    //Updates the profile
    public boolean updateProfile(String[] newProfile)
    {
        return false;
    }

    //Gets Chat preview given parameters
    public String[] getChatPreview(String UserIdFrom, String AdId, String ChatId)
    {
        return null;
    }

    //Get all messages from the server
    public String[] getAllMessages()
    {
        return null;
    }

    //Base for all requests
    class request extends AsyncTask<Void, Void, String>
    {
        //Base ip
        String baseIP;

        //Username
        String username;

        //Password
        String password;

        //Context from parent
        Context context;

        //Data to send
        byte[] data;

        //Constructor
        public request(String ip, String userName,
                       String passWord, Context con,
                       byte[] tosend)
        {
            baseIP = ip; username = userName; password = passWord; context = con; data = tosend;
        }

        @Override
        protected String doInBackground(Void... args)
        {
            if(Looper.myLooper() == null)
            {
                Looper.prepare();
            }
            try
            {
                //Constructing the basic request and body
                URL url = new URL(baseIP);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Username", username);
                conn.setRequestProperty("Password", password);

                //Setting up and sending the data
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(data.length);
                BufferedOutputStream outstream = new BufferedOutputStream(conn.getOutputStream());
                outstream.write(data);
                outstream.close();

                //Fetching, and splitting the data
                BufferedReader instream = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String data = "";
                String line = "";
                while((line = instream.readLine()) != null)
                {
                    data += line;
                }
                instream.close();

                conn.disconnect();
                return data;
            }
            catch (Exception e)
            {
                return "Error: "+e.getLocalizedMessage();
            }
        }

    }





}

