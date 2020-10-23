package com.book.movieticketbooking.theatreactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.book.movieticketbooking.R;
import com.book.movieticketbooking.login.Login;
import com.google.firebase.auth.FirebaseAuth;

public class TheatreActivity extends AppCompatActivity {
    private Button add_upcoming_movie,add_show,update_show,view_booking,add_movie,view_feedback,update_Upcoming;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theatre_activity);

        setupUI();

        update_Upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TheatreActivity.this, UpdateUpcomingShow.class));
            }
        });
        
        add_upcoming_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TheatreActivity.this, AddUpcomingMovie.class));
            }
        });

        add_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TheatreActivity.this, AddShow.class));
            }
        });

        update_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TheatreActivity.this, UpdateShow.class));
            }
        });

        view_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TheatreActivity.this, ViewBooking.class));
            }
        });

        add_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TheatreActivity.this, AddMovie.class));
            }
        });

        view_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TheatreActivity.this, ViewFeedback.class));
            }
        });

    }

    public void setupUI(){
        add_upcoming_movie = (Button)findViewById(R.id.add_upcoming_movie);
        add_show = (Button)findViewById(R.id.add_show);
        update_show = (Button)findViewById(R.id.update_show);
        view_booking = (Button)findViewById(R.id.view_booking);
        add_movie = (Button)findViewById(R.id.add_movie);
        view_feedback = (Button)findViewById(R.id.view_feedback);
        update_Upcoming = (Button)findViewById(R.id.update_upcoming_movie);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.theatre_Logout: {
                logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("User Activity");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.signOut();
                startActivity(new Intent(TheatreActivity.this, Login.class));
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}