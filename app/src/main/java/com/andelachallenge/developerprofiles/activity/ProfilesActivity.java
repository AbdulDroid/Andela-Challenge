package com.andelachallenge.developerprofiles.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andelachallenge.developerprofiles.R;
import com.andelachallenge.developerprofiles.model.Items;
import com.andelachallenge.developerprofiles.service.ImageLoadTask;

import java.util.ArrayList;

/**
 * Created by Abdulkarim on 3/15/2017.
 */

public class ProfilesActivity extends AppCompatActivity {

    TextView loginTextView, profileUrlTextView;
    ImageView profileImage;
    Button share;
    ArrayList<Items> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        //Getting the extras attached to the intent from the previous activity


        final String login = getIntent().getStringExtra("login");
        final String user_image = getIntent().getStringExtra("avatar");
        final String user_url = getIntent().getStringExtra("url");


        //Initializing the TextViews, ImageView and the Share buttons


        loginTextView = (TextView) findViewById(R.id.username);
        profileUrlTextView = (TextView) findViewById(R.id.github_url);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        share = (Button) findViewById(R.id.share_button);

        //Assigning Values to the various views
        loginTextView.setText(login);

        ImageLoadTask loadImage = new ImageLoadTask(getApplicationContext());

        try{
            profileImage.setImageBitmap(loadImage.execute(user_image).get());
        }catch (Exception e){
            e.printStackTrace();
        }

        profileUrlTextView.setClickable(true);
        String text = "<a href='"+ user_url + "'>Github Profile URL</a>";
        profileUrlTextView.setText(Html.fromHtml(text));
        profileUrlTextView.setMovementMethod(LinkMovementMethod.getInstance());



        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @"
                + login + "," + user_url + ">");
                startActivity(shareIntent.createChooser(shareIntent, getResources()
                        .getText(R.string.share_intent)));
            }
        });

    }

}
