package com.unisoftwareproductions.uni.Main.Settings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataUser;
import com.unisoftwareproductions.uni.Handlers.ImageHandler;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Main.Drawer_Main;

import com.unisoftwareproductions.uni.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zachary on 20-Jul-16.
 */
public class ChangeProfilePicture extends AppCompatActivity {

    Context context;
    CircleImageView ivProfilePicture, ivProfilePictureSmall;
    Bitmap ProfilePictureLarge, ProfilePictureSmall;
    String PPLString, PPSString;
    DataUser dataUser;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App = new App(this);
        setContentView(R.layout.changeprofilepicture);
        context = this;
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_changeprofilepicture);

        dataUser = new DataUser(App.getClient().getUser(App.getUSRID()));
        ivProfilePicture = (CircleImageView) findViewById(R.id.profilepicture);
        ivProfilePictureSmall = (CircleImageView) findViewById(R.id.profilepicturethumb);
        ProfilePictureLarge = dataUser.getuserUserMain();
        ProfilePictureSmall = dataUser.getuserUserThumb();
        ivProfilePicture.setImageBitmap(ProfilePictureLarge);
        ivProfilePictureSmall.setImageBitmap(ProfilePictureSmall);
    }

    public void AddPhoto(View view) {
        Intent intent = new Intent();
        intent.setType(ImageHandler.IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        int intentid = ImageHandler.PROFILEPICTURE;
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), intentid);
    }


    //Todo - Handle image on user upload, not post.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // What ImageButton was clicked
            switch (requestCode) {
                case ImageHandler.PROFILEPICTURE:
                    ProfilePictureLarge = HandleImage(ivProfilePicture, ImageHandler.image_profile_main_px(), data);
                    if (ProfilePictureLarge == null) {setNullImages(); return;}
                    ProfilePictureSmall = HandleImage(ivProfilePictureSmall, ImageHandler.image_profile_thumb_px(), data);
                    PPLString = ImageHandler.ConvertBitmaptoString(ProfilePictureLarge);
                    PPSString = ImageHandler.ConvertBitmaptoString(ProfilePictureSmall);
                    break;
            }
        }
    }

    private Bitmap HandleImage(CircleImageView iv, int size, Intent data) {
        Bitmap bitmap = ImageHandler.ImagefromStoragetoBitmap(data, size, context);
        ImageHandler.SetImage(bitmap, iv);
        return bitmap;
    }

    private void setNullImages () {
        Drawable ProfileImage = getResources().getDrawable(R.drawable.logopurple);
        Bitmap B = ((BitmapDrawable) ProfileImage).getBitmap();
        try {
            ProfilePictureLarge = ImageHandler.ResizeBitmap(B, ImageHandler.image_profile_main_px());
            ProfilePictureSmall = ImageHandler.ResizeBitmap(B, ImageHandler.image_profile_thumb_px());
            PPLString = ImageHandler.ConvertBitmaptoString(ProfilePictureLarge);
            PPSString = ImageHandler.ConvertBitmaptoString(ProfilePictureSmall);
        } catch (IOException e) { e.printStackTrace(); }
    }


    public void changeProfilePicture(View view) {
        if (ProfilePictureLarge == null) { setNullImages(); }
        App.setPPL(PPLString);
        App.setPPS(PPSString);

        String[] profile = {dataUser.getuserUsername(), dataUser.getuserPassword(), dataUser.getuserEmail(),
                dataUser.getuserPSE(), dataUser.getuserStudying(), dataUser.getuserGender(), dataUser.getuserBirthDate(),
                PPLString, PPSString};
        App.getClient().updateProfile(profile);

        Intent changedrawer = new Intent(this, Drawer_Main.class);
        changedrawer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(changedrawer);
    }
}