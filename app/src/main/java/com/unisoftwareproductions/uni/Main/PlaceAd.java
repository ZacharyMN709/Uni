package com.unisoftwareproductions.uni.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.ImageHandler;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;

import com.unisoftwareproductions.uni.R;

import java.io.IOException;

public class PlaceAd extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Context context;
    ImageView ibImageA, ibImageB, ibImageC, ibImageD, ibImageE;
    EditText etItemName, etPrice, etDescription;
    RadioGroup rgCondition;
    String USR, USRID, PPS, Category, String1, String2, String3, String4, String5;
    Spinner spinCategory;
    Bitmap PicturePreview, Picture1, Picture2, Picture3, Picture4, Picture5;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            App = new App(this);
            setContentView(R.layout.placead);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar_placead);
            context = this;

            // Get reference to the views
            etItemName = (EditText) findViewById(R.id.etItemName);
            etPrice = (EditText) findViewById(R.id.etPrice);
            etDescription = (EditText) findViewById(R.id.etDescription);
            ibImageA = (ImageView) findViewById(R.id.ibImageA);
            ibImageB = (ImageView) findViewById(R.id.ibImageB);
            ibImageC = (ImageView) findViewById(R.id.ibImageC);
            ibImageD = (ImageView) findViewById(R.id.ibImageD);
            ibImageE = (ImageView) findViewById(R.id.ibImageE);

            spinCategory = (Spinner) findViewById(R.id.spinnerCategory);
            ArrayAdapter<CharSequence> PSEAdapter = ArrayAdapter.createFromResource(this,
                    R.array.Category_array, android.R.layout.simple_spinner_item);
            PSEAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinCategory.setAdapter(PSEAdapter);
            spinCategory.setOnItemSelectedListener(this);

            ibImageA.setOnClickListener(this);
            ibImageB.setOnClickListener(this);
            ibImageC.setOnClickListener(this);
            ibImageD.setOnClickListener(this);
            ibImageE.setOnClickListener(this);

            rgCondition = (RadioGroup) findViewById(R.id.rgCondition);

            USRID = App.getUSRID();
            USR = App.getUSR();
            PPS = App.getPPS();

        }

    public void AddPhoto(View view) {
        Intent intent = new Intent();
        intent.setType(ImageHandler.IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        int intentid;
        if (Picture1 == null) { intentid = ImageHandler.SELECTPICTUREA; }
        else if (Picture2 == null) { intentid = ImageHandler.SELECTPICTUREB; }
        else if (Picture3 == null) { intentid = ImageHandler.SELECTPICTUREC; }
        else if (Picture4 == null) { intentid = ImageHandler.SELECTPICTURED; }
        else { intentid = ImageHandler.SELECTPICTUREE; }
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)),
                intentid);
    }

    public void onClick (View view) {
        Intent intent = new Intent();
        intent.setType(ImageHandler.IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        int intentid;
        switch (view.getId()) {
            case R.id.ibImageA:
                intentid = ImageHandler.SELECTPICTUREA;
                break;
            case R.id.ibImageB:
                intentid = ImageHandler.SELECTPICTUREB;
                break;
            case R.id.ibImageC:
                intentid = ImageHandler.SELECTPICTUREC;
                break;
            case R.id.ibImageD:
                intentid = ImageHandler.SELECTPICTURED;
                break;
            case R.id.ibImageE:
                intentid = ImageHandler.SELECTPICTUREE;
                break;
            default: return;
        }
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)),
                intentid);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // What ImageButton was clicked
            switch (requestCode) {
                case ImageHandler.SELECTPICTUREA:
                    Picture1 = HandleImage(ibImageA, data);
                    String1 = ImageHandler.ConvertBitmaptoString(Picture1);
                    if (Picture1 == null) {
                        DownshiftA();
                    }
                    break;
                case ImageHandler.SELECTPICTUREB:
                    Picture2 = HandleImage(ibImageB, data);
                    String2 = ImageHandler.ConvertBitmaptoString(Picture2);
                    if (Picture2 == null) {
                        DownshiftB();
                    }
                    break;
                case ImageHandler.SELECTPICTUREC:
                    Picture3 = HandleImage(ibImageC, data);
                    String3 = ImageHandler.ConvertBitmaptoString(Picture3);
                    if (Picture3 == null) {
                        DownshiftC();
                    }
                    break;
                case ImageHandler.SELECTPICTURED:
                    Picture4 = HandleImage(ibImageD, data);
                    String4 = ImageHandler.ConvertBitmaptoString(Picture4);
                    if (Picture4 == null) {
                        DownshiftD();
                    }
                    break;
                case ImageHandler.SELECTPICTUREE:
                    Picture5 = HandleImage(ibImageE, data);
                    String5 = ImageHandler.ConvertBitmaptoString(Picture5);
                    if (Picture5 == null) {
                        DownshiftE();
                    }
                    break;
            }
        }
    }

    private Bitmap HandleImage(ImageView iv, Intent data) {
        Bitmap bitmap = ImageHandler.ImagefromStoragetoBitmap(data, ImageHandler.image_ad_main_px(), context);
        ImageHandler.SetImage(bitmap, iv);
        imageviewresize(iv, bitmap);
        return bitmap;
    }

    private void imageviewresize(ImageView imageview, Bitmap bitmap) {
        if (bitmap != null) {
            imageview.requestLayout();
            imageview.getLayoutParams().height = ImageHandler.image_ad_main_dp();
            imageview.getLayoutParams().width = ImageHandler.image_ad_main_dp();
        } else {
            imageview.requestLayout();
            imageview.getLayoutParams().height = 0;
            imageview.getLayoutParams().width = 0;
        }
    }

    private void DownshiftA() {
        Picture1 = Picture2;
        String1 = String2;
        ibImageA.setImageBitmap(Picture1);
        imageviewresize(ibImageA, Picture1);
        DownshiftB();
    }
    private void DownshiftB() {
        Picture2 = Picture3;
        String2 = String3;
        ibImageB.setImageBitmap(Picture2);
        imageviewresize(ibImageB, Picture2);
        DownshiftC();
    }
    private void DownshiftC() {
        Picture3 = Picture4;
        String3 = String4;
        ibImageC.setImageBitmap(Picture3);
        imageviewresize(ibImageC, Picture3);
        DownshiftD();
    }
    private void DownshiftD() {
        Picture4 = Picture5;
        String4 = String5;
        ibImageD.setImageBitmap(Picture4);
        imageviewresize(ibImageD, Picture4);
        DownshiftE();
    }
    private void DownshiftE() {
        Picture5 = null;
        String5 = null;
        ibImageE.setImageBitmap(null);
        imageviewresize(ibImageE, Picture5);
        Toast.makeText(PlaceAd.this, "Picture file not loaded. Please use another.", Toast.LENGTH_SHORT).show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Category = "*" + parent.getItemAtPosition(pos).toString() + "* ";
    }

    public void onNothingSelected(AdapterView<?> parent) {   }

    public void Confirm(View view) {
        if (validate()) {
            String ItemName = Category + etItemName.getText().toString();
            String ItemPrice = etPrice.getText().toString();
            String ItemDescription = etDescription.getText().toString();
            String ItemCondition = null;
            String Tags = null;
            try { PicturePreview = ImageHandler.ResizeBitmap(Picture1, ImageHandler.image_ad_thumb_px());
            } catch (IOException e) { e.printStackTrace(); }
            switch (rgCondition.getCheckedRadioButtonId()) {
                case R.id.rgUnused:
                    ItemCondition = "Unused";
                    break;
                case R.id.rgUsed:
                    ItemCondition = "Used";
                    break;
                case R.id.rgBroken:
                    ItemCondition = "Damaged/Broken";
                    break;
            }

            Log.e("String1", String1);
            if (String2 != null) {Log.e("String2", String2);}
            else {Log.e("String2", "NULL");}
            if (String3 != null) {Log.e("String3", String3);}
            else {Log.e("String3", "NULL");}
            if (String4 != null) {Log.e("String4", String4);}
            else {Log.e("String4", "NULL");}
            if (String5 != null) {Log.e("String5", String5);}
            else {Log.e("String5", "NULL");}

            String[] ad = new String[] {
                    ItemName, ItemPrice, ItemDescription, ItemCondition,
                    ImageHandler.ConvertBitmaptoString(PicturePreview),
                    String1, String2, String3, String4, String5};

            Log.e("Confirm: ", "To send length  = " + String.valueOf(ad.toString().length()));

            Log.e("PlaceAd", App.getClient().postAd(ad));

            onBackPressed();
        }
    }

    // Ensures that no key fields are empty
    private boolean validate(){
        // "" refers to absence of text
        if(etItemName.getText().toString().trim().equals("")){
            Toast.makeText(PlaceAd.this, "Item Name is Empty!", Toast.LENGTH_SHORT).show();
            return false;}
        else if(etPrice.getText().toString().trim().equals("")){
            Toast.makeText(PlaceAd.this, "Item Price is Empty!", Toast.LENGTH_SHORT).show();
            return false;}
        else if(etDescription.getText().toString().trim().equals("")){
            Toast.makeText(PlaceAd.this, "Item Description is Empty!", Toast.LENGTH_SHORT).show();
            return false;}
            // -1 is no RadioButton pressed
        else if(rgCondition.getCheckedRadioButtonId() == -1){
            Toast.makeText(PlaceAd.this, "Item Quality is Unchecked!", Toast.LENGTH_SHORT).show();
            return false;}
            // null refers to absence of a photo loaded from phone memory
        else if(ibImageA.getDrawable() == null){
            Toast.makeText(PlaceAd.this, "Item Image is Empty!", Toast.LENGTH_SHORT).show();
            return false;}
        else { return true; }
    }
}
