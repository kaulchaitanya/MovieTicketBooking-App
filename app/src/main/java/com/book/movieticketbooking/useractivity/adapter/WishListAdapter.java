package com.book.movieticketbooking.useractivity.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.movieticketbooking.R;
import com.book.movieticketbooking.useractivity.model.Wishlist;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class WishListAdapter extends FirebaseRecyclerAdapter<Wishlist, WishListAdapter.MyviewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public WishListAdapter(@NonNull FirebaseRecyclerOptions<Wishlist> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyviewHolder holder, int position, @NonNull Wishlist model) {
        holder.textView1.setText(model.getFilm_name());
        holder.textView2.setText(model.getFilm_year());

        String id = model.getFilm_id();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("Upcoming Movie Image");
        storageReference.child(id)
                .child("Image").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(holder.imageView4);
            }
        });
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_listview,parent,false);
        return new WishListAdapter.MyviewHolder(view);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView textView1,textView2;
        ImageView imageView4;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.wishlist_movieName);
            textView2 = (TextView) itemView.findViewById(R.id.wishlist_year);

            imageView4 = (ImageView) itemView.findViewById(R.id.wishlist_imageView);
        }
    }
}
