package com.unisoftwareproductions.uni.Main.Start;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.NavigationController;
import com.unisoftwareproductions.uni.Main.Fragments.PopUps.PopUpCalendar;
import com.unisoftwareproductions.uni.Handlers.ImageHandler;
import com.unisoftwareproductions.uni.R;

import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zachary on 10-Jul-16.
 */
public class Register extends AppCompatActivity implements PopUpCalendar.CalendarPass, AdapterView.OnItemSelectedListener {

    String Email, Username, Password, PSE, Studying, Gender, BirthDate, CurrentDate, PPLString, PPSString;
    Bitmap ProfilePictureLarge, ProfilePictureSmall;
    CircleImageView ivProfilePicture;
    EditText etUsername;
    TextView DateofBirth;
    Spinner spinStudying, spinPSE;
    RadioGroup rgGender;
    RadioButton Female, Male, Other;
    Context context;
    App App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App = new App(this);
        setContentView(R.layout.register);
        ivProfilePicture = (CircleImageView) findViewById(R.id.profilepicture);
        context = getBaseContext();
        Email = getIntent().getStringExtra("Email");
        Password = getIntent().getStringExtra("Password");

        etUsername = (EditText) findViewById(R.id.etUsername);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        Female = (RadioButton) findViewById(R.id.rgFemale);
        Male = (RadioButton) findViewById(R.id.rgMale);
        Other = (RadioButton) findViewById(R.id.rgOther);
        spinPSE = (Spinner) findViewById(R.id.spinnerPSE);
        spinStudying = (Spinner) findViewById(R.id.spinnerCourse);
        DateofBirth = (TextView) findViewById(R.id.tvBirthDate);

        setBaseData();
    }

    public void calendarpopup(View view) {
        hideSoftKeyboard(this);
        Fragment newFragment = new PopUpCalendar();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction FT = fragmentManager.beginTransaction();
        FT.add(R.id.fragmentholder, newFragment, "CalendarPopUp");
        FT.addToBackStack("CalendarPopUp");
        FT.commit();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onDataPass(String data) {
        BirthDate = data;
        DateofBirth.setText(BirthDate);
    }

    public void AddPhoto(View view) {
        Intent intent = new Intent();
        intent.setType(ImageHandler.IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        int intentid = ImageHandler.PROFILEPICTURE;
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), intentid);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // What ImageButton was clicked
            switch (requestCode) {
                case ImageHandler.PROFILEPICTURE:
                    ProfilePictureLarge = HandleImage(ivProfilePicture, data);
                    if (ProfilePictureLarge == null) {ProfilePictureSmall = null; return;}
                    PPLString = ImageHandler.ConvertBitmaptoString(ProfilePictureLarge);
                    PPSString = ImageHandler.ConvertBitmaptoString(ProfilePictureSmall);
                    break;
            }
            try { ivProfilePicture.setImageBitmap(ImageHandler.ResizeBitmaptoDevice(ProfilePictureLarge));
            } catch (IOException e) { e.printStackTrace(); }
        }
        imageviewresize(ivProfilePicture);
    }

    private Bitmap HandleImage(ImageView iv, Intent data) {
        Bitmap bitmap = ImageHandler.ImagefromStoragetoBitmap(data, ImageHandler.image_profile_main_px(), context);
        ImageHandler.SetImage(bitmap, iv);
        try { ProfilePictureSmall = ImageHandler.ResizeBitmap(bitmap, ImageHandler.image_profile_thumb_px());
        } catch (IOException e) { e.printStackTrace();
            ProfilePictureLarge = null;
            imageviewresize(iv);
            return null;
        }
        imageviewresize(iv);
        return bitmap;
    }

    private void imageviewresize(ImageView imageview) {
        if (imageview.getDrawable() != null) {
            imageview.requestLayout();
            imageview.getLayoutParams().height = ImageHandler.image_profile_main_dp();
            imageview.getLayoutParams().width = ImageHandler.image_profile_main_dp();
        } else {
            imageview.requestLayout();
            imageview.getLayoutParams().height = 0;
            imageview.getLayoutParams().width = 0;
        }

    }


    public void createaccount(View view) {

        Username = etUsername.getText().toString();
        Gender = getGender();
        if (master(Email, Username)) {
             if (ProfilePictureLarge == null) {
                 Drawable ProfileImage = getResources().getDrawable(R.drawable.logopurple);
                 Bitmap B = ((BitmapDrawable) ProfileImage).getBitmap();
                 try {
                     ProfilePictureLarge = ImageHandler.ResizeBitmap(B, ImageHandler.image_profile_main_px());
                     ProfilePictureSmall = ImageHandler.ResizeBitmap(B, ImageHandler.image_profile_thumb_px());
                     PPLString = ImageHandler.ConvertBitmaptoString(ProfilePictureLarge);
                     PPSString = ImageHandler.ConvertBitmaptoString(ProfilePictureSmall);
                 } catch (IOException e) { e.printStackTrace(); }
             }

            App.setClient("Null", "Null");
            String response = App.getClient().register(Username, Password, Email, PSE, Studying, Gender, BirthDate,
                    PPLString, PPSString);
            if (response == "Invalid Character: ," || response == "Registration Failed, Unknown Reason") {
                Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                return;}
            App.setSavedPassword(Password);
            App.setSavedUsername(Username);
            NavigationController.toStartScreen(this);
        }
    }

    @Override
    public void onBackPressed() {
        PopUpCalendar myFragment = (PopUpCalendar)getFragmentManager().findFragmentByTag("CalendarPopUp");
        if (myFragment != null && myFragment.isVisible()) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.spinnerPSE:
                PSE = parent.getItemAtPosition(pos).toString();
                switch (pos) {
                    case 0:
                        setSecondarySpinner(R.array.Studying_array_MUN);
                        break;
                    case 1:
                        setSecondarySpinner(R.array.Studying_array_MI);
                        break;
                    case 2:
                        setSecondarySpinner(R.array.Studying_array_CNA);
                        break;
                    case 3:
                        setSecondarySpinner(R.array.Studying_array_CNA);
                        break;
                    case 4:
                        setSecondarySpinner(R.array.Studying_array_CNA);
                        break;
                }
                break;
            case R.id.spinnerCourse:
                Studying = parent.getItemAtPosition(pos).toString();
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {   }

    private void setSecondarySpinner(int array) {
        ArrayAdapter<CharSequence> Studyingadapter = ArrayAdapter.createFromResource(this,
                array, android.R.layout.simple_spinner_item);
        Studyingadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStudying.setAdapter(Studyingadapter);
        spinStudying.setOnItemSelectedListener(this);
    }


    private String getGender() {
        String Gender;
        switch (rgGender.getCheckedRadioButtonId()) {
            case R.id.rgFemale:
                Gender = "Female";
                break;
            case R.id.rgMale:
                Gender = "Male";
                break;
            case R.id.rgOther:
                Gender = "Other";
                break;
            default:
                Gender = null;
        }
        return Gender;
    }

    private boolean master(String email, String username) {
        if (!filled()) {
            return false; }
        if (!matched(email)) {
            return false; }
        if (!validname(username)) {
            Toast.makeText(Register.this, "Invalid Username. Please choose a different name.", Toast.LENGTH_SHORT).show();
            return false; }
        else
            return true;
    }

    private boolean validname(String username) {
        if (username.equalsIgnoreCase(",")) { return false; }
        if (username.equalsIgnoreCase("@")) { return false; }
        if (username.equalsIgnoreCase("Uni")) { return false; }
        if (username.equalsIgnoreCase("Admin")) { return false; }
        else {return true;}
    }

    // Ensures text fields is not empty
    private boolean filled() {
        if (etUsername.getText().toString().trim().equals("")) {
            Toast.makeText(Register.this, "Username is empty!", Toast.LENGTH_SHORT).show();
            return false; }
        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(Register.this, "Please choose a Gender", Toast.LENGTH_SHORT).show();
            return false; }
        if (CurrentDate.equals(BirthDate)) {
            Toast.makeText(Register.this, "Please enter your Birth Date", Toast.LENGTH_SHORT).show();
            return false; }
        else
            return true;
    }

    private boolean matched(String email){
        if(email.contains("@mun.ca") & PSE.equals("Memorial University")){
            return true; }
        if(email.contains("@mi.mun.ca") & PSE.equals("Marine Institute")){
            return true; }
        if(email.contains("@ed.cna.nl.ca") & PSE.contains("College of the North Atlantic") ){
            return true;}
        if(email.contains("@unisoftwareproductions.ca")){
            return true; }
        else {
            Toast.makeText(Register.this, "E-mail domain and chosen Institute do not match!", Toast.LENGTH_LONG).show();
            return false; }
    }

    private void setBaseData() {
        ArrayAdapter<CharSequence> PSEAdapter = ArrayAdapter.createFromResource(this,
                R.array.PSE_array, android.R.layout.simple_spinner_item);
        PSEAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPSE.setAdapter(PSEAdapter);
        spinPSE.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> Studyingadapter = ArrayAdapter.createFromResource(this,
                R.array.Studying_array, android.R.layout.simple_spinner_item);
        Studyingadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStudying.setAdapter(Studyingadapter);
        spinStudying.setOnItemSelectedListener(this);

        Calendar c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH) + 1;
        int Day = c.get(Calendar.DAY_OF_MONTH);
        CurrentDate = Year + "-" + Month + "-" + Day;
        BirthDate = CurrentDate;
        Gender = "Female";
        PSE = "Memorial University";
        Studying = "Aboriginal Studies";
    }

}
