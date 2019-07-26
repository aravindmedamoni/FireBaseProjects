package com.aravind.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadsActivity extends AppCompatActivity {

    private Button uploadButton,selectFileButton;
    private ImageView imageView;
    private Uri uri;

    private StorageReference mStorageRef;

    private static final int GALLERY = 4;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploads);

        uploadButton = findViewById(R.id.upload_button);
        selectFileButton = findViewById(R.id.SelectFile);
        imageView = findViewById(R.id.uploadImage);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        selectFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Below if Condition is used for checking the Permissions are granted or not if granted
                // if condition is execute. Otherwise We Should request the user to allow the Permissions.{in Else Part}
                if (ContextCompat.checkSelfPermission(UploadsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE )== PackageManager.PERMISSION_GRANTED){
                    selectFile();
                }else {
                    ActivityCompat.requestPermissions(UploadsActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }

            }
        });
    }
// the Bellow Method is Checking Whether the user Allowing the permissions are or not
// if he allows if condition is execute or else condition is going to be execute.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectFile();
        }else {
            Toast.makeText(this, "Please Provide Permissions", Toast.LENGTH_SHORT).show();
        }
    }
// this method is Used to select the uploaded file {pdf/images/audios/videos}

    private void selectFile(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY);
    }

    // This method automatically called by the android management whether the user select any file or not
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY && resultCode==RESULT_OK && data!=null){
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Loading..");
            progressDialog.setProgress(0);
            uri = data.getData();
            progressDialog.show();

            imageView.setImageURI(uri);

        }else {
            Toast.makeText(this, "Please Select Any File.", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadFile(View view) {

        if (uri!=null){
            StorageReference fileName = mStorageRef.child("images/"+uri.getLastPathSegment()+".png");

            fileName.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UploadsActivity.this, "Image uploaded Successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadsActivity.this, "Image upload Failed"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setProgress(currentProgress);
                }
            });
        }else {
            Toast.makeText(this, "Please Select File", Toast.LENGTH_SHORT).show();
        }


    }
}
