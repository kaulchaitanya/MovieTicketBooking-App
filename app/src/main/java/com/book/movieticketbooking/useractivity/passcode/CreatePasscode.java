package com.book.movieticketbooking.useractivity.passcode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.book.movieticketbooking.R;
import com.book.movieticketbooking.useractivity.BankAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanks.passcodeview.PasscodeView;

import static com.book.movieticketbooking.R.color.colorPrimary;

public class CreatePasscode extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private PasscodeView passcodeView;
    private String CurrentId;
    private DatabaseReference databaseReference;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_passcode);

        getSupportActionBar().hide();
        getWindow().setStatusBarColor((ContextCompat.getColor(getApplicationContext(), colorPrimary)));

        firebaseAuth = FirebaseAuth.getInstance();
        CurrentId = firebaseAuth.getCurrentUser().getUid();

        passcodeView = (PasscodeView) findViewById(R.id.passcodeView);

        passcodeView.setCorrectInputTip("Your Passcode is Set").setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {
                Toast.makeText(getApplication(),"Passcode do not match",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String number) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Passcode").child(CurrentId);
                //PasscodeSetting passcodeSetting = new PasscodeSetting(number);
                databaseReference.child("passNumber").setValue(number);
                databaseReference.child("UserId").setValue(CurrentId);
                startActivity(new Intent(getApplicationContext(),BankAccount.class));
                finish();
            }
        });
    }
}