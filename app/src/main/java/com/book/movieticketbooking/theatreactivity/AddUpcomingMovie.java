package com.book.movieticketbooking.theatreactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.book.movieticketbooking.theatreactivity.model.Upcomingmovie;
import com.book.movieticketbooking.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddUpcomingMovie extends AppCompatActivity {
    private EditText MovieName,Releasing_year;
    private ImageView imageView;
    private Button add;
    private DatabaseReference databaseReference;
    private static final int PICK = 3;
    Uri imagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                imageView.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_upcoming_movie);

        setupUI();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Intent.ACTION_PICK));
                intent.setType("image/*");
                startActivityForResult(intent,PICK);
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Upcoming Movie");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        String movieName = MovieName.getText().toString().trim();
        String year = Releasing_year.getText().toString().trim();

        if (imagePath == null){
            Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(movieName)){
            MovieName.setError("Enter movie name");
        }else if (TextUtils.isEmpty(year)){
            Releasing_year.setError("Enter Releasing year");
        }else {
            String id = databaseReference.push().getKey();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Upcoming Movie Image").child(id).child("Image");
            UploadTask uploadTask = storageReference.putFile(imagePath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext()," Something is wrong image not uploaded ",Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"Upload image successful",Toast.LENGTH_SHORT).show();
                }
            });

            Upcomingmovie upcoming_movie = new Upcomingmovie(id,movieName,year);
            databaseReference.child(id).setValue(upcoming_movie).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(AddUpcomingMovie.this," Upload upcoming movie successful ",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddUpcomingMovie.this," Something is wrong show not uploaded ",Toast.LENGTH_SHORT).show();
                }
            });
            finish();
        }
    }

    public void setupUI(){
        MovieName = (EditText)findViewById(R.id.up_movie_name);
        Releasing_year = (EditText)findViewById(R.id.up_release);

        add = (Button)findViewById(R.id.add_up_movie);

        imageView = (ImageView)findViewById(R.id.upcoming_movie_imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}