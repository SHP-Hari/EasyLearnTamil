package com.sliit.easylearner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseUser currentUser;
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);


        Button btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View focusView = null;
                boolean cancel = false;

                if (isEmpty(editTextPassword)) {
                    textInputPassword.setError(getResources().getString(R.string.emptyPassword));
                    focusView = editTextPassword;
                    cancel = true;
                } else if (!isValidPassword(editTextPassword)) {
                    textInputPassword.setError(getResources().getString(R.string.invalidPassword));
                    focusView = editTextPassword;
                    cancel = true;
                } else {
                    textInputPassword.setError(null);
                    textInputPassword.setErrorEnabled(false);
                }

                if (isEmpty(editTextEmail)) {
                    textInputEmail.setError(getResources().getString(R.string.emptyEmailAddress));
                    focusView = editTextEmail;
                    cancel = true;
                } else if (!isEmail(editTextEmail)) {
                    textInputEmail.setError(getResources().getString(R.string.invalidEmailAddress));
                    focusView = editTextEmail;
                    cancel = true;
                } else {
                    textInputEmail.setError(null);
                    textInputEmail.setErrorEnabled(false);
                }
                if (cancel) {
                    focusView.requestFocus(View.FOCUS_UP);
//                    closeKeyboard();
                } else {
                    String email = String.valueOf(editTextEmail.getText());
                    String password = String.valueOf(editTextPassword.getText());
                    attemptLogin(email, password);
                }
            }
        });
    }

    private void attemptLogin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            currentUser = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }

    public void startSignUp(View view){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public static boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    public static boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(EditText text) {
        CharSequence password = text.getText().toString();
        return password.length() >= 6;
    }

    public static boolean isValidContactNumber(EditText text) {
        CharSequence contactNumber = text.getText().toString();
        return ((String) contactNumber).matches("^(\\+94|0)[0-9]{9}$");
    }

    public static boolean isNumber(EditText text) {
        CharSequence number = text.getText().toString();
        return ((String) number).matches("^[0-9]{9}$");
    }

    public static boolean isLettersOnly(EditText text) {
        CharSequence name = text.getText().toString();
        return ((String) name).matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");
    }

    public static boolean isSpecialCharectersOnly(EditText text) {
        CharSequence name = text.getText().toString();
        String splChrs = "-/@#$%^&_+=()" ;
        return ((String) name).matches("[" + splChrs + "]+");
    }

    public static boolean isValidExamName(EditText text) {
        CharSequence name = text.getText().toString();
        String splChrs = "-/@#$%^&_+=()" ;
        return ((String) name).matches("(?!^\\d+$)^.+$");
    }
}
