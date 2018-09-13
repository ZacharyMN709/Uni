package com.unisoftwareproductions.uni.Handlers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;

public class ImageHandler {

    public static final String IMAGE_TYPE = "image/*";
    public static final int SELECTPICTUREA = 201;
    public static final int SELECTPICTUREB = 301;
    public static final int SELECTPICTUREC = 401;
    public static final int SELECTPICTURED = 501;
    public static final int SELECTPICTUREE = 601;
    public static final int PROFILEPICTURE = 801;

    public static int max_dp_factor = 4;
    public static float phonedensity = (Resources.getSystem().getDisplayMetrics().density);
    public static double leewaypercentage =  0.65;
    public static int image_profile_main_px() { return 560;}
    public static int image_profile_main_dp() { return (int) (image_profile_main_px() * phonedensity / max_dp_factor);}
    public static int image_profile_thumb_px() { return 160;}
    public static int image_profile_thumb_dp() { return (int) (image_profile_thumb_px() * phonedensity / max_dp_factor);}
    public static int image_ad_main_px() { return 800;}
    public static int image_ad_main_dp() { return (int) (image_ad_main_px() * phonedensity / max_dp_factor);}
    public static int image_ad_thumb_px() { return 480;}
    public static int image_ad_thumb_dp() { return (int) (image_ad_thumb_px() * phonedensity / max_dp_factor);}

    //----------------------------------------------------------------------------------------------

    Uri uri;
    ContentResolver resolver;
    String path;
    Matrix orientation;
    int MAX_SIZE;

    public ImageHandler() {   }

    public ImageHandler(Uri uri, ContentResolver resolver, int MAX_SIZE) {
        this.uri = uri;
        this.resolver = resolver;
        this.MAX_SIZE = MAX_SIZE;
    }

    /* Support for gallery apps and remote ("picasa") images */
    private boolean getInformationFromMediaDatabase() {
        String[] fields = {MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION};
        Cursor cursor = resolver.query(uri, fields, null, null, null);

        if (cursor == null)
            return false;

        cursor.moveToFirst();
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
        this.orientation = new Matrix();
        this.orientation.setRotate(orientation);
        cursor.close();

        return true;
    }

    /* Support for file managers and dropbox */
    private boolean getInformationFromFileSystem() throws IOException {
        path = uri.getPath();

        if (path == null)
            return false;

        ExifInterface exif = new ExifInterface(path);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        // Sets orientation back to normal if altered
        this.orientation = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                /* Identity matrix */
                break;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                this.orientation.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                this.orientation.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                this.orientation.setScale(1, -1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                this.orientation.setRotate(90);
                this.orientation.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                this.orientation.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                this.orientation.setRotate(-90);
                this.orientation.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                this.orientation.setRotate(-90);
                break;
        }
        return true;
    }

    // Determines picture size
    private boolean getStoredDimensions() throws IOException {
        InputStream input = resolver.openInputStream(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(resolver.openInputStream(uri), null, options);

        /* The input stream could be reset instead of closed and reopened if it were possible
           to reliably wrap the input stream on a buffered stream, but it's not possible because
           decodeStream() places an upper read limit of 1024 bytes for a reset to be made (it calls
           mark(1024) on the stream). */
        input.close();

        // Sets minimum size of image to be selected
        int min_size = (int) (MAX_SIZE * leewaypercentage);
        if (options.outHeight < min_size || options.outWidth < min_size) {return false;}
        return true;
    }

    // Ensures an image is selected
    // Relies on private booleans "getInformationFromMediaDatabase" and "getInformationFromFileSystem"
    private boolean getInformation() throws IOException {
        if (getInformationFromMediaDatabase())
            return true;
        if (getInformationFromFileSystem())
            return true;
        return false;
    }

    // Alters selected image to fit in app.
    // Relies on private boolean "getInformation" and "getStoredDimensions"
    public Bitmap getBitmap() throws IOException {

        // Ensures that an image is selected
        if (!getInformation())
            throw new FileNotFoundException();

        // Ensure that image is proper size
        if (!getStoredDimensions())
            throw new InvalidObjectException(null);

        // Generates Bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap original = BitmapFactory.decodeStream(resolver.openInputStream(uri), null, options);

        return ResizeBitmap(original, MAX_SIZE);
    }

    //--------------------------------------------------------------------------------------------

    public static Bitmap ResizeBitmap(Bitmap bitmap, int MAX_SIZE) throws IOException {
        if (bitmap == null) {return null;}
        float ratio = 0;
        int originalHeight = bitmap.getHeight();
        int originalWidth = bitmap.getWidth();

        // Alters Image size
        if (originalHeight > originalWidth) { ratio = MAX_SIZE / (float) originalHeight; }
        if (originalWidth >= originalHeight) { ratio = MAX_SIZE / (float) originalWidth; }
        int finalHeight = Math.round(originalHeight * ratio);
        int finalWidth = Math.round(originalWidth * ratio);
        Log.e("ImageHandler", "ResizeBitmap: finalHeight: " + finalHeight);
        Log.e("ImageHandler", "ResizeBitmap: finalWidth: " + finalWidth);

        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true);
    }

    //--------------------------------------------------------------------------------------------

    public static Bitmap ResizeBitmaptoDevice(Bitmap bitmap) throws IOException {
        if (bitmap == null) {return null;}
        int originalHeight = bitmap.getHeight();
        int originalWidth = bitmap.getWidth();
        int finalHeight = Math.round(originalHeight * phonedensity / max_dp_factor);
        int finalWidth = Math.round(originalWidth * phonedensity / max_dp_factor);
        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true);
    }

    //-------------------------------------------------------------------------------------

    public static Bitmap ImagefromStoragetoBitmap(Intent data, int maxsize, Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = new ImageHandler(data.getData(), context.getContentResolver(), maxsize).getBitmap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //-------------------------------------------------------------------------------------

    public static String ImagefromStoragetoString(Intent data, int maxsize, Context context) {
        String bitmapstring = null;
        Bitmap tempbitmap = ImagefromStoragetoBitmap(data, maxsize, context);
        ConvertBitmaptoString(tempbitmap);
        return bitmapstring;
    }

    //----------------------------------------------------------------------------------------------

    public static Bitmap ReassembleBitmapfromBytes(byte[] b, int MAX_SIZE){
        Bitmap RETURN = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            RETURN =  ResizeBitmap(bitmap, MAX_SIZE);
        } catch (Exception e) {
            e.getMessage();
        }
        return RETURN;
    }

    //----------------------------------------------------------------------------------------------

    public static Bitmap ReassembleBitmapfromString(String string, int MAX_SIZE){
        if (string == null) {return null;}
        byte[] bytes = Base64.decode(string, Base64.DEFAULT);
        return ReassembleBitmapfromBytes(bytes, MAX_SIZE);
    }

    //----------------------------------------------------------------------------------------------

    public static byte[] ConvertBitmaptoBytes(Bitmap pic) {
        if (pic == null) {return null;}
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        Log.e("BitmaptoBytes: ", "Byte array B length is: " + String.valueOf(b.length));

        return b;
    }

    //----------------------------------------------------------------------------------------------

    public static String ConvertBitmaptoString(Bitmap pic) {
        if (pic == null) {return null;}
        Log.e("BitmaptoString: ", String.valueOf(pic.getHeight()));
        Log.e("BitmaptoString: ", String.valueOf(pic.getWidth()));
        String string = Base64.encodeToString(ConvertBitmaptoBytes(pic), Base64.DEFAULT);
        Log.e("BitmaptoString: ", "String length: " + String.valueOf(string.length()));
        return string;
    }

    //----------------------------------------------------------------------------------------------

    public static void SetImage(Bitmap bitmap, ImageView iv) {
        try {
            iv.setImageBitmap(ImageHandler.ResizeBitmaptoDevice(bitmap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}



