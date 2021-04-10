package com.sharnoxz.vacxinity.ui.settings;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.sharnoxz.vacxinity.R;
import com.sharnoxz.vacxinity.accounts.SharedPrefs;
import com.sharnoxz.vacxinity.accounts.UserProfile;
import com.sharnoxz.vacxinity.accounts.login;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    RelativeLayout profileView,privacyAndSafety,appUpdate,language,contactsUpdate,dataSaver,subscribe,unsubscribe;
    RelativeLayout helpCenter,safetyCenter,reportBugs,termOfUse,communityGuidelines,dataPolicy,logout;
    CircleImageView userPic;
    TextView userName;
    public static String documentId;
    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = firebaseAuth.getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        Settings(root);
        return root;
    }

    private void Settings(View root){
        userName = root.findViewById(R.id.Settings_UserName);
        userPic = root.findViewById(R.id.Settings_UserImage);
        try {
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
                            try {
                                db.collection("users")
                                        .document(documentId)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                userName.setText(Objects.requireNonNull(documentSnapshot.get("name")).toString());
                                                Glide.with(getContext()).load(Uri.parse(Objects.requireNonNull(documentSnapshot.get("profilePic")).toString())).into(userPic);

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                e.printStackTrace();
                                            }
                                        });
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
        }catch (Exception e) {
            e.printStackTrace();
        }

        profileView = root.findViewById(R.id.setting_rl1);
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserProfile.class);
                startActivity(intent);
            }
        });

        privacyAndSafety = root.findViewById(R.id.Under_Account_rl1);
        privacyAndSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com";
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        appUpdate = root.findViewById(R.id.Under_General_rl1);
        appUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com";
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        language = root.findViewById(R.id.Under_General_rl2);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("com.android.settings","com.android.settings.LanguageSettings");
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        contactsUpdate = root.findViewById(R.id.Under_General_rl3);
        contactsUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://eyezx.herokuapp.com/F5-guNNGzl4a56hlAAAA";
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        dataSaver = root.findViewById(R.id.Under_General_rl4);
        dataSaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        subscribe = root.findViewById(R.id.Under_General_rl5);
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

        helpCenter = root.findViewById(R.id.Under_Support_rl1);
        helpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com";
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        safetyCenter = root.findViewById(R.id.Under_Support_rl2);
        safetyCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com";
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        reportBugs = root.findViewById(R.id.Under_Support_rl3);
        reportBugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com";
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        termOfUse = root.findViewById(R.id.Under_About_rl1);
        termOfUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com";
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        communityGuidelines = root.findViewById(R.id.Under_About_rl2);
        communityGuidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com";
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        dataPolicy = root.findViewById(R.id.Under_About_rl3);
        dataPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com";
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        logout = root.findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                SharedPrefs.saveSharedSetting(getContext(),"sharedPrefs","true");
                Intent intent = new Intent(getContext(), login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


    }


}
