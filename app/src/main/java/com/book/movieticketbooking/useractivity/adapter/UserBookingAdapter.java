package com.book.movieticketbooking.useractivity.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.movieticketbooking.useractivity.model.Booking;
import com.book.movieticketbooking.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class UserBookingAdapter extends FirebaseRecyclerAdapter<Booking, UserBookingAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserBookingAdapter(@NonNull FirebaseRecyclerOptions<Booking> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, int position, @NonNull Booking model) {
        holder.textView1.setText(model.getBookingId());
        holder.textView2.setText(model.getMovieName());
        holder.textView3.setText(model.getUserName());
        holder.textView4.setText(model.getBookingSeat());
        holder.textView5.setText(model.getShowTime());
        holder.textView6.setText(model.getAddress());
        holder.textView7.setText(model.getTheatre());

        String id = model.getMovieId();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Add Show");
        storageReference.child(id)
                .child("Image").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(holder.imageView);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_booking_listview,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7;
        ImageView imageView;
        public myViewHolder(@NonNull View listViewItem) {
            super(listViewItem);
            textView1 = (TextView)listViewItem.findViewById(R.id.book_Booking_Id);
            textView2 = (TextView) listViewItem.findViewById(R.id.book_Movie_Name);
            textView3 =  (TextView)listViewItem.findViewById(R.id.book_User_Name);
            textView4 =  (TextView)listViewItem.findViewById(R.id.Book_seat);
            textView5 =  (TextView)listViewItem.findViewById(R.id.Book_Time);
            textView6 =  (TextView)listViewItem.findViewById(R.id.book_Address);
            textView7 =  (TextView)listViewItem.findViewById(R.id.book_Theatre);

            imageView = (ImageView)listViewItem.findViewById(R.id.Image_view);
        }
    }
}
