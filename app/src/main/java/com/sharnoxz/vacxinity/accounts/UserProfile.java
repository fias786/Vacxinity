package com.sharnoxz.vacxinity.accounts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sharnoxz.vacxinity.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfile extends AppCompatActivity {

    ImageView edit;
    TextView profileName,name,gender,address,pinCode,phone,email;
    CircleImageView profilePic,image;
    ImageView backgroundImage;
    ProgressBar progressBar;

    private static final int PERMISSION_CODE = 1000;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public static String documentId;
    private Uri mImageUri;
    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = firebaseAuth.getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference reference = firebaseStorage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        edit = findViewById(R.id.EditAll);
        profilePic = findViewById(R.id.userImage);
        email=findViewById(R.id.ProfileEmail);
        profileName = findViewById(R.id.profileName);
        name = findViewById(R.id.SettingName);
        gender = findViewById(R.id.SettingGender);
        address =findViewById(R.id.SettingAddress);
        pinCode =findViewById(R.id.SettingPinCode);
        phone =findViewById(R.id.SettingPhone);
        backgroundImage = findViewById(R.id.backgroundImage);
        image = findViewById(R.id.editNameLogo);
        progressBar =findViewById(R.id.progressBarSetting);

        backgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        openCamera();
                        galleryAddPic();

                    }

                } else {
                    openCamera();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        db.collection("BackGroundPic")
                .document(firebaseAuth.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Object str = documentSnapshot.get("background");
                        if(str!=null){
                            String string = str.toString();
                            Glide.with(UserProfile.this).load(Uri.parse(string).toString()).into(backgroundImage);
                        }
                        else{
                            backgroundImage.setColorFilter(Color.GRAY);
                        }
                    }
                });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(UserProfile.this,EditUserDetails.class);
                startActivity(intent);
            }
        });
        edit.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        db.collection("users")
                .whereEqualTo("userUid",firebaseAuth.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot documentSnapshot : documentSnapshots){
                            documentId = documentSnapshot.getId();
                        }
                        db.collection("users")
                                .document(documentId)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        profileName.setText(documentSnapshot.get("name").toString());
                                        //String name,gender,address,pinCode,phone,profilePic,userUid;
                                        name.setText(documentSnapshot.get("name").toString());
                                        gender.setText(documentSnapshot.get("gender").toString());
                                        address.setText(documentSnapshot.get("address").toString());
                                        pinCode.setText(documentSnapshot.get("pinCode").toString());
                                        phone.setText(documentSnapshot.get("phone").toString());
                                        email.setText(user.getEmail());
                                        Glide.with(UserProfile.this).load(Uri.parse(documentSnapshot.get("profilePic").toString())).into(profilePic);
                                        Glide.with(UserProfile.this).load(Uri.parse(documentSnapshot.get("profilePic").toString())).into(image);
                                        //profilePic.setImageURI(Uri.parse(documentSnapshot.get("profilePic").toString()));
                                        //image.setImageURI(Uri.parse(documentSnapshot.get("profilePic").toString()));
                                        progressBar.setVisibility(View.GONE);
                                        edit.setVisibility(View.VISIBLE);

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       progressBar.setVisibility(View.GONE);
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
                    openCamera();
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
            assert data != null;
            mImageUri = data.getData();
            backgroundImage.setImageURI(mImageUri);
            BackGroundPic backGroundPic = new BackGroundPic(null);
            db.collection("BackGroundPic")
                    .document(Objects.requireNonNull(firebaseAuth.getUid()))
                    .set(backGroundPic)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String timeSpan = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                            final StorageReference image1 = reference.child("BackGround").child("Image"+timeSpan+".jpg");
                            image1.putFile(mImageUri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            image1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    db.collection("BackGroundPic")
                                                            .document(firebaseAuth.getUid())
                                                            .update("background",uri.toString())
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {

                                                                }
                                                            });

                                                }
                                            });
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

}
