package com.guc.helloworld;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_VIDEO = 0xF120;
    private static final int REQUEST_CODE_GALLERY = 0xF121;
    private static final int REQUEST_CODE_CAMERA_PROVIDE_FILE = 0xF123;
    private static final int REQUEST_CODE_RECORD = 0xF125;
    public final String LOG_TAG = getClass().getName();

    //http://developer.android.com/training/camera/photobasics.html
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
    }

    //<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    public void openCameraApp(View view) {
        final String items[] = {"Pick Photo",
                "Take Photo"};
        AlertDialog.Builder ab = new AlertDialog.Builder(
                this);
        ab.setItems(items,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface d,
                                        int choice) {

                        switch (choice) {
                            case 0:
                                pickPhoto();

                                break;

                            case 1:
                                dispatchTakePictureIntent();

                                break;

                        }


                    }
                }
        );
        ab.show();

    }

    void pickPhoto() {
        startActivityForResult(getPhotoPickerIntent(), REQUEST_CODE_GALLERY);
    }

    public static Intent getPhotoPickerIntent() {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            return Intent.createChooser(intent, "Pick Photo");
        } else { //Handle KITKAT image picking
            return new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
    }

    //CREATE TEMP FILE
    //PROVIDE TO CAMERA APP
    //SAVE IMAGE IF SETTING ENABLED
    String mCurrentPhotoPath;
    File tempFile;

    Uri mCurrentPhotoUri;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir;

        storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        //SAVE IN CACHE
        //storageDir = getCacheDir();
        //SAVE in

        //storageDir = getExternalFilesDir(null);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpeg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Log.d(LOG_TAG, "dispatchTakePictureIntent(): resolveActivity! null");

            // Create the File where the photo should go
            try {
                tempFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (tempFile != null) {
                Log.d(LOG_TAG, "dispatchTakePictureIntent(): photoFile! null");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(tempFile));
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA_PROVIDE_FILE);
            }
        } else {
            Log.d(LOG_TAG, "dispatchTakePictureIntent(): resolveActivity null");

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            //REQUEST_CODE_CAMERA_PROVIDE_FILE
            if (requestCode == REQUEST_CODE_CAMERA_PROVIDE_FILE) {
                ImageView myImage = (ImageView) findViewById(R.id.image);
                //myImage.setImageDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));
                Picasso.with(this).load(mCurrentPhotoPath).into(myImage);
            } else if (requestCode == REQUEST_CODE_GALLERY) {
                Uri mMediaUri = data.getData();
                Log.d(LOG_TAG, "uri: " + mMediaUri.getPath());

                ImageView myImage = (ImageView) findViewById(R.id.image);
                myImage.setImageURI(mMediaUri);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
