package com.andelachallenge.developerprofiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Profiles extends AppCompatActivity {

    ImageView profile_photo;
    TextView username, github_url;
    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        profile_photo = (ImageView)findViewById(R.id.profile_image);
        username = (TextView)findViewById(R.id.username);
        github_url = (TextView)findViewById(R.id.github_url);
        shareButton = (Button)findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                share_intent();
            }
        });
    }

    public void share_intent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        String shareContent = "Check out this awesome developer @<username>,<github " +
                "profile url>";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        startActivity(Intent.createChooser(shareIntent,"Share via"));
    }
}
