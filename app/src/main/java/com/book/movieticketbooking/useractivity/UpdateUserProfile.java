package com.book.movieticketbooking.useractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.book.movieticketbooking.useractivity.model.Userprofile;
import com.book.movieticketbooking.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserProfile extends AppCompatActivity {

    private EditText Update_Name,Update_Email,Update_Mobile,Update_Address;
    private EditText Update_Dob;
    private Button saveProfile;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        setupUI();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Update_Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateUserProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        Update_Dob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Info").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Userprofile userProfile = snapshot.getValue(Userprofile.class);
                Update_Name.setText( userProfile.getUserName());
                Update_Dob.setText( userProfile.getUserDob());
                Update_Email.setText( userProfile.getUserEmail());
                Update_Mobile.setText( userProfile.getUserMobile());
                Update_Address.setText(  userProfile.getUserAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateUserProfile.this,error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });


        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = Update_Name.getText().toString();
                String Dob = Update_Dob.getText().toString();
                String Email = Update_Email.getText().toString();
                String Mobile = Update_Mobile.getText().toString();
                String Address = Update_Address.getText().toString();

                if (TextUtils.isEmpty(Name)){
                    Update_Name.setError("Enter Name");
                }else if (TextUtils.isEmpty(Dob)){
                    Update_Dob.setError("Select Date");
                }else if (TextUtils.isEmpty(Email)){
                    Update_Email.setError("Enter Email");
                }else if (TextUtils.isEmpty(Mobile)){
                    Update_Mobile.setError("Enter Mobile number");
                }else if (TextUtils.isEmpty(Address)){
                    Update_Address.setError("Enter Address");
                }else {
                    Map<String,Object> updateProfile = new HashMap<String, Object>();
                    updateProfile.put("userName",Name);
                    updateProfile.put("userDob",Dob);
                    updateProfile.put("userEmail",Email);
                    updateProfile.put("userMobile",Mobile);
                    updateProfile.put("userAddress",Address);
                    databaseReference.updateChildren(updateProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Update successful",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Update Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
            }
        });
    }

    public void setupUI(){
        Update_Name = (EditText)findViewById(R.id.update_Name);
        Update_Dob = (EditText) findViewById(R.id.update_Dob);
        Update_Email = (EditText)findViewById(R.id.update_Email);
        Update_Mobile = (EditText)findViewById(R.id.update_Mobile);
        Update_Address = (EditText)findViewById(R.id.update_Address);

        saveProfile = (Button)findViewById(R.id.save);
    }

}