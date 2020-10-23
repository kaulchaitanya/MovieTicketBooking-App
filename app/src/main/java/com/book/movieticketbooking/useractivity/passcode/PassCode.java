package com.book.movieticketbooking.useractivity.passcode;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.book.movieticketbooking.R;
import com.book.movieticketbooking.useractivity.BankAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hanks.passcodeview.PasscodeView;

import static com.book.movieticketbooking.R.color.colorPrimary;

public class PassCode extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView passcode;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_code);

        getSupportActionBar().hide();
        getWindow().setStatusBarColor((ContextCompat.getColor(getApplicationContext(), colorPrimary)));

        firebaseAuth = FirebaseAuth.getInstance();
        final PasscodeView passcodeView = findViewById(R.id.passcode_View);
        passcode = (TextView)findViewById(R.id.forgot_passcode);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Passcode").child(firebaseAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String code = String.valueOf(snapshot.child("passnumber").getValue());
                passcodeView.setPasscodeLength(4).setCorrectInputTip("You can now access your Wallet")
                        .setLocalPasscode(code).
                        setListener(new PasscodeView.PasscodeViewListener() {
                            @Override
                            public void onFail() {
                            }

                            @Override
                            public void onSuccess(String number) {
                                startActivity(new Intent(PassCode.this, BankAccount.class));
                                finish();
                            }
                });

                passcode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(PassCode.this,ResetPasscode.class));
                        finish();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PassCode.this, error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.passcode,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_passcode_change:
                startActivity(new Intent(PassCode.this, ResetPasscode.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}