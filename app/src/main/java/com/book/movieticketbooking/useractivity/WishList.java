package com.book.movieticketbooking.useractivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.book.movieticketbooking.R;
import com.book.movieticketbooking.theatreactivity.model.Upcomingmovie;
import com.book.movieticketbooking.useractivity.adapter.UpcomingMovieAdapter;
import com.book.movieticketbooking.useractivity.adapter.WishListAdapter;
import com.book.movieticketbooking.useractivity.model.Wishlist;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class WishList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WishListAdapter wishListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Wishlist");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSplash)));
        getWindow().setStatusBarColor((ContextCompat.getColor(this,R.color.colorSplash)));

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.BLACK);
        recyclerView = (RecyclerView)findViewById(R.id.recview3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Wishlist> options = new FirebaseRecyclerOptions.Builder<Wishlist>().
                setQuery(FirebaseDatabase.getInstance().getReference("WishList").child(firebaseAuth.getUid()), Wishlist.class).build();

        wishListAdapter = new WishListAdapter(options);
        recyclerView.setAdapter(wishListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        wishListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wishListAdapter.stopListening();
    }
}