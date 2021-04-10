package com.sharnoxz.vacxinity.accounts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sharnoxz.vacxinity.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserData extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_CODE = 1000;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Uri mImageUri;
    String documentId;
    String imageUri="https://firebasestorage.googleapis.com/v0/b/wosafe-5e1fc.appspot.com/o/notification_icon%2Fdefaultprofile.jpg?alt=media&token=a78b0342-e4c2-477a-9b03-be87af4c043e";
    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = firebaseAuth.getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference reference = firebaseStorage.getReference();

    CircleImageView profileImage;
    EditText name,gender,address,pinCode,phone;
    Button done;
    ProgressBar progressBar,progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        profileImage = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        gender=findViewById(R.id.gender);
        address=findViewById(R.id.address);
        pinCode=findViewById(R.id.pincode);
        phone=findViewById(R.id.phone);
        done=findViewById(R.id.Done);
        progressBar = findViewById(R.id.progressBar);
        progressBar1 = findViewById(R.id.PicProgressBar);

        SharedPrefs.saveSharedSetting(UserData.this,"sharedPrefs","true");


        profileImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
//                        openCamera();
//                        galleryAddPic();
                        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent,1);

                    }

                } else {
                    //openCamera();
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,1);
                }
            }
        });



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = name.getText().toString();
                String getGender = gender.getText().toString();
                String getAddress = address.getText().toString();
                String getPinCode = pinCode.getText().toString();
                String getPhone = phone.getText().toString();
                String getUserUid = getIntent().getStringExtra("UserId");


                if(TextUtils.isEmpty(getName)){
                    Toast.makeText(UserData.this,"Please enter your Name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getGender)){
                    Toast.makeText(UserData.this,"Please enter your Gender",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getAddress)){
                    Toast.makeText(UserData.this,"Please enter your Address",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getPinCode)){
                    Toast.makeText(UserData.this,"Please enter Pin Code",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getPhone)){
                    Toast.makeText(UserData.this,"Please enter your Phone",Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    Userdatas userdatas = new Userdatas(getName,getGender,getAddress,getPinCode,getPhone,imageUri,user.getUid());
                    db.collection("users")
                            .document(user.getUid())
                            .set(userdatas, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onUpdate");
                                    Intent intent = new Intent(UserData.this, login.class);
                                    startActivity(intent);
                                    finish();
                                    progressBar.setVisibility(View.GONE);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UserData.this,"Data Uploading Failed",Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });
    }

    String currentPhotoPath;
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex){
                Toast.makeText(this,"Error in creating File",Toast.LENGTH_LONG).show();
            }
            if(photoFile!=null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.saif.android.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        openCamera();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                assert data != null;
                mImageUri = data.getData();
                String timeSpan = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                progressBar1.setVisibility(View.VISIBLE);
                final StorageReference image1 = reference.child("UserPics").child("Image"+timeSpan+".jpg");
                image1.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                image1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Photo photo = new Photo(uri.toString(),user.getUid());
                                        imageUri = uri.toString();
                                        Glide.with(UserData.this).load(imageUri).into(profileImage);
                                        progressBar1.setVisibility(View.GONE);
                                        db.collection("users")
                                                .document(user.getUid())
                                                .set(photo)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(UserData.this, "Pic Uploaded", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        e.printStackTrace();
                                                        Toast.makeText(UserData.this,"Pic not Uploaded",Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                        progressBar1.setVisibility(View.GONE);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Toast.makeText(UserData.this,"Pic not Uploaded",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(UserData.this,"Pic not Uploaded",Toast.LENGTH_SHORT).show();
            }

        }
    }

}
