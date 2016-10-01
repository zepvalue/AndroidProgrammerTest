package com.apppartner.androidprogrammertest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class MainActivity extends AppCompatActivity
{
    private Button loginButton;
    private Button chatButton;
    private Button animationButton;
    private TextView codingTaskTextView;

    String response = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        codingTaskTextView = (TextView) findViewById(R.id.textViewCodingTasks);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato Bold.ttf");
        codingTaskTextView.setTypeface(font);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        chatButton = (Button) findViewById(R.id.buttonChat);
        animationButton = (Button) findViewById(R.id.buttonAnimation);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);


    }



    public void onLoginButtonClicked(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onChatButtonClicked(View v)
    {
        Intent intentFromLogin = getIntent();
        response = intentFromLogin.getStringExtra("extraResponse");
        if(response == null)
            response = "Not Logged";
        Intent chatIntent = new Intent(this, ChatActivity.class);
        chatIntent.putExtra("extraResponse" , response);
        startActivity(chatIntent);
    }

    public void onAnimationTestButtonClicked(View v)
    {
        Intent intent = new Intent(this, AnimationActivity.class);
        startActivity(intent);
    }
}
