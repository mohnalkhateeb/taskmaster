package com.example.taskmaster.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.example.taskmaster.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        configureAWS();

        ViewPager viewPager = findViewById(R.id.viewPager);

        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager(),0);
        SignUpFragment signUpFragment = SignUpFragment.newInstance("", "");
        signUpFragment.setSignUpListener(new SignUpFragment.OnSignUpComplete() {
            @Override
            public void signUpCompleted() {
                Log.i("AuthActivity", "The code works");
            }
        });

        pagerAdapter.addFragments(signUpFragment);
        pagerAdapter.addFragments(SignInFragment.newInstance("", ""));
        viewPager.setAdapter(pagerAdapter);
    }
    private void configureAWS() {
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }
    }
}