package com.book.movieticketbooking.useractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.book.movieticketbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeMailPass extends AppCompatActivity {
    private EditText changeEmail,changePassword;
    private Button updateEmail,updatePassword;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mail_pass);

        setupUI();
        progressDialog = new ProgressDialog(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = changeEmail.getText().toString();
                if(TextUtils.isEmpty(Email)){
                    changeEmail.setError("Enter email");
                }else {
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                    firebaseUser.updateEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(ChangeMailPass.this,"Email successfully changed",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(ChangeMailPass.this,"Email update failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password = changePassword.getText().toString();
                if (TextUtils.isEmpty(Password)){
                    changePassword.setError("Enter new password");
                }else {
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                    firebaseUser.updatePassword(Password).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(ChangeMailPass.this,"Password successfully changed",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(ChangeMailPass.this,"Password update failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void setupUI(){
        changeEmail = (EditText)findViewById(R.id.change_email);
        changePassword = (EditText)findViewById(R.id.change_password);

        updateEmail = (Button)findViewById(R.id.update_email);
        updatePassword = (Button)findViewById(R.id.update_password);
    }
}