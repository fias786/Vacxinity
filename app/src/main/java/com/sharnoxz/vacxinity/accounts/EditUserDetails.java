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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
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

public class EditUserDetails extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_CODE = 1000;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public static String documentId;

    private Uri mImageUri;

    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = firebaseAuth.getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference reference = firebaseStorage.getReference();

    CircleImageView profileImage;
    EditText name,gender,address,pinCode,phone;
    Button done;
    ProgressBar progressBar;
    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);

        profileImage = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        gender=findViewById(R.id.gender);
        address=findViewById(R.id.address);
        pinCode=findViewById(R.id.pincode);
        phone=findViewById(R.id.phone);
        done=findViewById(R.id.Done);
        progressBar = findViewById(R.id.progressBar);

        db.collection("users")
                .whereEqualTo("userUid", Objects.requireNonNull(user).getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot documentSnapshot : documentSnapshots){
                            documentId = documentSnapshot.getId();
                            db.collection("users")
                                    .document(documentId)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            name.setText(Objects.requireNonNull(documentSnapshot.get("name")).toString());
                                            gender.setText(Objects.requireNonNull(documentSnapshot.get("gender")).toString());
                                            address.setText(Objects.requireNonNull(documentSnapshot.get("address")).toString());
                                            pinCode.setText(Objects.requireNonNull(documentSnapshot.get("pinCode")).toString());
                                            phone.setText(Objects.requireNonNull(documentSnapshot.get("phone")).toString());
                                            Glide.with(EditUserDetails.this).load(Objects.requireNonNull(documentSnapshot.get("profilePic")).toString()).into(profileImage);
                                            imageUri = Objects.requireNonNull(documentSnapshot.get("profilePic")).toString();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

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
                String getUserUid = firebaseAuth.getUid();


                if(TextUtils.isEmpty(getName)){
                    Toast.makeText(EditUserDetails.this,"Please enter your Name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getGender)){
                    Toast.makeText(EditUserDetails.this,"Please enter your Gender",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getAddress)){
                    Toast.makeText(EditUserDetails.this,"Please enter your Address",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getPinCode)){
                    Toast.makeText(EditUserDetails.this,"Please enter Pin Code",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getPhone)){
                    Toast.makeText(EditUserDetails.this,"Please enter your Phone",Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    final Userdatas userdatas = new Userdatas(getName,getGender,getAddress,getPinCode,getPhone,imageUri,getUserUid);
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
                                            .set(userdatas, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    String timeSpan = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                                                    final StorageReference image1 = reference.child(documentId).child("Image"+timeSpan+".jpg");
                                                    image1.putFile(mImageUri)
                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                    image1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri) {
                                                                            db.collection("users")
                                                                                    .document(documentId)
                                                                                    .update("profilePic",uri.toString())
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {
                                                                                            Log.d(TAG,"onUpdate");
                                                                                            onBackPressed();
                                                                                            finish();
                                                                                            progressBar.setVisibility(View.GONE);
                                                                                        }
                                                                                    });

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
                                    Toast.makeText(EditUserDetails.this,"Data Uploading Failed",Toast.LENGTH_SHORT).show();
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

   /* private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivity(intent,REQUEST_IMAGE_CAPTURE);
    }
    */

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
            mImageUri = data.getData();
            profileImage.setImageURI(mImageUri);
        }
    }
}
