package com.arvoron.scherpel.arvoron;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import id.zelory.compressor.Compressor;


public class NewTreeActivity extends AppCompatActivity {
    private Toolbar newPostToolbar;
    private ImageView newPostImage;
    private EditText newPostDesc;
    private EditText newPostName;
    private EditText newPostFamily;
    private EditText newPostLocation;
    private Button newPostButton;
    private Uri postImageUri = null;
    private ProgressBar newPostProgress;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String current_user_id;
    private Bitmap compressedImageFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tree);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        newPostToolbar = findViewById(R.id.tree_toolbar);
        setSupportActionBar(newPostToolbar);
        getSupportActionBar().setTitle("Catalog a Tree!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newPostImage = findViewById(R.id.treeImage);
        newPostDesc = findViewById(R.id.treeDesc);
        newPostButton = findViewById(R.id.treeButton);
        newPostProgress = findViewById(R.id.treeProgress);
        newPostName = findViewById(R.id.treeName);
        newPostFamily = findViewById(R.id.treeFamily);
        newPostLocation = findViewById(R.id.treeLocation);
        newPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .setMaxCropResultSize(912, 912)
                        .start(NewTreeActivity.this);
            }

        });

    newPostButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String desc = newPostDesc.getText().toString();
            final String name = newPostName.getText().toString();
            final String family = newPostFamily.getText().toString();
            final String location = newPostLocation.getText().toString();

            if(!TextUtils.isEmpty(name) && postImageUri != null){

                newPostProgress.setVisibility(View.VISIBLE);
                final String randomName = UUID.randomUUID().toString();
                StorageReference filePath = storageReference.child("tree_images").child(randomName + ".jpg");
                filePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                        final String downloadUri = task.getResult().getDownloadUrl().toString();
                        if(task.isSuccessful()){
                            File newImageFile = new File(postImageUri.getPath());
                            try {
                                compressedImageFile = new Compressor(NewTreeActivity.this)
                                        .setMaxHeight(200)
                                        .setMaxWidth(200)
                                        .setQuality(1)
                                        .compressToBitmap(newImageFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] thummbData = baos.toByteArray();
                            UploadTask uploadTask = storageReference.child("tree_images/thumbs").child(randomName + ".jpg")
                                    .putBytes(thummbData);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    String downloadThumbFromUri = taskSnapshot.getDownloadUrl().toString();
                                    Map<String, Object> postMap = new HashMap<>();
                                    postMap.put("image_url", downloadUri);
                                    postMap.put("thumb_url", downloadThumbFromUri);
                                    postMap.put("name", name);
                                    postMap.put("family", family);
                                    postMap.put("location", location);
                                    postMap.put("desc", desc);
                                    postMap.put("user_id", current_user_id);
                                    postMap.put("timestamp", FieldValue.serverTimestamp());
                                    firebaseFirestore.collection("Trees").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(NewTreeActivity.this, "Your tree was cataloged!", Toast.LENGTH_SHORT).show();
                                                Intent gotomain = new Intent(NewTreeActivity.this, NewMainActivity.class);
                                                startActivity(gotomain);
                                                finish();
                                            }else{

                                            }
                                            newPostProgress.setVisibility(View.INVISIBLE);
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                        }else{
                            newPostProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        }
    });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                postImageUri = result.getUri();
                newPostImage.setImageURI(postImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }

}
