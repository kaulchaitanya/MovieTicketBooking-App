package com.book.movieticketbooking.useractivity.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.movieticketbooking.theatreactivity.model.Addshow;
import com.book.movieticketbooking.useractivity.model.Booking;
import com.book.movieticketbooking.useractivity.model.Movieprice;
import com.book.movieticketbooking.useractivity.model.Userprofile;
import com.book.movieticketbooking.theatreactivity.model.Viewbooking;
import com.book.movieticketbooking.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class BookShowAdapter extends FirebaseRecyclerAdapter<Addshow, BookShowAdapter.myViewHolder> {
    
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public BookShowAdapter(@NonNull FirebaseRecyclerOptions<Addshow> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, final int position, @NonNull final Addshow model) {
        holder.movie_name.setText(model.getMovie_Name());
        holder.lang.setText(model.getMovie_Lang());
        holder.director.setText(model.getDirector_Name());
        holder.cast.setText(model.getMovie_Cast());
        holder.category.setText(model.getM_category());
        holder.time.setText(model.getShow_Time());
        holder.seat.setText(model.getAvailable_Seat());
        holder.address.setText(model.getM_address());
        holder.theatre_name.setText(model.getTheatre_Name());
        holder.m_price.setText(model.getPrice());

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final String uid = firebaseAuth.getCurrentUser().getUid();

        String id = model.getMovieId();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Add Show");
        storageReference.child(id)
                .child("Image").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(holder.imageView);
            }
        });

        int AvailableSeat = Integer.parseInt(String.valueOf(model.getAvailable_Seat()));
        if (AvailableSeat == 0){
            holder.book.setEnabled(false);
        }else {
            holder.book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = LayoutInflater.from(view.getContext());
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    view = inflater.inflate(R.layout.b_show,null);

                    TextView textView = (TextView)view.findViewById(R.id.textView);
                    View view1 = (View)view.findViewById(R.id.view);

                    final TextView editText1 = (TextView) view.findViewById(R.id.b_movie_id);
                    final TextView editText2 = (TextView) view.findViewById(R.id.b_movie_name);
                    final TextView editText3 = (TextView) view.findViewById(R.id.b_user_name);
                    final TextView editText4 = (TextView) view.findViewById(R.id.b_user_contact);
                    final TextView editText5 = (TextView) view.findViewById(R.id.b_movie_time);
                    final TextView editText6 = (TextView) view.findViewById(R.id.b_seat);
                    final EditText editText7 = (EditText) view.findViewById(R.id.b_seat_booking);

                    Button b_book = (Button) view.findViewById(R.id.b_book);

                    builder.setView(view);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                    final String id = model.getMovieId();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Add Show").child(id);
                    final View finalView = view;
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Addshow addShow = snapshot.getValue(Addshow.class);
                            editText1.setText(addShow.getMovieId());
                            editText2.setText(addShow.getMovie_Name());
                            editText5.setText(addShow.getShow_Time());
                            editText6.setText(addShow.getAvailable_Seat());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(finalView.getContext(),error.getCode(),Toast.LENGTH_SHORT).show();
                        }
                    });

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User Info").child(uid);
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Userprofile userProfile = snapshot.getValue(Userprofile.class);
                            editText3.setText(userProfile.getUserName());
                            editText4.setText(userProfile.getUserMobile());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(finalView.getContext(),error.getCode(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    b_book.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String Booking_Seat = editText7.getText().toString();
                            if (TextUtils.isEmpty(Booking_Seat)){
                                editText7.setError("Enter number of booking seat");
                            }else {
                                final String MovieId = editText1.getText().toString();
                                final String MovieName = editText2.getText().toString();
                                final String UserName = editText3.getText().toString();
                                final String ShowTime = editText5.getText().toString();
                                final int booking_Seat2 = Integer.parseInt(editText7.getText().toString());
                                int AvailableSeat = Integer.parseInt(String.valueOf(model.getAvailable_Seat()));
                                if (booking_Seat2 > AvailableSeat){
                                    editText7.setError("Total Seat : "+AvailableSeat);
                                }else if (booking_Seat2 == 0) {
                                    editText7.setError("Enter a valid number");
                                }else if(booking_Seat2 > 10){
                                    editText7.setError("Sorry, You only book 10 seats at a time");
                                } else {
                                    String MoviePrice2 = model.getPrice();
                                    int price = Integer.parseInt(MoviePrice2);
                                    int T_Price = price * booking_Seat2;
                                    String T_price = Integer.toString(T_Price);
                                    String movieid = model.getMovieId();
                                    Movieprice moviePrice = new Movieprice(T_price);
                                    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Movie Price").child(movieid);
                                    databaseReference2.setValue(moviePrice);

                                    LayoutInflater inflater1 = LayoutInflater.from(v.getContext());
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                                    v = inflater1.inflate(R.layout.payment,null);

                                    final EditText edit1 = (EditText) v.findViewById(R.id.cardholder_name);
                                    final EditText edit2 = (EditText) v.findViewById(R.id.account_number);
                                    final EditText edit3 = (EditText) v.findViewById(R.id.payment_code);

                                    final Button button = (Button) v.findViewById(R.id.pay_button);

                                    DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Movie Price").child(movieid);
                                    final View finalV = v;
                                    databaseReference3.addValueEventListener(new ValueEventListener() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Movieprice movieprice1 = snapshot.getValue(Movieprice.class);
                                            assert movieprice1 != null;
                                            button.setText("PAY: "+ movieprice1.getMoviePrice()+" RS.");
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(finalV.getContext(), error.getCode(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    builder1.setView(v);
                                    final AlertDialog alertDialog1 = builder1.create();
                                    alertDialog1.show();

                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(final View v) {
                                            final String Name = edit1.getText().toString();
                                            final String Number = edit2.getText().toString();
                                            final String Password = edit3.getText().toString();
                                            String MoviePrice = model.getPrice();
                                            final String Address = model.getM_address();
                                            final String Theatre = model.getTheatre_Name();
                                            int AvailableSeat = Integer.parseInt(model.getAvailable_Seat());
                                            int Seat = AvailableSeat - booking_Seat2;
                                            final String Seat2 = Integer.toString(Seat);
                                            final String key = model.getMovieId();

                                            final int Movie_Price = Integer.parseInt(MoviePrice);
                                            if (TextUtils.isEmpty(Name)){
                                                edit1.setError("Enter Cardholder name");
                                            }else if (TextUtils.isEmpty(Number)){
                                                edit2.setError("Enter Account number");
                                            }else if (TextUtils.isEmpty(Password)){
                                                edit3.setError("Enter Payment password");
                                            }else {
                                                final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Account Info").child(uid);
                                                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        String HolderName = String.valueOf(snapshot.child("accountHolderName").getValue());
                                                        String HolderAccount = String.valueOf(snapshot.child("accountNumber").getValue());
                                                        String HolderPassword = String.valueOf(snapshot.child("accountPassword").getValue());
                                                        int AvailableBalance = Integer.parseInt(String.valueOf(snapshot.child("accountMoney").getValue()));
                                                        int Price = booking_Seat2 * Movie_Price;
                                                        if (!Name.equals(HolderName)){
                                                            edit1.setError("Holder name is incorrect");
                                                        }else if (!Number.equals(HolderAccount)){
                                                            edit2.setError("Account number is incorrect");
                                                        }else if (!Password.equals(HolderPassword)){
                                                            edit3.setError("Payment password is wrong");
                                                        }else if (AvailableBalance < Price){
                                                            Toast.makeText(v.getContext(), "Insufficient Balance", Toast.LENGTH_SHORT).show();
                                                            alertDialog1.dismiss();
                                                        }else {
                                                            DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Booking");
                                                            String id = databaseReference3.push().getKey();
                                                            Booking booking = new Booking(id,MovieId,MovieName,UserName,ShowTime,Booking_Seat,Address,Theatre);
                                                            databaseReference3.child(uid).child(id).setValue(booking);

                                                            DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference("View Booking").child(id);
                                                            Viewbooking view_booking = new Viewbooking(id,MovieId,MovieName,UserName,ShowTime,Booking_Seat);
                                                            databaseReference4.setValue(view_booking);

                                                            DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference("Add Show").child(key).child("available_Seat");
                                                            databaseReference5.setValue(Seat2);

                                                            int Balance = AvailableBalance - Price;
                                                            String Balance2 = Integer.toString(Balance);
                                                            databaseReference2.child("accountMoney").setValue(Balance2);
                                                            Toast.makeText(v.getContext(), "Booking Confirm", Toast.LENGTH_SHORT).show();
                                                            alertDialog1.dismiss();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        Toast.makeText(v.getContext(), error.getCode(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                   alertDialog.dismiss();
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_listview2,parent,false);
        return new myViewHolder(view);
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView movie_name,lang,director,cast,category,time,seat,address,theatre_name,m_price;
        Button book;
        ImageView imageView;

        public myViewHolder(@NonNull View listViewItem) {
            super(listViewItem);
            movie_name = (TextView)listViewItem.findViewById(R.id.MovieName);
            lang = (TextView)listViewItem.findViewById(R.id.Language);
            director = (TextView)listViewItem.findViewById(R.id.Director);
            cast = (TextView)listViewItem.findViewById(R.id.Cast);
            category = (TextView)listViewItem.findViewById(R.id.Category);
            time = (TextView)listViewItem.findViewById(R.id.Time);
            seat = (TextView)listViewItem.findViewById(R.id.Seat);
            address = (TextView)listViewItem.findViewById(R.id.Address);
            theatre_name = (TextView)listViewItem.findViewById(R.id.TheatreName);
            m_price = (TextView)listViewItem.findViewById(R.id.book_price);

            book = (Button)listViewItem.findViewById(R.id.BookShow);

            imageView = (ImageView)listViewItem.findViewById(R.id.bookShow_imageView);
        }
    }
}
