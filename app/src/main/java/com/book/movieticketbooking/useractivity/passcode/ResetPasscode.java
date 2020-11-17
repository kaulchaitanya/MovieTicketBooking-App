package com.book.movieticketbooking.useractivity.passcode;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.book.movieticketbooking.R;
import com.book.movieticketbooking.useractivity.BankAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hanks.passcodeview.PasscodeView;

import java.util.HashMap;
import java.util.Map;

import static com.book.movieticketbooking.R.color.colorPrimary;

public class ResetPasscode extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passcode);

        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();
        getWindow().setStatusBarColor((ContextCompat.getColor(getApplicationContext(), colorPrimary)));


        final PasscodeView passcodeView = findViewById(R.id.change_passcodeView);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Passcode").child(firebaseAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                passcodeView.setPasscodeLength(4).setCorrectInputTip("Passcode successfully change").setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(ResetPasscode.this, "Passcode do not match", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String number) {
                        Map<String,Object > updatePAssCode = new HashMap<String, Object>();
                        updatePAssCode.put("passNumber",number);
                        databaseReference.updateChildren(updatePAssCode).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(ResetPasscode.this, BankAccount.class));
                                    finish();
                                } else {
                                    Toast.makeText(ResetPasscode.this, "There is someting wronhg password is not updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ResetPasscode.this, error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}