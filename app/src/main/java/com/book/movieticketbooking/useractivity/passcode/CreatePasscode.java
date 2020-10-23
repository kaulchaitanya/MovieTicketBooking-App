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
import com.book.movieticketbooking.useractivity.model.Passcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanks.passcodeview.PasscodeView;

import static com.book.movieticketbooking.R.color.colorPrimary;

public class CreatePasscode extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_passcode);

        getSupportActionBar().hide();
        getWindow().setStatusBarColor((ContextCompat.getColor(getApplicationContext(), colorPrimary)));

        firebaseAuth = FirebaseAuth.getInstance();
        final PasscodeView passcodeView = (PasscodeView) findViewById(R.id.passcodeView);

        passcodeView.setCorrectInputTip("Your passcode is set").setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {
                Toast.makeText(getApplication(),"Passcode do not match",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String number) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Passcode").child(firebaseAuth.getUid());
                Passcode passcode = new Passcode(number);
                databaseReference.setValue(passcode);
                startActivity(new Intent(CreatePasscode.this, BankAccount.class));
                finish();
            }
        });

    }
}