package com.example.taskmaster.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.example.taskmaster.MainActivity;
import com.example.taskmaster.R;

public class ConfirmSignUpActivity extends AppCompatActivity {
    private static final String TAG = "ConfirmSignUpActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sign_up);


        Intent passedData = getIntent();
        String email = passedData.getStringExtra(SignUpFragment.USER_EMAIL);

        EditText confirmationCode = findViewById(R.id.verification_code_input);

        Button confirmBtn = findViewById(R.id.confirm);
        confirmBtn.setOnClickListener(view -> {
            String code = confirmationCode.getText().toString();
            verifyEmail(code, email);
            startActivity(new Intent(ConfirmSignUpActivity.this, MainActivity.class));
        });
    }

    private void verifyEmail(String verificationCode, String email) {
        Amplify.Auth.confirmSignUp(
                email,
                verificationCode,
                result -> {
                    Log.i(TAG,
                            result.isSignUpComplete() ?
                                    "Confirm signUp succeeded" :
                                    "Confirm sign up not complete");


                },
                error -> Log.e(TAG, error.toString())
        );
    }
}