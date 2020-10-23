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

import com.book.movieticketbooking.R;
import com.book.movieticketbooking.theatreactivity.model.Addshow;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddShow extends AppCompatActivity {
    private EditText movieName,Lang,Director,Cast,Category,Timing,Seat,Address,theatreName,price;
    private ImageView add_Image;
    private Button addShow;
    private DatabaseReference databaseReference;
    private static final int PICK = 3;
    Uri imagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                add_Image.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_show);

        setupUI();

        add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Intent.ACTION_PICK));
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,PICK);
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Add Show");

        addShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    public void validate(){
        String MovieName = movieName.getText().toString().trim();
        String Language = Lang.getText().toString().trim();
        String DirectorName = Director.getText().toString().trim();
        String CastName = Cast.getText().toString().trim();
        String CateGory = Category.getText().toString().trim();
        String TimIng = Timing.getText().toString().trim();
        String AvailableSeat = Seat.getText().toString().trim();
        String TheatreAddress = Address.getText().toString().trim();
        String TheatreName = theatreName.getText().toString().trim();
        String TheatrePrice = price.getText().toString().trim();
        int Available = Integer.parseInt(AvailableSeat);

        if (imagePath == null){
            Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(MovieName)){
            movieName.setError("Enter movie name");
        }else if (TextUtils.isEmpty(Language)){
            Lang.setError("Enter language");
        }else if (TextUtils.isEmpty(DirectorName)) {
            Director.setError("Enter director name");
        }else if (TextUtils.isEmpty(CastName)) {
            Cast.setError("Enter cast name");
        }else if (TextUtils.isEmpty(CateGory)){
            Category.setError("Enter category");
        }else if (TextUtils.isEmpty(TimIng)){
            Timing.setError("Enter show time");
        }else if (TextUtils.isEmpty(AvailableSeat)){
            Seat.setError("Enter seat");
        }else if (Available > 200){
            Seat.setError("You enter only 200 seats");
        }else if (TextUtils.isEmpty(TheatreAddress)){
            Address.setError("Enter address");
        }else if (TextUtils.isEmpty(TheatreName)){
            theatreName.setError("Enter theatre name");
        }else if (TextUtils.isEmpty(TheatrePrice)){
            price.setError("Enter movie price");
        } else{
            String id = databaseReference.push().getKey();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Add Show").child(id).child("Image");
            UploadTask uploadTask = storageReference.putFile(imagePath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext()," Something is wrong image not uploaded ",Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext()," Upload image successful",Toast.LENGTH_SHORT).show();
                }
            });

            Addshow addShow = new Addshow(id,MovieName,Language,DirectorName,CastName,CateGory,TimIng,AvailableSeat,TheatreAddress,TheatreName,TheatrePrice);
            databaseReference.child(id).setValue(addShow).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(AddShow.this,"Upload show successful",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddShow.this," Something is wrong show not uploaded ",Toast.LENGTH_SHORT).show();
                }
            });
            finish();
        }
    }

    public void setupUI(){
        movieName = (EditText)findViewById(R.id.movie_name);
        Lang = (EditText)findViewById(R.id.lang);
        Director = (EditText)findViewById(R.id.director_name);
        Cast = (EditText)findViewById(R.id.cast_name);
        Category = (EditText)findViewById(R.id.category);
        Timing = (EditText)findViewById(R.id.timing);
        Seat = (EditText)findViewById(R.id.available_seat);
        Address = (EditText)findViewById(R.id.address);
        theatreName = (EditText)findViewById(R.id.theatre_name);
        price = (EditText)findViewById(R.id.theatre_movie_price);

        addShow = (Button)findViewById(R.id.add_show);

        add_Image = (ImageView)findViewById(R.id.add_show_imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}