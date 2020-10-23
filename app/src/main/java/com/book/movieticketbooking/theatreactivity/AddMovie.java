package com.book.movieticketbooking.theatreactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.book.movieticketbooking.theatreactivity.model.Downloadmovies;
import com.book.movieticketbooking.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMovie extends AppCompatActivity {
    private EditText editText1,editText2;
    private Button button,pick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        editText1 = (EditText)findViewById(R.id.d_movie_name);
        editText2 = (EditText)findViewById(R.id.d_movie_link);

        button = (Button)findViewById(R.id.add);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movie_name = editText1.getText().toString();
                String movie_link = editText2.getText().toString();

                if (TextUtils.isEmpty(movie_name)){
                    editText1.setError("Enter movie name");
                }else if (TextUtils.isEmpty(movie_link)){
                    editText2.setError("Enter movie link");
                }else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    String id = databaseReference.push().getKey();
                    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Download Movie").child(id);
                    Downloadmovies download_movies = new Downloadmovies(id,movie_name,movie_link);
                    databaseReference2.setValue(download_movies);
                    Toast.makeText(getApplicationContext(),"Upload movie successful",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}