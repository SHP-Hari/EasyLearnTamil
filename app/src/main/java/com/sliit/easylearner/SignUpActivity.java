package com.sliit.easylearner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword1;
    EditText editTextPassword2;
    TextInputLayout textInputName;
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;
    TextInputLayout textInputPasswordConfirm;
    ProgressDialog progressDialog;
    private DatabaseReference userRef;
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword1 = findViewById(R.id.password1);
        editTextPassword2 = findViewById(R.id.password2);
        textInputName = findViewById(R.id.textInputName);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputPasswordConfirm = findViewById(R.id.textInputPasswordConfirm);
        progressDialog = new ProgressDialog(this);

        Button btnSignUp = findViewById(R.id.signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                View focusView = null;

                if (LoginActivity.isEmpty(editTextPassword1)) {
                    textInputPassword.setError(getResources().getString(R.string.emptyPassword));
                    focusView = editTextPassword1;
                    cancel = true;
                } else if (!LoginActivity.isValidPassword(editTextPassword1)) {
                    textInputPassword.setError(getResources().getString(R.string.invalidPassword));
                    focusView = editTextPassword1;
                    cancel = true;
                } else {
                    textInputPassword.setError(null);
                    textInputPassword.setErrorEnabled(false);
                }

                if (LoginActivity.isEmpty(editTextEmail)) {
                    textInputEmail.setError(getResources().getString(R.string.emptyEmailAddress));
                    focusView = editTextEmail;
                    cancel = true;
                } else if (!LoginActivity.isEmail(editTextEmail)) {
                    textInputEmail.setError(getResources().getString(R.string.invalidEmailAddress));
                    focusView = editTextEmail;
                    cancel = true;
                } else {
                    textInputEmail.setError(null);
                    textInputEmail.setErrorEnabled(false);
                }

                if (LoginActivity.isEmpty(editTextName)) {
                    textInputName.setError(getResources().getString(R.string.emptyUsernaName));
                    focusView = editTextName;
                    cancel = true;
                } else if (!LoginActivity.isLettersOnly(editTextName)) {
                    textInputName.setError(getResources().getString(R.string.invalidUserName));
                    focusView = editTextName;
                    cancel = true;
                } else {
                    textInputName.setError(null);
                    textInputName.setErrorEnabled(false);
                }

                if (cancel) {
                    focusView.requestFocus();
//                    closeKeyboard();
                } else {
                    String name = String.valueOf(editTextName.getText());
                    String email = String.valueOf(editTextEmail.getText());
                    String password1 = String.valueOf(editTextPassword1.getText());
                    String password2 = String.valueOf(editTextPassword2.getText());
                    if (password1.equals(password2)){
                        createAccount(editTextEmail.getText().toString(), editTextPassword1.getText().toString());
                        textInputPasswordConfirm.setError(null);
                        textInputPasswordConfirm.setErrorEnabled(false);
                    }else {
                        textInputPasswordConfirm.setError(getResources().getString(R.string.mismatchingPasswords));
                        focusView = editTextPassword2;
                        focusView.requestFocus();
                    }
                }
            }
        });
    }

    private void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            currentUser = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            saveUserData(editTextName.getText().toString(), editTextEmail.getText().toString());

                        } else {

                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            editTextEmail.setText(currentUser.getEmail());
        }
    }

    private void saveUserData(String name, String email){
        Uid=mAuth.getCurrentUser().getUid();
        userRef= FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);

        progressDialog.setMessage("Updating Profile...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        HashMap usermap=new HashMap();
        usermap.put("Name", name);
        usermap.put("Email", email);

        userRef.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this,"Profile Updated!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SignUpActivity.this.finish();

                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this,"Error!! Please Try Again!!", Toast.LENGTH_SHORT).show();
                }


            }

        });
    }

    public void startLogin(View view){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
