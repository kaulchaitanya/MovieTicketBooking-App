package com.book.movieticketbooking.useractivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.book.movieticketbooking.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class UpdateProfileImage extends AppCompatActivity {
    private ImageView image;
    private FirebaseAuth firebaseAuth;
    private Button button;
    private static final int PICK = 3;
    Uri imagePath;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode,final  @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            if (imagePath == null){
                button.setVisibility(View.INVISIBLE);
            }else {
                button.setVisibility(View.VISIBLE);
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                image.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__profile__image);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBlack)));
        getWindow().setStatusBarColor((ContextCompat.getColor(this,R.color.colorBlack)));
        getSupportActionBar().setTitle("Profile Pic");

        image = (ImageView) findViewById(R.id.view_image);
        firebaseAuth = FirebaseAuth.getInstance();
        button = (Button)findViewById(R.id.save_image);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Profile Image");
        storageReference.child(firebaseAuth.getUid())
                .child("Image").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(image);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final ProgressDialog progressDialog = new ProgressDialog(UpdateProfileImage.this);
                    progressDialog.setTitle("Uploading Profile Image");
                    progressDialog.show();
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("Profile Image").child(firebaseAuth.getUid()).child("Image");
                    UploadTask uploadTask = storageReference.putFile(imagePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image update successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_dp,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_images:
                Intent intent = new Intent((Intent.ACTION_PICK));
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,PICK);
                break;
            case R.id.action_delete:
                Delete();
                break;
            case R.id.action_share:
                try {
                    Intent intent1 = new Intent(Intent.ACTION_SEND);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.putExtra(Intent.EXTRA_STREAM,imagePath);
                    intent1.setType("image/*");
                    startActivity(Intent.createChooser(intent1,"Share Via"));
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void Delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileImage.this);
        builder.setTitle("Delete Profile image");
        builder.setMessage("Are you sure you want to delete profile image ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                StorageReference storageReference1 = FirebaseStorage.getInstance().getReference("Profile Image").child(firebaseAuth.getUid()).child("Image");
                storageReference1.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Delete successful",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext()," Something is wrong upcoming movie not deleted ",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}