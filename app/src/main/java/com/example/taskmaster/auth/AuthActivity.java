package com.example.taskmaster.auth;

import static com.example.taskmaster.MainActivity.getPinpointManager;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.pinpoint.AWSPinpointAnalyticsPlugin;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.taskmaster.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        configureAWS();

        ViewPager viewPager = findViewById(R.id.viewPager);

        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        SignUpFragment signUpFragment = SignUpFragment.newInstance("", "");
        signUpFragment.setSignUpListener(new SignUpFragment.OnSignUpComplete() {
            @Override
            public void signUpCompleted() {
                Log.i("AuthActivity", "The code works");
            }
        });

        pagerAdapter.addFragments(SignInFragment.newInstance("", ""));
        pagerAdapter.addFragments(signUpFragment);
        viewPager.setAdapter(pagerAdapter);
    }
    private void configureAWS() {
        try {
//            Amplify.addPlugin(new AWSDataStorePlugin());

            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSPinpointAnalyticsPlugin(getApplication()));
            Amplify.addPlugin(new AWSS3StoragePlugin());
//            getPinpointManager(getApplicationContext());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }
    }
}