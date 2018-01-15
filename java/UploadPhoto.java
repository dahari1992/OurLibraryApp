package com.example.matan.library;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadPhoto extends AppCompatActivity {
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect,btnSelect2;
    private ImageView ivImage;
    private String userChoosenTask;
    FirebaseStorage storage;
    StorageReference storageRef,imageRef;
    ProgressDialog progressDialog;
    UploadTask uploadTask;
    ImageView imageView;
    Uri selectedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_photo);
        //accessing the firebase storage
        storage = FirebaseStorage.getInstance();

        //creates a storage reference
        storageRef = storage.getReference("Admin");

            btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
            btnSelect.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    selectImage();
                }
            });


            ivImage = (ImageView) findViewById(R.id.myImage);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if(userChoosenTask.equals("Take Photo"))
                            cameraIntent();
                        else if(userChoosenTask.equals("Choose from Library"))
                            galleryIntent();
                    } else {

                    }
                    break;
            }
        }

        private void selectImage() {

            final CharSequence[] items = { "Take Photo", "Choose from Library",
                    "Cancel" };

            AlertDialog.Builder builder = new AlertDialog.Builder(UploadPhoto.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result=Utility.checkPermission(UploadPhoto.this);

                    if (items[item].equals("Take Photo")) {
                        userChoosenTask ="Take Photo";
                        if(result)
                            cameraIntent();



                    } else if (items[item].equals("Choose from Library")) {
                        userChoosenTask ="Choose from Library";
                        if(result)
                            galleryIntent();

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }

        private void galleryIntent()
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            selectedImage = intent.getData();
            startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);

        }

        private void cameraIntent()
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            selectedImage = intent.getData();

            startActivityForResult(intent, REQUEST_CAMERA);


        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE)
                    onSelectFromGalleryResult(data);
                else if (requestCode == REQUEST_CAMERA)
                    onCaptureImageResult(data);
            }
        }

        private void onCaptureImageResult(Intent data) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ivImage.setImageBitmap(thumbnail);
            Context context = getApplicationContext();
            CharSequence text = "Photo uploaded successfully!";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context,text,duration).show();
        }

        @SuppressWarnings("deprecation")
        private void onSelectFromGalleryResult(Intent data) {

            Bitmap bm=null;
            if (data != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ivImage.setImageBitmap(bm);
            Context context = getApplicationContext();
            CharSequence text = "Photo uploaded successfully!";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context,text,duration).show();
        }



    }



