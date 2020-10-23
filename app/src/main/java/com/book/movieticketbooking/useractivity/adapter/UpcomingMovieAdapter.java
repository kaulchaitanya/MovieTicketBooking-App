package com.book.movieticketbooking.useractivity.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.movieticketbooking.theatreactivity.model.Upcomingmovie;
import com.book.movieticketbooking.R;
import com.book.movieticketbooking.useractivity.model.Wishlist;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class UpcomingMovieAdapter extends FirebaseRecyclerAdapter<Upcomingmovie, UpcomingMovieAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UpcomingMovieAdapter(@NonNull FirebaseRecyclerOptions<Upcomingmovie> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, int position, @NonNull final Upcomingmovie model) {
        holder.Moviename.setText(model.getMovie_Name());
        holder.Year.setText(model.getMovie_Year());

        String id = model.getMovieId();
        final StorageReference[] storageReference = {FirebaseStorage.getInstance().getReference("Upcoming Movie Image")};
        storageReference[0].child(id)
                .child("Image").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(holder.imageView);
            }
        });

        final int[] counter = {1};
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (counter[0] == 1){
                    String moviename = model.getMovie_Name();
                    String releasingyear = model.getMovie_Year();
                    String id = model.getMovieId();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    String uid = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("WishList").child(uid).child(id);
                    Wishlist wishList = new Wishlist(id,moviename,releasingyear);
                    databaseReference.setValue(wishList);
                    Toast.makeText(v.getContext(), "Added to wishlist", Toast.LENGTH_SHORT).show();
                    counter[0]--;
                }else {
                    Toast.makeText(v.getContext(), "You Already Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_movie_listview,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Moviename,Year;
        ImageView imageView,imageView2;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            Moviename = (TextView)itemView.findViewById(R.id.movieName);
            Year = (TextView)itemView.findViewById(R.id.year);

            imageView = (ImageView)itemView.findViewById(R.id.upcomingMovie_imageView);
            imageView2 = (ImageView)itemView.findViewById(R.id.wishList);
        }
    }
}
